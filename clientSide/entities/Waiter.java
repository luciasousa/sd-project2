package clientSide.entities;
import commInfra.Request;
import clientSide.stubs.*;

/**
 *    Waiter thread.
 *
 *      It simulates the waiter life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public class Waiter extends Thread 
{
    /**
     *  Waiter State.
     */
    private int waiterState;

    /**
     *  Reference to the bar.
     */
    private BarStub barStub;

    /**
     *  Reference to the kitchen.
     */
    private KitchenStub kitchenStub;

    /**
     *  Reference to the table.
     */
    private TableStub tableStub;

    /**
     *   Instantiation of a Waiter thread.
     *
     *     @param waiterState waiter state
     *     @param barStub reference to the barStub
     *     @param kitchenStub reference to the kicthenStub
     *     @param tableStub reference to the tableStub
     */
    public Waiter(int waiterState, BarStub barStub, KitchenStub kitchenStub, TableStub tableStub)
    {
        this.waiterState = waiterState;
        this.barStub = barStub;
        this.kitchenStub = kitchenStub;
        this.tableStub = tableStub;
    }

    /**
     *   Set waiter state.
     *
     *     @param state waiter state
     */
    public void setWaiterState(int state)
    {
        waiterState = state;
    }

    /**
     *   Get waiter state.
     *
     *     @return waiter state
     */
    public int getWaiterState()
    {
        return waiterState;
    }

    /**
     *   Life cycle of the waiter.
     *   
     *   Starts at the state appraising situation (waiting for the arrival of the students)
     *   Ends when all the N students exit the restuarant
     */
    public void run() 
    {
        //System.out.println("waiter thread");
        while(true)
        {
            Request r = barStub.lookAround();
            switch(r.getRequestType()) 
            {
                case 'c': //client arriving
                    //System.out.println("client arriving - 'c'");
                    tableStub.saluteTheClient(r);
                    barStub.returnToBar();
                    break;
                
                case 'o': //order ready to be collected
                    //System.out.println("order ready to be collected - 'o'");
                    tableStub.getThePad();
                    kitchenStub.handTheNoteToChef();
                    barStub.returnToBar();
                    break;
                
                case 'p': //portion ready to be collected
                    //System.out.println("portion ready to be collected - 'p'");
                    if(!tableStub.haveAllClientsBeenServed())
                    {
                        barStub.collectPortion();
                        tableStub.deliverPortion();
                        barStub.returnToBar();
                    }
                    break;

                case 'b': //bill presentation
                    //System.out.println("bill presentation - 'b'");
                    barStub.prepareTheBill();
                    tableStub.presentTheBill();
                    barStub.returnToBar();
                    break;
                    
                case 'g': //say goodbye to students
                    //System.out.println("say goodbye to students - 'g'");
                    int numberOfStudentsInRestaurant = barStub.sayGoodbye(r);
                    if(numberOfStudentsInRestaurant == 0) return;
            }
        }
    }
}
