package serverSide.sharedRegions;
import commInfra.*;
import serverSide.entities.BarClientProxy;
import serverSide.entities.TableClientProxy;
import serverSide.main.*;
import clientSide.entities.*;
import clientSide.stubs.GeneralReposStub;

/**
 *    Bar
 *
 *    It is responsible for the the synchronization of the Students and Waiter
 *    is implemented as an implicit monitor.
 *    
 *    There are internal synchronization points: 
 *    blocking point for the Waiter, where he waits for the Student to signal
 *    blocking point for the Waiter, where he waits for the Chef to signal
 */
public class Bar {

    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;

    /**
     *   Counter of the number of pending service requests.
     */
    private int numberOfPendingServiceRequests = 0;

    /**
     *   FIFO with the pending service requests.
     */
    private MemFIFO<Request> pendingServiceRequests;

    /**
     *   FIFO with the IDs of the students in order of arrival.
     */
    private MemFIFO<Integer> arrivalQueue;

    /**
     *   Counter of the number of students in restaurant.
     */
    private int numberOfStudentsInRestaurant;

    /**
     *   Reference to the table.
     */
    private Table table;

    /**
     *   Reference to the kitchen.
     */
    private Kitchen kitchen;

    /**
     *   Reference to the general repository.
     */
    private final GeneralReposStub reposStub;

    /**
     *   Array with the IDs of the students in order of arrival.
     */
    private int[] studentsArrival;

    /**
     *   Array of booleans that indicates if waiter said goodbye to student.
     */
    private boolean[] clientsGoodbye;


    /**
     *  Table instantiation.
     *
     *    @param reposStub reference to the General Information Repository Stub
     */
    public Bar(GeneralReposStub reposStub, Table table, Kitchen kitchen)
    {
        try {
            pendingServiceRequests = new MemFIFO(new Request[Constants.N+1]);
        } catch (MemException e) {
            pendingServiceRequests = null;
            e.printStackTrace();
        }
        try {
            arrivalQueue = new MemFIFO(new Integer[Constants.N]);
        } catch (MemException e) {
            arrivalQueue = null;
            e.printStackTrace();
        }
        this.reposStub = reposStub;
        this.table = table;
        this.kitchen = kitchen;
        studentsArrival = new int [Constants.N];
        clientsGoodbye = new boolean[Constants.N];
        nEntities=0;
    }

