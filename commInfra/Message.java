package commInfra;

import java.io.*;

import javax.xml.ws.AsyncHandler;

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
   *  Current course number.
   */

   private int nCourses = 0;

   /**
   *  Current portion number.
   */

   private int nPortions = 0;

   /**
   *  Current seat number.
   */

   private int seatNumber = -1;

   /**
   *  Value of boolean
   */

   private boolean boolVal = false;

   /**
   *  Array of students order.
   */

   private int[] arr = null;


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
   *  Request ID
   */

   private int requestID = -1;

   /**
   *  Request type
   */

  private char requestType;

  /**
   *  Number of students in the restaurant
   */

  private int numberOfStudentsInRest = 0;

  /**
   *  String for the type of message
   */
  private String c = "";

  /**
   *  Message instantiation (form 1).
   *
   *     @param type message type
   */

   public Message (int type)
   {
      this.msgType = type;
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
      //System.out.printf("ids: %d\n",id);
      
      this.msgType=type;
      this.studentID= id;
      //System.out.printf("ids: %d\n",this.studentID);
      this.studentState = state;
      //System.out.printf("state: %d\n",this.studentState);
   }

   /**
   *  Message instantiation (form 3).
   *
   *     @param type message type
   *     @param id student identification
   *     @param state student state
   *     @param bool_val value of boolean
   */

  public Message (int type, int id, int state, boolean boolVal)
  {
     //System.out.printf("ids: %d\n",id);
     
     this.msgType=type;
     this.studentID= id;
     //System.out.printf("ids: %d\n",this.studentID);
     this.studentState = state;
     this.boolVal = boolVal;
  }

  /**
   *  Message instantiation (form 4).
   *
   *     @param type message type
   *     @param id student identification
   *     @param state student state
   *     @param arr array of students by order of arrival
   */

  public Message (int type, int id, int state, int[] arr)
  {
     //System.out.printf("ids: %d\n",id);
     
     this.msgType=type;
     this.studentID= id;
     //System.out.printf("ids: %d\n",this.studentID);
     this.studentState = state;
     this.arr = arr;
  }

  /**
   *  Message instantiation (form 5).
   *
   *     @param type message type
   *     @param state waiter state
   *     @param numberOfStudentsInRest number of students in restaurant
   *     @param c string to distingish message
   */
  public Message (int type,int state, int numberOfStudentsInRest, String c){
      this.msgType=type;
      this.waiterState= state;
      //System.out.printf("ids: %d\n",this.studentID);
      this.numberOfStudentsInRest = numberOfStudentsInRest;
      this.c = c;
  }

  /**
   *  Message instantiation (form 6).
   *
   *     @param type message type
   *     @param state waiter state
   *     @param request request
   */

  public Message (int type, int state, Request request)
  {
     this.msgType=type;
     
     if ((type == MessageType.SETWAITERSTATE) || (type == MessageType.SALUTECLIENTREQ) || (type == MessageType.SALUTECLIENT) 
      || (type == MessageType.GETPADREQ) || (type == MessageType.GETPAD) || (type == MessageType.HVCLIENTSBEENSRVREQ) || (type == MessageType.HVCLIENTSBEENSRV)
      || (type == MessageType.DELVPTREQ) || (type == MessageType.DELVPT) || (type == MessageType.PRESBILLREQ) || (type == MessageType.PRESBILL)
      || (type == MessageType.NOTEREQ) || (type == MessageType.NOTE) || (type == MessageType.LOOKWAITER) || (type == MessageType.WAITERLOOKED)
      || (type == MessageType.RETURNWAITER) || (type == MessageType.WAITERRETURNED) || (type == MessageType.COLLECTWAITER) || (type == MessageType.WAITERCOLLECTED)
      || (type == MessageType.PORTIONCOLLECT) || (type == MessageType.PORTIONCOLLECTDONE)|| (type == MessageType.ENDOP) || (type == MessageType.EOPDONE) || (type == MessageType.PREPAREWAITER) || (type == MessageType.WAITERPREPARED) || (type == MessageType.SAYGOODBYE) || (type == MessageType.SAYGOODBYEDONE))
     { 
        this.waiterState = state;
     }
     else if ((type == MessageType.SETCHEFSTATE) || (type == MessageType.ALERTWAITER) || (type == MessageType.WAITERALERTED)
      || (type == MessageType.WAFOR) || (type == MessageType.ORDERDONE) || (type == MessageType.PRPCS) || (type == MessageType.CSPREP)
      || (type == MessageType.GETFIRSTCOURSE) || (type == MessageType.FIRSTCOURSE) || (type == MessageType.PRCPRES) || (type == MessageType.PRESDONE)
      || (type == MessageType.PORDELIV) || (type == MessageType.PORDELIVDONE) || (type == MessageType.POREADY) || (type == MessageType.POREADYDONE)
      || (type == MessageType.SETFIRSTCS) || (type == MessageType.SETFIRSTCSDONE) || (type == MessageType.ORDERCOMPREQ) || (type == MessageType.ORDERCOMP)
      || (type == MessageType.CLEANREQ) || (type == MessageType.CHEFWAIT) || (type == MessageType.CHEFWAITDONE) || (type == MessageType.ENDOP) || (type == MessageType.EOPDONE) || (type == MessageType.CLEAN))
     { 
        this.chefState = state;
     }
     this.requestID = request.getRequestID();
     this.requestType = request.getRequestType();
  }

  /**
   *  Message instantiation (form 7).
   *
   *     @param type message type
   *     @param state chef state
   *     @param boolVal boolean value
   */

  public Message (int type, int state, Boolean boolVal)
  {
     //System.out.printf("ids: %d\n",id);
     
     this.msgType=type;
     //System.out.printf("ids: %d\n",this.studentID);
     if ((type == MessageType.SETWAITERSTATE) || (type == MessageType.SALUTECLIENTREQ) || (type == MessageType.SALUTECLIENT) 
      || (type == MessageType.GETPADREQ) || (type == MessageType.GETPAD) || (type == MessageType.HVCLIENTSBEENSRVREQ) || (type == MessageType.HVCLIENTSBEENSRV)
      || (type == MessageType.DELVPTREQ) || (type == MessageType.DELVPT) || (type == MessageType.PRESBILLREQ) || (type == MessageType.PRESBILL)
      || (type == MessageType.NOTEREQ) || (type == MessageType.NOTE) || (type == MessageType.LOOKWAITER) || (type == MessageType.WAITERLOOKED)
      || (type == MessageType.RETURNWAITER) || (type == MessageType.WAITERRETURNED) || (type == MessageType.COLLECTWAITER) || (type == MessageType.WAITERCOLLECTED)
      || (type == MessageType.PORTIONCOLLECT) || (type == MessageType.PORTIONCOLLECTDONE)|| (type == MessageType.ENDOP) || (type == MessageType.EOPDONE) || (type == MessageType.PREPAREWAITER) || (type == MessageType.WAITERPREPARED) || (type == MessageType.SAYGOODBYE) || (type == MessageType.SAYGOODBYEDONE))
     { 
        this.waiterState = state;
     }
     else if ((type == MessageType.SETCHEFSTATE) || (type == MessageType.ALERTWAITER) || (type == MessageType.WAITERALERTED)
      || (type == MessageType.WAFOR) || (type == MessageType.ORDERDONE) || (type == MessageType.PRPCS) || (type == MessageType.CSPREP)
      || (type == MessageType.GETFIRSTCOURSE) || (type == MessageType.FIRSTCOURSE) || (type == MessageType.PRCPRES) || (type == MessageType.PRESDONE)
      || (type == MessageType.PORDELIV) || (type == MessageType.PORDELIVDONE) || (type == MessageType.POREADY) || (type == MessageType.POREADYDONE)
      || (type == MessageType.SETFIRSTCS) || (type == MessageType.SETFIRSTCSDONE) || (type == MessageType.ORDERCOMPREQ) || (type == MessageType.ORDERCOMP)
      || (type == MessageType.CLEANREQ) || (type == MessageType.CHEFWAIT) || (type == MessageType.CHEFWAITDONE) || (type == MessageType.ENDOP) || (type == MessageType.EOPDONE) || (type == MessageType.CLEAN))
     { 
        this.chefState = state;
     }
     this.boolVal=boolVal;
  }

   /**
   *  Message instantiation (form 8).
   *
   *     @param type message type
   *     @param state chef/waiter state
   */
  public Message (int type, int state)
  {
     this.msgType = type;

     if ((type == MessageType.SETWAITERSTATE) || (type == MessageType.SALUTECLIENTREQ) || (type == MessageType.SALUTECLIENT) 
      || (type == MessageType.GETPADREQ) || (type == MessageType.GETPAD) || (type == MessageType.HVCLIENTSBEENSRVREQ) || (type == MessageType.HVCLIENTSBEENSRV)
      || (type == MessageType.DELVPTREQ) || (type == MessageType.DELVPT) || (type == MessageType.PRESBILLREQ) || (type == MessageType.PRESBILL)
      || (type == MessageType.NOTEREQ) || (type == MessageType.NOTE) || (type == MessageType.LOOKWAITER) || (type == MessageType.WAITERLOOKED)
      || (type == MessageType.RETURNWAITER) || (type == MessageType.WAITERRETURNED) || (type == MessageType.COLLECTWAITER) || (type == MessageType.WAITERCOLLECTED)
      || (type == MessageType.PORTIONCOLLECT) || (type == MessageType.PORTIONCOLLECTDONE)|| (type == MessageType.ENDOP) || (type == MessageType.EOPDONE) || (type == MessageType.PREPAREWAITER) || (type == MessageType.WAITERPREPARED) || (type == MessageType.SAYGOODBYE) || (type == MessageType.SAYGOODBYEDONE))
     { 
         this.waiterState = state;
     }
     else if ((type == MessageType.SETCHEFSTATE) || (type == MessageType.ALERTWAITER) || (type == MessageType.WAITERALERTED)
      || (type == MessageType.WAFOR) || (type == MessageType.ORDERDONE) || (type == MessageType.PRPCS) || (type == MessageType.CSPREP)
      || (type == MessageType.GETFIRSTCOURSE) || (type == MessageType.FIRSTCOURSE) || (type == MessageType.PRCPRES) || (type == MessageType.PRESDONE)
      || (type == MessageType.PORDELIV) || (type == MessageType.PORDELIVDONE) || (type == MessageType.POREADY) || (type == MessageType.POREADYDONE)
      || (type == MessageType.SETFIRSTCS) || (type == MessageType.SETFIRSTCSDONE) || (type == MessageType.ORDERCOMPREQ) || (type == MessageType.ORDERCOMP)
      || (type == MessageType.CLEANREQ) || (type == MessageType.CHEFWAIT) || (type == MessageType.CHEFWAITDONE) || (type == MessageType.ENDOP) || (type == MessageType.EOPDONE) || (type == MessageType.CLEAN))
     { 
         this.chefState = state;
     }
     else if(type == MessageType.SETNUMBERCOURSES) {
         this.nCourses = state;
     }
     else if(type == MessageType.SETNUMBERPORTIONS) {
         this.nPortions = state;
     }
     else if(type == MessageType.SETSEAT) {
         this.seatNumber = state;
  }
     else { GenericIO.writelnString ("Message type = " + type + ": non-implemented instantiation!");
         System.exit (1);
     }
  }



  /**
   *  Message instantiation (form 9).
   *
   *     @param type message type
   *     @param studentID student identification
   *     @param studentState student state
   *     @param waiterState waiter state
   *     @param chefState chef state
   */

   public Message (int type, int studentID, int studentState, int waiterState, int chefState)
   {
      this.msgType = type;
      this.studentID= studentID;
      this.studentState = studentState;
      this.waiterState= waiterState;
      this.chefState = chefState;
   }

  /**
   *  Message instantiation (form 10).
   *
   *     @param type message type
   *     @param name name of the logging file
   *     @param nIter number of iterations of the student life cycle
   */

   public Message (int type, String name, int nIter)
   {
      this.msgType = type;
      this.fName= name;
      this.nIter = nIter;
   }

   /**
   *  Message instantiation (form 11).
   *     @param type message type
   *     @param id request id
   *     @param state student state
   *     @param type_req request type 
   */

  public Message (int type,int id,int state, char type_req)
  {
     this.msgType=type;
     this.studentID = id;
     this.requestID = id;
     this.studentState=state;
     this.requestType= type_req;
  }

  /**
   *  Getting message type.
   *
   *     @return message type
   */

   public int getMsgType ()
   {
      return (this.msgType);
   }

  /**
   *  Getting chef state.
   *
   *     @return chef state
   */

   public int getChefState ()
   {
      return (this.chefState);
   }

  /**
   *  Getting student identification.
   *
   *     @return student identification
   */

   public int getStudentID ()
   {
      return (this.studentID);
   }

  /**
   *  Getting student state.
   *
   *     @return student state
   */

   public int getStudentState ()
   {
      return (this.studentState);
   }

   /**
   *  Getting waiter state.
   *
   *     @return waiter state
   */

   public int getWaiterState ()
   {
      return (this.waiterState);
   }

  /**
   *  Getting end of operations flag.
   *
   *     @return end of operations flag
   */

   public boolean getEndOp ()
   {
      return (this.endOp);
   }

   /**
   *  Getting array flag.
   *
   *     @return end of operations flag
   */

  public int[] getArr ()
  {
     return (this.arr);
  }

   /**
   *  Getting boolean value flag.
   *
   *     @return boolean value flag
   */
   public boolean getBoolVal()
   {
      return (this.boolVal);
   }


  /**
   *  Getting name of logging file.
   *
   *     @return name of the logging file
   */

   public String getLogFName ()
   {
      return (this.fName);
   }

  /**
   *  Getting the number of iterations of the student life cycle.
   *
   *     @return number of iterations of the student life cycle
   */

   public int getNIter ()
   {
      return (this.nIter);
   }

   /**
   *  Getting the number of students in restaurant.
   *
   *     @return number of students in restaurant
   */
   public int getNumberOfStudentsInRestaurant(){
      return (this.numberOfStudentsInRest);
   }

   /**
   *  Getting the request.
   *
   *     @return request
   */
   public Request getRequest() {
      return new Request(this.requestID, this.requestType);
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
              "\nEnd of Operations = " + endOp +
              "\nName of logging file = " + fName +
              "\nNumber of iterations = " + nIter);
   }

   /**
   *  Getting the current course number.
   *
   *     @return current course number
   */

   public int getNumberOfCourses() {
      return (this.nCourses);
   }

   /**
   *  Getting the current portion number.
   *
   *     @return current portion number
   */

   public int getNumberOfPortions() {
      return (this.nPortions);
   }

   /**
   *  Getting the current seat number.
   *
   *     @return current seat number
   */

   public int getSeatNumber() {
      return (this.seatNumber);
   }

}
