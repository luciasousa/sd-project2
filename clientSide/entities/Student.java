package clientSide.entities;
import clientSide.main.Constants;
import clientSide.stubs.*;

/**
 *   Student thread.
 *
 *   Used to simulate the student life cycle.
 *   Static solution.
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
    private final TableStub table;

    /**
     *  Reference to the bar.
     */
    private final BarStub bar;

    /**
     *   Instantiation of a student thread.
     *
     *     @param studentID student id
     *     @param studentState student state
     *     @param tabel reference to table
     *     @param bar reference to the bar
     */
    public Student(int studentID,int studentState, TableStub table, BarStub bar)
    {
        this.studentID = studentID;
        this.studentState = studentState;
        this.table = table;
        this.bar = bar;
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
        //System.out.println("student thread");
        walkABit();
        int[] orderOfArrival = bar.enter();
        table.readMenu();
        if (orderOfArrival[0] != studentID){
            table.informCompanion();
            //System.out.printf("student %d inform companion left the talk\n",studentID);
        } 
        else
        {
            table.prepareTheOrder();
            while(!table.hasEverybodyChosen()) table.addUpOnesChoice();
            //System.out.println("student is going to call the waiter");
            bar.callWaiter();
            table.describeTheOrder();
            table.joinTheTalk();
        }
        
        for(int i=0; i< Constants.M; i++)
        {
            table.startEating();
            table.endEating();
            //wait for everyone to finish
            if(!table.hasEverybodyFinished())
                table.waitForEverybodyToFinish();
            
            table.waitForCourseToBeReady();
        }

        if(orderOfArrival[Constants.N-1] != studentID) table.waitForPayment();
        
        if(orderOfArrival[Constants.N-1] == studentID) 
        {
            //System.out.println("last student is paying the bill");
            bar.signalTheWaiter();
            table.shouldHaveArrivedEarlier();
            table.honourTheBill();
        }
        bar.exit();
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