    /**
     *    Operation look around
     *
     *    Called by the waiter to look around
     *    waits until has requests
     *    @return the request read from the queue
     * 
     */
    public synchronized Request lookAround() 
    {
        BarClientProxy waiter = (BarClientProxy) Thread.currentThread();
        if(waiter.getWaiterState() != WaiterStates.APPST) {
            waiter.setWaiterState(WaiterStates.APPST);
            int state = waiter.getWaiterState();
            reposStub.setWaiterState(state);
        }
        //System.out.println("waiter looking");
        while(numberOfPendingServiceRequests == 0) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Thread interrupted");
            }
        }
        Request request = null;
        try {
            request = pendingServiceRequests.read();
        } catch (MemException e) {
            e.printStackTrace();
        }
        //System.out.print(request.getRequestID());
        //System.out.println(" - " + request.getRequestType());
        numberOfPendingServiceRequests--;
        return request;
    }

    /**
     *    Operation enter
     *
     *    Called by the student to enter in the restaurant
     *    puts a request for the waiter 
     *    signals waiter 
     *    while students are waiting to take a seat at the table
     *    @return array of the students IDs in order of arrival
     * 
     */
    public int[] enter() 
    {
        int studentID;
        synchronized(this)
        {
            BarClientProxy student = (BarClientProxy) Thread.currentThread();
            studentID = student.getStudentID();
            //System.out.printf("student %d enters\n", studentID);
            try {
                arrivalQueue.write(studentID);
            } catch (MemException e1) {
                e1.printStackTrace();
            }
            studentsArrival[numberOfStudentsInRestaurant] = studentID;
            numberOfStudentsInRestaurant++;
            Request r = new Request(studentID, 'c');
            numberOfPendingServiceRequests++;
            try {
                pendingServiceRequests.write(r);
            } catch (MemException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
        table.takeASeat();
        return studentsArrival;
    }

    /**
     *    Operation return to bar
     *
     *    Called by the waiter to return to the bar
     * 
     */
    public synchronized void returnToBar() 
    {
        //System.out.println("waiter is returning to bar");
        BarClientProxy waiter = (BarClientProxy) Thread.currentThread();
        waiter.setWaiterState(WaiterStates.APPST);
        int state = waiter.getWaiterState();
        reposStub.setWaiterState(state);
    }

    /**
     *    Operation call waiter
     *
     *    Called by the student to describe the order
     *    puts a request for the waiter
     *    signals waiter waiting in lookaround
     *    while students are waiting in the table for waiter to get the pad
     * 
     */
    public void callWaiter() 
    {
        //System.out.println("waiter was called");
        synchronized(this) 
        {
            BarClientProxy student = (BarClientProxy)Thread.currentThread();
            int studentID = student.getStudentID();
            Request r = new Request(studentID, 'o');
            numberOfPendingServiceRequests += 1;
            try {
                pendingServiceRequests.write(r);
            } catch (MemException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
        table.waitForPad();
    }

    /**
     *    Operation alert the waiter
     *
     *    Called by the chef for waiter collect the portions
     *    puts a request for the waiter
     *    signals waiter waiting in lookaround
     *    while chef is waiting in the kitchen for the collection
     * 
     */
    public void alertTheWaiter() 
    {
        synchronized(this) 
        {
            //chef's ID is equal to the number of students
            Request r = new Request(Constants.N, 'p');
            numberOfPendingServiceRequests += 1;
            try {
                pendingServiceRequests.write(r);
            } catch (MemException e) {
                e.printStackTrace();
            }
            notifyAll();
            //System.out.println("chef alerts the waiter");
        }
        kitchen.chefWaitForCollection();
    }

    /**
     *    Operation collect portion
     *
     *    Called by the waiter to collect portion
     *    and informing chef in the kitchen that portion was collected
     * 
     */
    public void collectPortion() 
    {
        //System.out.println("waiter is collecting portion");
        synchronized(this)
        {
            BarClientProxy waiter = (BarClientProxy) Thread.currentThread();
            waiter.setWaiterState(WaiterStates.WTFPT);
            int state = waiter.getWaiterState();
            reposStub.setWaiterState(state);
        }
        kitchen.portionHasBeenCollected();
    }

    /**
     *    Operation signal the waiter
     *
     *    Called by the last student to signal the waiter after everybody eaten and is time to pay
     *    puts a request for the waiter
     *    signals waiter waiting in lookaround
     *    
     */
    public void signalTheWaiter() 
    {
        synchronized(this) 
        {
            //System.out.println("waiter has been signaled");
            BarClientProxy student = (BarClientProxy)Thread.currentThread();
            int studentID = student.getStudentID();
            //bill presentation
            Request r = new Request(studentID, 'b');
            numberOfPendingServiceRequests += 1;
            try {
                pendingServiceRequests.write(r);
            } catch (MemException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
    }

    /**
     *    Operation prepare the bill
     *
     *    Called by the waiter to prepare the bill
     * 
     */
    public synchronized void prepareTheBill() 
    {
        BarClientProxy waiter = (BarClientProxy) Thread.currentThread();
        waiter.setWaiterState(WaiterStates.PRCBL);
        int state = waiter.getWaiterState();
        reposStub.setWaiterState(state);
        //System.out.println("waiter preparing the bill");
    }

    /**
     *    Operation exit
     *
     *    Called by the student to exit the restaurant
     *    puts a request for the waiter
     *    signals waiter waiting in lookaround
     *    waits for waiter to say goodbye 
     * 
     */
    public void exit() 
    {
        int studentID;
        synchronized(this)
        {
            BarClientProxy student = (BarClientProxy) Thread.currentThread();
            studentID = student.getStudentID();
            //say goodbye
            Request r = new Request(studentID, 'g');
            numberOfPendingServiceRequests += 1;
            try {
                pendingServiceRequests.write(r);
            } catch (MemException e) {
                e.printStackTrace();
            }
            notifyAll();
            student.setStudentState(StudentStates.GGHOM);
            int stID = student.getStudentID();
            int state = student.getStudentState();
            reposStub.setStudentState(stID, state);
            while(!clientsGoodbye[studentID])
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //System.out.printf("student %d going home\n",studentID);
        }        
    }

    /**
     *    Operation say goodbye
     *
     *    Called by the waiter to say goodbye to the student
     *    signals student that waiter said goodbye
     * 
     *    @param studentID student id
     *    @return number of students in restuarant
     *    
     */
    public synchronized int sayGoodbye(int studentID) 
    {
        //System.out.printf("saying goodbye to student %d\n", studentID);
        clientsGoodbye[studentID] = true;
        notifyAll();
        numberOfStudentsInRestaurant--;
        return numberOfStudentsInRestaurant;
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
            ServerBar.waitConnection = false;
        notifyAll ();                                        // the bar may now terminate
    }
}
