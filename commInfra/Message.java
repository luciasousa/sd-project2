package commInfra;

import java.io.*;
import genclass.GenericIO;

/**
 *   Internal structure of the exchanged messages.
 *
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable
{
  /**
   *  Serialization key.
   */

   private static final long serialVersionUID = 2021L;

  /**
   *  Message type.
   */

   private int msgType = -1;

  /**
   *  Chef state.
   */

   private int chefState = -1;

   /**
   *  Waiter state.
   */

  private int waiterState = -1;

  /**
   *  Student identification.
   */

   private int studentID = -1;

  /**
   *  Student state.
   */

   private int studentState = -1;

  /**
   *  End of operations.
   */

   private boolean endOp = false;

  /**
   *  Name of the logging file.
   */

   private String fName = null;

  /**
   *  Number of iterations of the student life cycle.
   */

   private int nIter = -1;

  /**
   *  Message instantiation (form 1).
   *
   *     @param type message type
   */

   public Message (int type)
   {
      msgType = type;
   }

  /**
   *  Message instantiation (form 2).
   *
   *     @param type message type
   *     @param id student identification
   *     @param state student state
   */

   public Message (int type, int id, int state)
   {
      msgType = type;
      //TODO: meter nas message type as mensagens que pertencem a cada entidade
      if ((msgType == MessageType.SETSTUDENTSTATE) || (msgType == MessageType.CALLCUST) || (msgType == MessageType.RPAYDONE))
      { 
         studentID= id;
         studentState = state;
      }
      else if ((msgType == MessageType.SETWAITERSTATE) || (msgType == MessageType.REQCUTH) || (msgType == MessageType.CUTHDONE) ||
               (msgType == MessageType.BSHOPF))
      { 
         waiterState = state;
      }
      else if ((msgType == MessageType.SETCHEFSTATE) || (msgType == MessageType.REQCUTH) || (msgType == MessageType.CUTHDONE) ||
               (msgType == MessageType.BSHOPF))
      { 
         chefState = state;
      }
      else { GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation!");
               System.exit (1);
      }
   }

  /**
   *  Message instantiation (form 3).
   *
   *     @param type message type
   *     @param id student identification
   */

   public Message (int type, int id)
   {
      msgType = type;
      studentID= id;
   }

  /**
   *  Message instantiation (form 4).
   *
   *     @param type message type
   *     @param id student identification
   *     @param endOP end of operations flag
   */

   public Message (int type, int id, boolean endOp)
   {
      msgType = type;
      studentID= id;
      this.endOp = endOp;
   }

  /**
   *  Message instantiation (form 5).
   *
   *     @param type message type
   *     @param studentID student identification
   *     @param studentState student state
   *     @param waiterState waiter state
   */

   public Message (int type, int studentID, int studentState, int waiterState)
   {
      msgType = type;
      this.studentID= studentID;
      this.studentState = studentState;
      this.waiterState = waiterState;
   }

  /**
   *  Message instantiation (form 6).
   *
   *     @param type message type
   *     @param studentID student identification
   *     @param studentState student state
   *     @param waiterState waiter state
   *     @param chefState chef state
   */

   public Message (int type, int studentID, int studentState, int waiterState, int chefState)
   {
      msgType = type;
      this.studentID= studentID;
      this.studentState = studentState;
      this.waiterState= waiterState;
      this.chefState = chefState;
   }

  /**
   *  Message instantiation (form 7).
   *
   *     @param type message type
   *     @param name name of the logging file
   *     @param nIter number of iterations of the customer life cycle
   */

   public Message (int type, String name, int nIter)
   {
      msgType = type;
      fName= name;
      this.nIter = nIter;
   }

  /**
   *  Getting message type.
   *
   *     @return message type
   */

   public int getMsgType ()
   {
      return (msgType);
   }

  /**
   *  Getting chef state.
   *
   *     @return chef state
   */

   public int getChefState ()
   {
      return (chefState);
   }

  /**
   *  Getting student identification.
   *
   *     @return student identification
   */

   public int getStudentID ()
   {
      return (studentID);
   }

  /**
   *  Getting student state.
   *
   *     @return student state
   */

   public int getStudentState ()
   {
      return (studentState);
   }

   /**
   *  Getting waiter state.
   *
   *     @return waiter state
   */

   public int getWaiterState ()
   {
      return (waiterState);
   }

  /**
   *  Getting end of operations flag (barber).
   *
   *     @return end of operations flag
   */

   public boolean getEndOp ()
   {
      return (endOp);
   }

  /**
   *  Getting name of logging file.
   *
   *     @return name of the logging file
   */

   public String getLogFName ()
   {
      return (fName);
   }

  /**
   *  Getting the number of iterations of the customer life cycle.
   *
   *     @return number of iterations of the customer life cycle
   */

   public int getNIter ()
   {
      return (nIter);
   }

  /**
   *  Printing the values of the internal fields.
   *
   *  It is used for debugging purposes.
   *
   *     @return string containing, in separate lines, the pair field name - field value
   */

   @Override
   public String toString ()
   {
      return ("Message type = " + msgType +
              "\nChef State = " + chefState +
              "\nWaiter State = " + waiterState +
              "\nStudent Id = " + studentID +
              "\nStudent State = " + studentState +
              "\nEnd of Operations (barber) = " + endOp +
              "\nName of logging file = " + fName +
              "\nNumber of iterations = " + nIter);
   }
}
