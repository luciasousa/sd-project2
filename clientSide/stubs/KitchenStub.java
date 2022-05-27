package clientSide.stubs;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Stub to the Kitchen.
 *
 *    It instantiates a remote reference to the kitchen.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class KitchenStub {
    /**
    *  Name of the computational system where the server is located.
    */
    private String serverHostName;

    /**
    *  Number of the listening port at the computational system where the server is located.
    */
    private int serverPortNumb;

    /**
    *  Instantiation of a remote reference
    *
    *    @param hostName name of the computational system where the server is located
    *    @param port number of the listening port at the computational system where the server is located
    */
    public KitchenStub (String hostName, int port)
    {
       serverHostName = hostName;
       serverPortNumb = port;
    }

    public void watchTheNews() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message

        while (!com.open ())       // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }

        outMessage = new Message (MessageType.WAFOR, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.ORDERDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 18!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () != ChefStates.WAFOR))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 2!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());
    }

    public void startPreparation() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.PRPCS, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.CSPREP)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 19!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.PRPCS))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 3!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());
    }

    public boolean getFirstCourse() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }

        outMessage = new Message (MessageType.GETFIRSTCOURSE, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.FIRSTCOURSE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 20!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 4!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());

        return (inMessage.getBoolVal());
    }

    public void proceedToPresentation() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }

        outMessage = new Message (MessageType.PRCPRES, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.PRESDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 21!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () < ChefStates.PRPCS) || (inMessage.getChefState () > ChefStates.DSHPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 5!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());

    }

    public boolean haveAllPortionsBeenDelivered() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.PORDELIV, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.PORDELIVDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 22!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () != ChefStates.DLVPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 6!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());

        return (inMessage.getBoolVal());
    }

    public void haveNextPortionReady() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.POREADY, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.POREADYDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 23!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () < ChefStates.DSHPT) || (inMessage.getChefState () > ChefStates.DLVPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 7!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());

    }

    //O QUE FAZER COM O BOOLEAN
    public void setFirstCourse(boolean b) {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SETFIRSTCS, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.SETFIRSTCSDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 24!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 8!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());

    }

    public boolean hasTheOrderBeenCompleted() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.ORDERCOMPREQ, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.ORDERCOMP)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 25!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () != ChefStates.DLVPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 9!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());

        return (inMessage.getBoolVal());
    }

    public void cleanUp() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.CLEANREQ, ((Chef) Thread.currentThread()).getChefState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.CLEAN)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 26!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () < ChefStates.DLVPT) || (inMessage.getChefState () > ChefStates.CLSSV))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 10!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());
    }

    public void handTheNoteToChef() {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.NOTEREQ, ((Waiter) Thread.currentThread()).getWaiterState());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.NOTE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 27!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getWaiterState () < WaiterStates.TKODR) || (inMessage.getWaiterState () > WaiterStates.PCODR))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid waiter state 5!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        ((Waiter) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
    }

    public void shutdown ()
    {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
            { Thread.sleep ((long) (1000));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SHUT);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.SHUTDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 28!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public void chefWaitForCollection(int chefState) 
    {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.CHEFWAIT, chefState);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.CHEFWAITDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 29!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getChefState () != ChefStates.DLVPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state 11!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        //((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState ());
    }

    public void portionHasBeenCollected(int waiterState) 
    {
        // communication channel
        ClientCom com = new ClientCom (serverHostName, serverPortNumb);
        Message outMessage,        // outgoing message
        inMessage;                 // incoming message
        while (!com.open ())                                           // waits for a connection to be established
        { try
            { Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.PORTIONCOLLECT, waiterState);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType () != MessageType.PORTIONCOLLECTDONE)
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type 30!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getWaiterState () < WaiterStates.APPST) || (inMessage.getWaiterState () > WaiterStates.WTFPT))
        { 
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid waiter state 6!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        //((Waiter) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
    }
}
