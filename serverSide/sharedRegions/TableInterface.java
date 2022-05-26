package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.main.*;
import commInfra.*;
import serverSide.entities.TableClientProxy;

public class TableInterface {
    /**
    *  Reference to the bar.
    */

    private final Table table;

    public TableInterface(Table table)
    {
        this.table = table;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;                                     // outgoing message

        /* validation of the incoming message */

        switch(inMessage.getMsgType())
        {
            case MessageType.READMENUREQ:   if ((inMessage.getStudentID () < 0) || (inMessage.getStudentID () >= Constants.N))
                                              throw new MessageException ("Invalid student id!", inMessage);
                                            else if ((inMessage.getStudentState () < StudentStates.TKSTT) || (inMessage.getStudentState () > StudentStates.SELCS))
                                              throw new MessageException ("Invalid student state!", inMessage);
                                            break;

            case MessageType.INFCOMPREQ:    if ((inMessage.getStudentID () < 0) || (inMessage.getStudentID () >= Constants.N))
                                              throw new MessageException ("Invalid student id!", inMessage);
                                            else if((inMessage.getStudentState() < StudentStates.SELCS) || (inMessage.getStudentState() > StudentStates.CHTWC ))
                                              throw new MessageException ("Invalid student state!", inMessage);
                                            break;
                            
            case MessageType.PREPORDERREQ:    if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                              throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState () < StudentStates.SELCS) || (inMessage.getStudentState () > StudentStates.OGODR))
                                              throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.EVBDCHOSENREQ:  if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState() != StudentStates.OGODR))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.ADDUPCHCREQ:   if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState() != StudentStates.OGODR))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.DESCORDERREQ:  if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState() != StudentStates.OGODR))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.JOINTALKREQ:   if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState () < StudentStates.OGODR) || (inMessage.getStudentState () > StudentStates.CHTWC))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.STARTEATREQ:   if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState () < StudentStates.CHTWC) || (inMessage.getStudentState () > StudentStates.EJYML))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.ENDEATREQ:     if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState () < StudentStates.CHTWC) || (inMessage.getStudentState () > StudentStates.EJYML))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.EVBDFINISHREQ: if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState () != StudentStates.OGODR))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.CSREADYREQ: if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                throw new MessageException ("Invalid student id", inMessage);
                                            else if((inMessage.getStudentState () != StudentStates.OGODR))
                                                throw new MessageException ("Invalid student state", inMessage);
                                            break;

            case MessageType.WTPAYREQ:  if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                            throw new MessageException ("Invalid student id", inMessage);
                                        else if((inMessage.getStudentState () != StudentStates.CHTWC))
                                            throw new MessageException ("Invalid student state", inMessage);
                                        break;

            case MessageType.SHARRIVEDEARLIERREQ:   if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                        throw new MessageException ("Invalid student id", inMessage);
                                                    else if((inMessage.getStudentState () < StudentStates.CHTWC) || (inMessage.getStudentState () > StudentStates.PYTBL))
                                                        throw new MessageException ("Invalid student state", inMessage);
                                                    break;

            case MessageType.HONOURBILLREQ:     if((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                    throw new MessageException ("Invalid student id", inMessage);
                                                else if((inMessage.getStudentState () != StudentStates.PYTBL))
                                                    throw new MessageException ("Invalid student state", inMessage);
                                                break;

            case MessageType.SALUTECLIENTREQ:   if((inMessage.getWaiterState () < WaiterStates.APPST) || (inMessage.getWaiterState () > WaiterStates.PRSMN))
                                                    throw new MessageException ("Invalid waiter state", inMessage);
                                                break;                                                

            case MessageType.GETPADREQ:     if((inMessage.getWaiterState () < WaiterStates.APPST) || (inMessage.getWaiterState () > WaiterStates.TKODR))
                                                throw new MessageException ("Invalid waiter state", inMessage);
                                            break;   

            case MessageType.HVCLIENTSBEENSRVREQ:   if(inMessage.getWaiterState () != WaiterStates.WTFPT)
                                                        throw new MessageException ("Invalid waiter state", inMessage);
                                                    break; 

            case MessageType.DELVPTREQ:     if(inMessage.getWaiterState () != WaiterStates.WTFPT)
                                                throw new MessageException ("Invalid waiter state", inMessage);
                                            break; 

            case MessageType.PRESBILLREQ:   if((inMessage.getWaiterState () < WaiterStates.PRCBL) || (inMessage.getWaiterState () > WaiterStates.RECPM))
                                                throw new MessageException ("Invalid waiter state", inMessage);
                                            break; 


            case MessageType.TAKESEAT:    if((inMessage.getStudentState () != StudentStates.GGTRT))
                                        throw new MessageException ("Invalid student state", inMessage);
                                        break;  

            case MessageType.WAITPAD:    if((inMessage.getStudentState () != StudentStates.OGODR))
                                        throw new MessageException ("Invalid student state", inMessage);
                                        break;  

            case MessageType.SHUT:          // check nothing
                                            break;
            default:                   throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ())

        { 
            case MessageType.READMENUREQ:   ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.readMenu();
                                            outMessage = new Message (MessageType.READMENU,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.INFCOMPREQ:    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.informCompanion();
                                            outMessage = new Message (MessageType.INFCOMP,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;
                          
            case MessageType.PREPORDERREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.prepareTheOrder();
                                            outMessage = new Message (MessageType.PREPORDER,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.EVBDCHOSENREQ: ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.hasEverybodyChosen();
                                            outMessage = new Message (MessageType.EVBDCHOSEN,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.ADDUPCHCREQ:   ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.addUpOnesChoice();
                                            outMessage = new Message (MessageType.ADDUPCHC,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.DESCORDERREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.describeTheOrder();
                                            outMessage = new Message (MessageType.DESCORDER,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.JOINTALKREQ:   ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.joinTheTalk();
                                            outMessage = new Message (MessageType.JOINTALK,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.STARTEATREQ:   ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.startEating();
                                            outMessage = new Message (MessageType.STARTEAT,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.ENDEATREQ: ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                        table.endEating();
                                        outMessage = new Message (MessageType.ENDEAT,
                                                ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                        break;

            case MessageType.EVBDFINISHREQ: ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.hasEverybodyFinished();
                                            outMessage = new Message (MessageType.EVBDFINISH,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.CSREADYREQ:    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.waitForCourseToBeReady();
                                            outMessage = new Message (MessageType.CSREADY,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.WTPAYREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                        table.waitForPayment();
                                        outMessage = new Message (MessageType.WTPAY,
                                                ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                        break;

            case MessageType.SHARRIVEDEARLIERREQ:   ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                                    table.shouldHaveArrivedEarlier();
                                                    outMessage = new Message (MessageType.SHARRIVEDEARLIER,
                                                            ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                                    break;

            case MessageType.HONOURBILLREQ: ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                            table.honourTheBill();
                                            outMessage = new Message (MessageType.HONOURBILL,
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;

            case MessageType.SALUTECLIENTREQ:   ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                                //salute the client needs student ID ???
                                                table.saluteTheClient(inMessage.getStudentID());
                                                outMessage = new Message (MessageType.SALUTECLIENT,
                                                        ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                                break;                                          

            case MessageType.GETPADREQ: ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                        table.getThePad();
                                        outMessage = new Message (MessageType.GETPAD,
                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                        break;              

            case MessageType.HVCLIENTSBEENSRVREQ:   ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                                    table.haveAllClientsBeenServed();
                                                    outMessage = new Message (MessageType.HVCLIENTSBEENSRV,
                                                            ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                                    break;  

            case MessageType.DELVPTREQ: ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                        table.deliverPortion();
                                        outMessage = new Message (MessageType.DELVPTREQ,
                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                        break;  

            case MessageType.PRESBILLREQ:   ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                            table.presentTheBill();
                                            outMessage = new Message (MessageType.PRESBILL,
                                                    ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                            break;  

            case MessageType.TAKESEAT:  ((TableClientProxy) Thread.currentThread ()).setStudentState(inMessage.getStudentState ());
                                            outMessage = new Message (MessageType.TAKESEATDONE,((TableClientProxy) Thread.currentThread ()).getStudentID (),
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;  
    
            case MessageType.WAITPAD:   ((TableClientProxy) Thread.currentThread ()).setStudentState(inMessage.getStudentState ());
                                            outMessage = new Message (MessageType.WAITPADDONE,((TableClientProxy) Thread.currentThread ()).getStudentID (),
                                                    ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                            break;  

            case MessageType.SHUT:        table.shutdown();
                                          outMessage = new Message (MessageType.SHUTDONE); 
                                          break;
        }
        return (outMessage);
    } 
}
