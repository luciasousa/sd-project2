package serverSide.sharedRegions;
import commInfra.*;
import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import clientSide.stubs.GeneralReposStub;

/**
 *    Kitchen
 *
 *    It is responsible for the the synchronization of the Chef and Waiter
 *    is implemented as an implicit monitor.
 *    
 *    There is one internal synchronization points: 
 *    a single blocking point for the Chef, where he waits for the Waiter to signal
 */

public class Kitchen 
{
    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;

    private KitchenClientProxy chefProxy;
    private KitchenClientProxy waiterProxy;

    /**
     *   Counter of the number of courses to deliver.
     */
    private int numberOfCoursesToDeliver;

    /**
     *   Counter of the number of portions to deliver.
     */
    private int numberOfPortionsToDeliver;

    /**
     *   Reference to the general repository
     */
    private final GeneralReposStub reposStub;

    /**
     *   Boolean flag that indicates if is the first course.
     */
    private boolean firstCourse;

    /**
     *   Boolean flag that indicates if note is available.
     */
    private boolean isNoteAvailable = false;

    /**
     *   Boolean flag that indicates if preperation was started.
     */
    private boolean preparationStarted;

    /**
     *   Boolean flag that indicates if portion was collected.
     */
    private boolean portionCollected;

    /**
     *  Kitchen instantiation.
     *
     *    @param repos reference to the General Information Repository
     */
    public Kitchen(GeneralReposStub reposStub)
    {
        firstCourse = true;
        this.reposStub = reposStub;
        nEntities=0;
        chefProxy = null;
        waiterProxy = null;
    }

    /**
     *    Operation set first course
     *
     *    Called by the chef to set first course
     *    @param b boolean value setting the first course
     *    
     */
    public void setFirstCourse(boolean b) { firstCourse = b; }

    /**
     *    Operation get first course
     *
     *    Called by the chef to get first course
     *    @return boolean value for the first course
     *    
     */
    public boolean getFirstCourse() { return firstCourse; }

    /**
     *    Operation watch the news
     *
     *    Called by the chef to watch the news
     *    waits until note is available
     *    
     */
    public synchronized void watchTheNews() 
    {
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        if(chefProxy.getChefState() != ChefStates.WAFOR) 
        {
            chefProxy.setChefState(ChefStates.WAFOR);
            int state = chefProxy.getChefState();
            reposStub.setChefState(state);
        }
        System.out.println("chef watches the news");
        while(!isNoteAvailable)
        {
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("chef has note");
    }
    
    /**
     *    Operation hand note to the chef
     *
     *    Called by the waiter to hand note to the chef
     *    signals chef that note is available
     *    waiter waits until chef starts preparation
     *    
     */
    public synchronized void handTheNoteToChef() 
    {
        waiterProxy = (KitchenClientProxy) Thread.currentThread();
        waiterProxy.setWaiterState(WaiterStates.PCODR);
        int state = waiterProxy.getWaiterState();
        reposStub.setWaiterState(state);
        System.out.println("waiter hands the note to chef");
        this.numberOfCoursesToDeliver = Constants.M;
        this.numberOfPortionsToDeliver = Constants.N;
        isNoteAvailable = true;
        notifyAll();
        while(!preparationStarted)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *    Operation chef wait for collection
     *
     *    Called by the waiter for chef to wait for collection of the portion
     *    
     */
    public synchronized void chefWaitForCollection() 
    {
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        chefProxy.setChefState(ChefStates.DLVPT);
        int state = chefProxy.getChefState();
        reposStub.setChefState(state);
        System.out.println("chef waits for waiter to collect portion");
        while(!portionCollected)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        portionCollected = false;
    }

    /**
     *    Operation portion has been collected
     *
     *    Called by the waiter to inform that portion was collected
     *    signals chef that portion was collected
     *    
     */
    public synchronized void portionHasBeenCollected()
    {
        portionCollected = true;
        notifyAll();
    }

    /**
     *    Operation start preparation
     *
     *    Called by the chef to start preparation
     *    signals waiter that preperation was started
     *    
     */    
    public synchronized void startPreparation() 
    {
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        chefProxy.setChefState(ChefStates.PRPCS);
        int state = chefProxy.getChefState();
        reposStub.setChefState(state);
        numberOfCoursesToDeliver--;
        System.out.printf("chef starts preparation\n");
        preparationStarted = true;
        notifyAll();
    }

    /**
     *    Operation proceedToPresentation
     *
     *    Called by the chef to proceed to presentation
     *    
     */
    public synchronized void proceedToPresentation() 
    {
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        chefProxy.setChefState(ChefStates.DSHPT);
        int state = chefProxy.getChefState();
        reposStub.setChefState(state);
        if(numberOfPortionsToDeliver!=0) numberOfPortionsToDeliver--;
        else numberOfPortionsToDeliver=Constants.N-1;
        System.out.printf("chef proceeds to presentation, course %d, portion %d\n",numberOfCoursesToDeliver,numberOfPortionsToDeliver);
    }

    /**
     *    Operation have all portions been delivered
     *
     *    Called by the chef to check if all portions were delivered
     *    
     *    @return boolean
     */
    public synchronized boolean haveAllPortionsBeenDelivered() 
    {
        if(numberOfPortionsToDeliver == 0) return true; else return false;
    }

    /**
     *    Operation has the order been completed
     *
     *    Called by the chef to check if the order was completed
     *    
     *    @return boolean
     */
    public synchronized boolean hasTheOrderBeenCompleted() 
    {
        if(numberOfCoursesToDeliver == 0) return true; else return false;
    }

    /**
     *    Operation have next portion ready
     *
     *    Called by the chef to have next portion ready
     *    
     */
    public synchronized void haveNextPortionReady() 
    {
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        chefProxy.setChefState(ChefStates.DSHPT);
        int state = chefProxy.getChefState();
        reposStub.setChefState(state);
        numberOfPortionsToDeliver--;
        System.out.printf("chef have next portion ready course %d, portion %d\n",numberOfCoursesToDeliver,numberOfPortionsToDeliver);
    }

    /**
     *    Operation continue preparation
     *
     *    Called by the chef to continue preparation
     *    
     */
    public synchronized void continuePreparation() 
    {
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        chefProxy.setChefState(ChefStates.PRPCS);
        int state = chefProxy.getChefState();
        reposStub.setChefState(state);
        numberOfCoursesToDeliver--;
        numberOfPortionsToDeliver = Constants.N;
        System.out.printf("chef continues preparation course %d, portion %d\n",numberOfCoursesToDeliver,numberOfPortionsToDeliver);
    }

    /**
     *    Operation clean up
     *
     *    Called by the chef to clean up
     *    
     */
    public synchronized void cleanUp() 
    {
        System.out.println("chef cleans up");
        chefProxy = (KitchenClientProxy) Thread.currentThread();
        chefProxy.setChefState(ChefStates.CLSSV);
        int state = chefProxy.getChefState();
        reposStub.setChefState(state);
    }

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        nEntities += 1;
        if (nEntities >= Constants.E)
            ServerKitchen.waitConnection = false;
        notifyAll ();                                        // the kitchen may now terminate
    }
}