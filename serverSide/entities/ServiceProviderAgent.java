package serverSide.entities;
import commInfra.*;
import serverSide.main.*;

/**
 *    Service provider agent.
 */
public class ServiceProviderAgent extends Thread implements Chef, Waiter, Student
{
    /**
    *  Communication channel.
    */

    private ServerCom com;
    
    /**
     *  Reference to the provided service.
     */
    
    private SharedRegionInterface shi;

   /**
    *  Service to be provided.
    */

   /**
    *  Instantiation.
    *
    *     @param com communication channel
    *     @param shi reference to provided service
    */

    public ServiceProviderAgent (ServerCom com, SharedRegionInterface shi)
    {
       this.com = com;
       this.shi = shi;
    }

   /**
    *  Life cycle of the service provider agent.
    */
    @Override
    public void run ()
    {
        /* service providing */
       Message message = (Message) com.readObject();
       message = shi.processAndReply(message);
       if (message != null) {
    	   com.writeObject(message);
       }
    }

    /**
     *  Student identification.
     */
    private int studentID;

    /**
     *  Student state.
     */
    private int studentState;

    /**
     *   Set Student id.
     *
     *     @param id Student id
     */

    public void setStudentID (int id)
    {
        studentID = id;
    }

    /**
     *   Get Student id.
     *
     *     @return Student Id.
     */
    public int getStudentID ()
    {
        return studentID;
    }

    /**
     *   Set Student state.
     *
     *     @param state Student state
     */
    public void setStudentState (int state)
    {
        studentState = state;
    }

    /**
     *   Get Hostess state.
     *
     *     @return Hostess state.
     */
    public int getStudentState ()
    {
        return studentState;
    }

    /**
     *  Chef state.
     */
    
    private int chefState;

    /**
     *   Set Chef State.
     *
     *     @param state Chef State
     */
    public void setChefState (int state)
    {
        chefState = state;
    }

    /**
     *   Get Chef State.
     *
     *     @return chef state
     */
    public int getChefState ()
    {
        return chefState;
    }

    /**
     *  Waiter state.
     */
    private int waiterState;

    /**
     *   Set Waiter State.
     *
     *     @param state Waiter State
     */
    public void setWaiterState (int state)
    {
        waiterState = state;
    }

    /**
     *   Get Waiter State.
     *
     *     @return Waiter state
     */
    public int getWaiterState ()
    {
        return waiterState;
    }

}
