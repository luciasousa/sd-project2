package clientSide.entities;
import clientSide.main.Constants;
import clientSide.stubs.*;

/**
 *    Student thread.
 *
 *      It simulates the student life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public class Student extends Thread 
{
    /**
     *  Student identification.
     */
    private int studentID;

    /**
     *  Student State.
     */
    private int studentState;

    /**
     *  Reference to the table.
     */
    private final TableStub tableStub;

    /**
     *  Reference to the bar.
     */
    private final BarStub barStub;

    /**
     *   Instantiation of a student thread.
     *
     *     @param studentID student id
     *     @param studentState student state
     *     @param tableStub reference to tableStub
     *     @param barStub reference to the barStub
     */
    public Student(int studentID,int studentState, TableStub tableStub, BarStub barStub)
    {
        this.studentID = studentID;
        this.studentState = studentState;
        this.tableStub = tableStub;
        this.barStub = barStub;
    }

    /**
     *   Set student id.
     *
     *     @param id student id
     */
    public void setStudentID(int id){
        studentID = id;
    }

    /**
     *   Get student id.
     *  
     *   @return stduent id
     */
    public int getStudentID(){
        return studentID;
    }

    /**
     *   Set student state.
     *
     *     @param state student state
     */
    public void setStudentState(int state){
        studentState = state;
    }

    /**
     *   Get student state.
     *
     *   @return student state
     */
    public int getStudentState(){
        return studentState;
    }

    /**
     *   Life cycle of the student.
     *   
     *   Starts at the state going to the restaurant 
     *   Ends when the student exits the restaurant
     * 
     */
    public void run() 
    {
        //System.out.println("student walk a bit");
        walkABit();
        //System.out.println("student enter");
        int[] orderOfArrival = barStub.enter();
        //System.out.println("student read menu");
        tableStub.readMenu();
        if (orderOfArrival[0] != studentID){
            tableStub.informCompanion();
            //System.out.printf("student %d inform companion left the talk\n",studentID);
        } 
        else
        {
            tableStub.prepareTheOrder();
            while(!tableStub.hasEverybodyChosen()) 
                tableStub.addUpOnesChoice();
            //System.out.println("student is going to call the waiter");
            barStub.callWaiter();
            tableStub.describeTheOrder();
            tableStub.joinTheTalk();
        }
        
        for(int i=0; i< Constants.M; i++)
        {
            tableStub.startEating();
            tableStub.endEating();
            //wait for everyone to finish
            if(!tableStub.hasEverybodyFinished())
                tableStub.waitForEverybodyToFinish();
            
            tableStub.waitForCourseToBeReady();
        }

        if(orderOfArrival[Constants.N-1] != studentID) tableStub.waitForPayment();
        
        if(orderOfArrival[Constants.N-1] == studentID) 
        {
            //System.out.println("last student is paying the bill");
            barStub.signalTheWaiter();
            tableStub.shouldHaveArrivedEarlier();
            tableStub.honourTheBill();
        }
        barStub.exit();
    }

    /**
     *  Living normal life.
     *
     *  Internal operation.
     */
    private void walkABit() {
        try { 
            Thread.sleep ((long) (1 + 40 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }
}
