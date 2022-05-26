package serverSide.sharedRegions;

import commInfra.Message;
import commInfra.MessageException;
import commInfra.MessageType;
import serverSide.entities.KitchenClientProxy;
import clientSide.entities.*;

public class KitchenInterface {

    /**
    *  Reference to the kitchen.
    */

    private final Kitchen kitchen;

    public KitchenInterface(Kitchen kitchen)
    {
        this.kitchen = kitchen;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                                     // outgoing message

        /* validation of the incoming message */

        switch(inMessage.getMsgType())
        {
            case MessageType.WAFOR:     if (inMessage.getChefState () != ChefStates.WAFOR)
                                          throw new MessageException ("Invalid chef state!", inMessage);
                                        break;

            case MessageType.PRPCS: if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.PRPCS))
                                        throw new MessageException ("Invalid chef state!", inMessage);
                                    break;

            case MessageType.GETFIRSTCOURSE: if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
                                                throw new MessageException ("Invalid chef state!", inMessage);
                                            break;

            case MessageType.PRCPRES:   if ((inMessage.getChefState () < ChefStates.PRPCS) || (inMessage.getChefState () > ChefStates.DSHPT))
                                            throw new MessageException ("Invalid chef state!", inMessage);
                                        break;

            case MessageType.PORDELIV:  if ((inMessage.getChefState () != ChefStates.DLVPT))
                                            throw new MessageException ("Invalid chef state!", inMessage);
                                        break;

            case MessageType.POREADY:   if ((inMessage.getChefState () < ChefStates.DSHPT) || (inMessage.getChefState () > ChefStates.DLVPT))
                                            throw new MessageException ("Invalid chef state!", inMessage);
                                        break;

            case MessageType.SETFIRSTCS:    if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
                                                throw new MessageException ("Invalid chef state!", inMessage);
                                            break;

            case MessageType.ORDERCOMPREQ:  if ((inMessage.getChefState () != ChefStates.DLVPT))
                                                throw new MessageException ("Invalid chef state!", inMessage);
                                            break;

            case MessageType.CLEANREQ:  if ((inMessage.getChefState () < ChefStates.DLVPT) || (inMessage.getChefState () > ChefStates.CLSSV))
                                            throw new MessageException ("Invalid chef state!", inMessage);
                                        break;

            case MessageType.NOTEREQ:   if ((inMessage.getWaiterState () < WaiterStates.TKODR) || (inMessage.getWaiterState () > WaiterStates.PCODR))
                                            throw new MessageException ("Invalid waiter state!", inMessage);
                                        break;

            case MessageType.SHUT:      // check nothing
                                        break;

            default:                    throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ())

        { 
            case MessageType.WAFOR: ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                    kitchen.watchTheNews();
                                    outMessage = new Message (MessageType.ORDERDONE,
                                        ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                    break;

            case MessageType.PRPCS: ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                    kitchen.startPreparation();
                                    outMessage = new Message (MessageType.CSPREP,
                                        ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                    break;

            case MessageType.GETFIRSTCOURSE:    ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                                kitchen.getFirstCourse();
                                                outMessage = new Message (MessageType.FIRSTCOURSE,
                                                    ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                                break;

            case MessageType.PRCPRES:   ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        kitchen.proceedToPresentation();
                                        outMessage = new Message (MessageType.PRESDONE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;

            case MessageType.PORDELIV:  ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        kitchen.haveAllPortionsBeenDelivered();
                                        outMessage = new Message (MessageType.PORDELIVDONE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;

            case MessageType.POREADY:   ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        kitchen.haveNextPortionReady();
                                        outMessage = new Message (MessageType.POREADYDONE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;

            case MessageType.SETFIRSTCS:    ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                            kitchen.setFirstCourse(false);
                                            outMessage = new Message (MessageType.SETFIRSTCSDONE,
                                                ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                            break; 

            case MessageType.ORDERCOMPREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                            kitchen.hasTheOrderBeenCompleted();
                                            outMessage = new Message (MessageType.ORDERCOMP,
                                                ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                            break; 

            case MessageType.CLEANREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        kitchen.cleanUp();
                                        outMessage = new Message (MessageType.CLEAN,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break; 

            case MessageType.NOTEREQ:   ((KitchenClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                        kitchen.handTheNoteToChef();
                                        outMessage = new Message (MessageType.NOTE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getWaiterState ());
                                        break;

            
            case MessageType.CHEFWAIT:    if(inMessage.getChefState() != ChefStates.DLVPT)
                                        throw new MessageException ("Invalid chef state", inMessage);
                                      break;   

            case MessageType.PORTIONCOLLECT:    if((inMessage.getWaiterState () < WaiterStates.APPST) || (inMessage.getWaiterState () > WaiterStates.WTFPT))
                                            throw new MessageException ("Invalid waiter state", inMessage);
                                            break;  

            case MessageType.SHUT:      kitchen.shutdown();
                                        outMessage = new Message (MessageType.SHUTDONE); 
                                        break;
        }
        return (outMessage);
    }
    
}
