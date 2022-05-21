package serverSide.sharedRegions;

import commInfra.Message;
import commInfra.MessageException;
import commInfra.MessageType;
import commInfra.Request;
import serverSide.entities.BarClientProxy;
import clientSide.entities.*;
import serverSide.main.Constants;

public class BarInterface
{
    /**
    *  Reference to the bar.
    */

    private final Bar bar;

    public BarInterface(Bar bar)
    {
        this.bar = bar;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;                                     // outgoing message

        /* validation of the incoming message */

        switch(inMessage.getMsgType())
        {
            case MessageType.ALERTWAITER:   if ((inMessage.getChefState () < ChefStates.DSHPT) || (inMessage.getChefState () > ChefStates.DLVPT))
                                              throw new MessageException ("Invalid chef state!", inMessage);
                                            break;

            case MessageType.ENTERSTUDENT:  if ((inMessage.getStudentID () < 0) || (inMessage.getStudentID () >= Constants.N))
                                              throw new MessageException ("Invalid student id!", inMessage);
                                            else if((inMessage.getStudentState() < StudentStates.GGTRT) || (inMessage.getStudentState() > StudentStates.TKSTT ))
                                              throw new MessageException ("Invalid student state!", inMessage);
                                            break;
                            
            case MessageType.CALLWAITER:    if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                              throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState() != StudentStates.OGODR))
                                              throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.SIGNALWAITER:  if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState() != StudentStates.CHTWC))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.EXITSTUDENT:   if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState() < StudentStates.CHTWC) || (inMessage.getStudentState() > StudentStates.GGHOM))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.LOOKWAITER:    if(inMessage.getWaiterState() != WaiterStates.APPST)
                                              throw new MessageException ("Invalid waiter state", inMessage);
                                            break;

            case MessageType.RETURNWAITER:  if(false)
                                              throw new MessageException ("Invalid waiter state", inMessage);
                                            break;

            case MessageType.COLLECTWAITER: if((inMessage.getWaiterState() < WaiterStates.APPST) || (inMessage.getWaiterState() > WaiterStates.WTFPT))
                                              throw new MessageException ("Invalid waiter state", inMessage);
                                            break;

            case MessageType.PREPAREWAITER: if((inMessage.getWaiterState() < WaiterStates.APPST) || (inMessage.getWaiterState() > WaiterStates.PRCBL))
                                              throw new MessageException ("Invalid waiter state", inMessage);
                                            break;                                                

            case MessageType.SAYGOODBYE:    if(inMessage.getWaiterState() != WaiterStates.APPST)
                                              throw new MessageException ("Invalid waiter state", inMessage);
                                            break;   

            case MessageType.SHUT:          // check nothing
                                            break;
            default:                   throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ())

        { 
            case MessageType.ALERTWAITER:   ((BarClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                            bar.alertTheWaiter();
                                            outMessage = new Message (MessageType.WAITERALERTED,
                                                    ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                            break;

            case MessageType.ENTERSTUDENT:  ((BarClientProxy) Thread.currentThread ()).setStudentID (inMessage.getStudentID ());
                                            ((BarClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            int[] orderOfArrival = bar.enter();
                                            outMessage = new Message (MessageType.STUDENTENTERED,
                                                    ((BarClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;
                            
            case MessageType.CALLWAITER:    ((BarClientProxy) Thread.currentThread ()).setStudentID (inMessage.getStudentID ());
                                            ((BarClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            bar.callWaiter();
                                            outMessage = new Message (MessageType.WAITERCALLED,
                                                    ((BarClientProxy) Thread.currentThread ()).getStudentState ());
                                            break; 

            case MessageType.SIGNALWAITER:  ((BarClientProxy) Thread.currentThread ()).setStudentID (inMessage.getStudentID ());
                                            ((BarClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            bar.signalTheWaiter();
                                            outMessage = new Message (MessageType.WAITERSIGNALED,
                                                    ((BarClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.EXITSTUDENT:   ((BarClientProxy) Thread.currentThread ()).setStudentID (inMessage.getStudentID ());
                                            ((BarClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            bar.exit();
                                            outMessage = new Message (MessageType.STUDENTEXITED,
                                                    ((BarClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.LOOKWAITER:    ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiterState ());
                                            Request r = bar.lookAround();
                                            outMessage = new Message (MessageType.WAITERLOOKED,
                                                    ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                            break;

            case MessageType.RETURNWAITER:  ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiterState ());
                                            bar.returnToBar();
                                            outMessage = new Message (MessageType.WAITERRETURNED,
                                                    ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                            break;

            case MessageType.COLLECTWAITER: ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiterState ());
                                            bar.collectPortion();
                                            outMessage = new Message (MessageType.WAITERCOLLECTED,
                                                    ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                            break;

            case MessageType.PREPAREWAITER: ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiterState ());
                                            bar.prepareTheBill();
                                            outMessage = new Message (MessageType.WAITERPREPARED,
                                                    ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                            break;                                             

            case MessageType.SAYGOODBYE:  ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiterState ());
                                          int studentID = inMessage.getStudentID();
                                          bar.sayGoodbye(studentID);
                                          outMessage = new Message (MessageType.SAYGOODBYEDONE,
                                                  ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                          break;  

            case MessageType.SHUT:        bar.shutdown();
                                          outMessage = new Message (MessageType.SHUTDONE); 
                                          break;
        }
        return (outMessage);
    } 
}
