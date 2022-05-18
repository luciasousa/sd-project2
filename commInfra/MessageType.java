package commInfra;

/**
 *   Type of the exchanged messages.
 *the
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class MessageType
{
  /**
   *  Initialization of the logging file name and the number of iterations (service request).
   */
    public static final int SETNFIC = 1;

  /**
   *  Logging file was initialized (reply).
   */
    public static final int NFICDONE = 2;

  /**
   *  Chef wait for an order (request).
   */
  public static final int WAFOR = 4;

  /**
   *  Order done (reply).
   */
  public static final int ORDERDONE = 5;

  /**
   *  Chef prepares course (request).
   */
  public static final int PRPCS = 6;

  /**
   *  Course prepared (reply).
   */
  public static final int CSPREP = 7;

  /**
   *  Get first course (request).
   */
  public static final int GETFIRSTCOURSE = 8;

  /**
   *  First course (reply).
   */
  public static final int FIRSTCOURSE = 9;

  /**
   *  Proceed to presentation (request).
   */
  public static final int PRCPRES = 10;

  /**
   *  Proceed to presentation (reply).
   */
  public static final int PRESDONE = 11;

  /**
   *  Have all portions been delivered (request).
   */
  public static final int PORDELIV = 12;

  /**
   *  Have all portions been delivered (reply).
   */
  public static final int PORDELIVDONE = 13;

  /**
   *  Have next portion ready (request).
   */
  public static final int POREADY = 14;

  /**
   *  Have next portion ready (reply).
   */
  public static final int POREADYDONE = 15;

  /**
   *  Set first course (request).
   */
  public static final int SETFIRSTCS = 16;

  /**
   *  Set first course (reply).
   */
  public static final int SETFIRSTCSDONE = 17;

  /**
   *  Has the order been completed (request).
   */
  public static final int ORDERCOMPREQ = 18;

  /**
   *  Order been complete (reply).
   */
  public static final int ORDERCOMP = 19;

  /**
   *  Clean up (request).
   */
  public static final int CLEANREQ = 20;

  /**
   *  Clean up (reply).
   */
  public static final int CLEAN = 21;

  /**
   *  Hand note to the chef (request).
   */
  public static final int NOTEREQ = 22;

  /**
   *  Hand note to the chef (reply).
   */
  public static final int NOTE = 23;

  /**
   *  Read menu (request).
   */
  public static final int READMENUREQ = 24;

  /**
   *  Read menu (reply).
   */
  public static final int READMENU = 25;

  /**
   *  Inform companion (request).
   */
  public static final int INFCOMPREQ = 26;

  /**
   *  Inform companion (reply).
   */
  public static final int INFCOMP = 27;

  /**
   *   Prepare the order (request).
   */
  public static final int PREPORDERREQ = 28;

  /**
   *   Prepare the order (reply).
   */
  public static final int PREPORDER = 29;

  /**
   *   Has everybody chosen (request).
   */
  public static final int EVBDCHOSENREQ = 30;

  /**
   *   Has everybody chosen (reply).
   */
  public static final int EVBDCHOSEN = 31;

  /**
   *   Add up ones choice (request).
   */
  public static final int ADDUPCHCREQ = 32;

  /**
   *   Add up ones choice (reply).
   */
  public static final int ADDUPCHC = 33;

  /**
   *   Describe the order (request).
   */
  public static final int DESCORDERREQ = 34;

  /**
   *   Describe the order (reply).
   */
  public static final int DESCORDER = 35;

  /**
   *   Join the talk (request).
   */
  public static final int JOINTALKREQ = 36;

  /**
   *   Join the talk (reply).
   */
  public static final int JOINTALK = 37;

  /**
   *   Start eating (request).
   */
  public static final int STARTEATREQ = 38;

  /**
   *   Start eating (reply).
   */
  public static final int STARTEAT = 40;

  /**
   *   End eating (request).
   */
  public static final int ENDEATREQ = 41;

  /**
   *   End eating (reply).
   */
  public static final int ENDEAT = 42;

  /**
   *   Wait for everybody to finish (request).
   */
  public static final int EVBDFINISHREQ = 43;

  /**
   *   Wait for everybody to finish (reply).
   */
  public static final int EVBDFINISH = 44;

  /**
   *   Wait for course to be ready (request).
   */
  public static final int CSREADYREQ = 45;

  /**
   *   Wait for course to be ready (reply).
   */
  public static final int CSREADY = 46;

  /**
   *   Wait for payment (request).
   */
  public static final int WTPAYREQ = 47;

  /**
   *   Wait for payment (reply).
   */
  public static final int WTPAY = 48;

  /**
   *   Should have arrived earlier (request).
   */
  public static final int SHARRIVEDEARLIERREQ = 49;

  /**
   *   Should have arrived earlier (reply).
   */
  public static final int SHARRIVEDEARLIER = 50;

  /**
   *   Honour the bill (request).
   */
  public static final int HONOURBILLREQ = 51;

  /**
   *   Honour the bill (reply).
   */
  public static final int HONOURBILL = 52;

  /**
   *   Salute the client (request).
   */
  public static final int SALUTECLIENTREQ = 53;

  /**
   *   Salute the client (reply).
   */
  public static final int SALUTECLIENT = 54;

  /**
   *   Get the pad (request).
   */
  public static final int GETPADREQ = 55;

  /**
   *   Get the pad (reply).
   */
  public static final int GETPAD = 56;

  /**
   *   Have all clients been served (request).
   */
  public static final int HVCLIENTSBEENSRVREQ = 57;

  /**
   *   Have all clients been served (reply).
   */
  public static final int HVCLIENTSBEENSRV = 58;

  /**
   *   Deliver portion (request).
   */
  public static final int DELVPTREQ = 59;

  /**
   *   Deliver portion (reply).
   */
  public static final int DELVPT = 60;

  /**
   *   Present the bill (request).
   */
  public static final int PRESBILLREQ = 61;

  /**
   *   Present the bill (reply).
   */
  public static final int PRESBILL = 62;

  /**
   *   Alert the waiter (request).
   */
  public static final int ALERTWAITER = 63;

  /**
   *   Alert the waiter (reply).
   */
  public static final int WAITERALERTED = 64;

  /**
   *   Student enters the restaurant (request).
   */
  public static final int ENTERSTUDENT = 65;

  /**
   *   Student enters the restaurant (reply).
   */
  public static final int STUDENTENTERED = 66;

  /**
   *   Call the waiter (request).
   */
  public static final int CALLWAITER = 67;

  /**
   *   Call the waiter (reply).
   */
  public static final int WAITERCALLED = 68;

  /**
   *   Signal the waiter (request).
   */
  public static final int SIGNALWAITER = 69;

  /**
   *   Signal the waiter (reply).
   */
  public static final int WAITERSIGNALED = 70;

  /**
   *   Student exits the restaurant (request).
   */
  public static final int EXITSTUDENT = 71;

  /**
   *   Student exits the restaurant (reply).
   */
  public static final int STUDENTEXITED = 72;

  /**
   *   Waiter looks around (request).
   */
  public static final int LOOKWAITER = 73;

  /**
   *   Waiter looks around (reply).
   */
  public static final int WAITERLOOKED = 74;

  /**
   *   Waiter returns to bar (request).
   */
  public static final int RETURNWAITER = 75;
  
  /**
   *   Waiter returns to bar (reply).
   */
  public static final int WAITERRETURNED = 76;

  /**
   *   Waiter collects the portion (request).
   */
  public static final int COLLECTWAITER = 77;

  /**
   *   Waiter collects the portion (reply).
   */
  public static final int WAITERCOLLECTED = 78;

  /**
   *   Waiter prepares the bill (request).
   */
  public static final int PREPAREWAITER = 79;

  /**
   *   Waiter prepares the bill (reply).
   */
  public static final int WAITERPREPARED = 80;

  /**
   *   Waiter says goodbye to student (request).
   */
  public static final int SAYGOODBYE = 81;

  /**
   *   Waiter says goodbye to student (reply).
   */
  public static final int SAYGOODBYEDONE = 82;




  /**
   *  Server shutdown (service request).
   */
  public static final int SHUT = 26;

  /**
   *  Server was shutdown (reply).
   */
   public static final int SHUTDONE = 27;

  /**
   *  Set chef state (service request).
   */
   public static final int SETCHEFSTATE = 28;

  /**
   *  Set waiter state (service request).
   */
   public static final int SETWAITERSTATE = 29;

  /**
   *  Set student state (service request).
   */
  public static final int SETSTUDENTSTATE = 30;


  /**
   *  Set chef, waiter and student states (service request).
   */
  public static final int SETSTATES = 31;

  /**
   *  Setting acknowledged (reply).
   */

   public static final int SACK = 32;

}
