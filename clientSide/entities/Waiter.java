package clientSide.entities;
import commInfra.Request;
import clientSide.stubs.*;

/**
 *   Waiter thread.
 *
 *   Used to simulate the Waiter life cycle.
 *   Static solution.
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
    private BarStub bar;

    /**
     *  Reference to the kitchen.
     */
    private KitchenStub kitchen;

    /**
     *  Reference to the table.
     */
    private TableStub table;

    /**
     *   Instantiation of a Waiter thread.
     *
     *     @param waiterState waiter state
     *     @param bar reference to the bar
     *     @param kitchen reference to the kicthen
     *     @param table reference to the table
     */
    public Waiter(int waiterState, BarStub bar, KitchenStub kitchen, TableStub table)
    {
        this.waiterState = waiterState;
        this.bar = bar;
        this.kitchen = kitchen;
        this.table = table;
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
            Request r = bar.lookAround();
            switch(r.getRequestType()) 
            {
                case 'c': //client arriving
                    System.out.println("client arriving - 'c'");
                    table.saluteTheClient(r);
                    bar.returnToBar();
                    break;
                
                case 'o': //order ready to be collected
                    System.out.println("order ready to be collected - 'o'");
                    table.getThePad();
                    kitchen.handTheNoteToChef();
                    bar.returnToBar();
                    break;
                
                case 'p': //portion ready to be collected
                    System.out.println("portion ready to be collected - 'p'");
                    if(!table.haveAllClientsBeenServed())
                    {
                        bar.collectPortion();
                        table.deliverPortion();
                        bar.returnToBar();
                    }
                    break;

                case 'b': //bill presentation
                    System.out.println("bill presentation - 'b'");
                    bar.prepareTheBill();
                    table.presentTheBill();
                    bar.returnToBar();
                    break;
                    
                case 'g': //say goodbye to students
                    System.out.println("say goodbye to students - 'g'");
                    int numberOfStudentsInRestaurant = bar.sayGoodbye(r);
                    if(numberOfStudentsInRestaurant == 0) return;
            }
        }
    }
}
