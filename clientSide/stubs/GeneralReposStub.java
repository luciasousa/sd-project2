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
     *  Counter with the number of course.
     */
    private int numberOfCourse;

    /**
     *  Counter with the number of portion.
     */
    private int numberOfPortion;    

    /**
     *  Array with the order that students sat down
     */
    private int[] seatOrder;

    private int seatNumber = 0;
 
   /**
    *   Instantiation of a stub to the general repository.
    *
    *     @param serverHostName name of the platform where is located the server
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
    *     @param nIter number of iterations of the student life cycle
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
   *     @param state waiter state
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
   *     @param chefState chef state
   *     @param waiterState waiter state
   *     @param studentID student id
   *     @param studentState student state
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
    *   Update Counter numberOfPortion
    *
    *     @param nPortions integer
    */
   public synchronized void setNumberOfPortions(int nPortions)
   {
      numberOfPortion = nPortions;
   }

   /**
    *   Update Counter numberOfCourse
    *
    *     @param nCourses integer
    */
   public synchronized void setNumberOfCourses(int nCourses)
   {
      numberOfCourse = nCourses;
   }

   /**
   *   Update Counter numberOfPortions and numberOfCourses
   *
   *     @param nPortions integer
   *     @param nCourses integer
   */

   public synchronized void setNumberOfPortionsAndCourses(int nPortions, int nCourses)
   {
      numberOfPortion = nPortions;
      numberOfCourse = nCourses;
   }

   /**
   *   Update Array seatOrder
   *
   *     @param id integer
   */

   public synchronized void setSeatOrder(int id)
   {
      seatOrder[seatNumber] = id;
      seatNumber += 1;
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
