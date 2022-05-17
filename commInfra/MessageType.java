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





  
}
