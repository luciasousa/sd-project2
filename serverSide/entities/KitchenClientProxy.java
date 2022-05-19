package serverSide.entities;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.sharedRegions.*;

public class KitchenClientProxy extends Thread implements ChefCloning, WaiterCloning, StudentCloning{
     /**
   *  Number of instantiayed threads.
   */

   private static int nProxy = 0;

   /**
    *  Communication channel.
    */
 
    private ServerCom sconi;
 
   /**
    *  Interface to the Restaurant.
    */
 
    private KitchenInterface kitInter;

    /**
     *  Chef state.
     */
    private int chefState;

    /**
     *  Student identification.
     */
    private int studentID;

    /**
     *  Student State.
     */
    private int studentState;
    /**
     *  Waiter State.
     */
    private int waiterState;

    public KitchenClientProxy(ServerCom sconi, KitchenInterface kitInter){
        super ("KItchenProxy_" + KitchenClientProxy.getProxyId());
        this.sconi = sconi;
        this.kitInter = kitInter;
    }

    /**
     *  Generation of the instantiation identifier.
     *
     *     @return instantiation identifier
     */

    private static int getProxyId ()
    {
        Class<?> cl = null;                                            // representation of the BarberShopClientProxy object in JVM
        int proxyId;                                                   // instantiation identifier

        try
        {
            cl = Class.forName ("serverSide.entities.BarberShopClientProxy");
        }
        catch (ClassNotFoundException e)
        { 
            GenericIO.writelnString ("Data type BarberShopClientProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { 
            proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }

    /**
     *   Set chef state.
     *
     *     @param state chef state
     */
    public void setChefState(int state)
    {
        chefState = state;
    }

    /**
     *   Get chef state.
     *
     *   @return chef state
     */
    public int getChefState()
    {
        return chefState;
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
     *  Life cycle of the service provider agent.
     */

    @Override
    public void run ()
    {
        Message inMessage = null,                                      // service request
                outMessage = null;                                     // service reply

        /* service providing */

        inMessage = (Message) sconi.readObject ();                     // get service request
        try
        { outMessage = kitInter.processAndReply (inMessage);         // process it
        }
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }
}
