package clientSide.stubs;
import commInfra.*;
import genclass.*;

/**
 *  Stub to the general repository.
 *
 *    It instantiates a remote reference to the general repository.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposStub {
    /**
   *  Name of the platform where is located the general repository server.
   */

   private String serverHostName;

   /**
    *  Port number for listening to service requests.
    */
 
    private int serverPortNumb;
 
   /**
    *   Instantiation of a stub to the general repository.
    *
    *     @param serverHostName name of the platform where is located the barber shop server
    *     @param serverPortNumb port number for listening to service requests
    */
 
    public GeneralReposStub (String serverHostName, int serverPortNumb)
    {
       this.serverHostName = serverHostName;
       this.serverPortNumb = serverPortNumb;
    }
 
   /**
    *   Operation initialization of the simulation.
    *
    *     @param fileName logging file name
    *     @param nIter number of iterations of the customer life cycle
    */
 
    public void initSimul (String fileName, int nIter)
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
       outMessage = new Message (MessageType.SETNFIC, fileName, nIter);
       com.writeObject (outMessage);
       inMessage = (Message) com.readObject ();
       if (inMessage.getMsgType() != MessageType.NFICDONE)
          { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
          }
       com.close ();
    }

    /**
   *   Set chef state.
   *
   *     @param state chef state
   */

   public void setChefState (int state)
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
      outMessage = new Message (MessageType.SETCHEFSTATE, state);
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType() != MessageType.SACK)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      com.close ();
   }

  /**
   *   Set waiter state.
   *
   *     @param state customer state
   */

   public void setWaiterState ( int state)
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
      outMessage = new Message (MessageType.SETWAITERSTATE, state);
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType() != MessageType.SACK)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      com.close ();
   }
   
   /**
   *   Set student state.
   *
   *     @param id student id
   *     @param state student state
   */

  public void setStudentState (int id, int state)
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
     outMessage = new Message (MessageType.SETSTUDENTSTATE, id, state);
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType() != MessageType.SACK)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

  /**
   *   Set chef, waiter and student state.
   *
   *     @param chefState barber id
   *     @param waiterState barber state
   *     @param studentID customer id
   *     @param studentState customer state
   */

   public void setChefWaiterStudentState (int chefState, int waiterState, int studentID, int studentState)
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
      outMessage = new Message (MessageType.SETSTATES, chefState, waiterState, studentID, studentState);
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType() != MessageType.SACK)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      com.close ();
   }

   /**
   *   Operation server shutdown.
   *
   *   New operation.
   */

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
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      com.close ();
   }
 
}
