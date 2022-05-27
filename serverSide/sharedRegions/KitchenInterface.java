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
                                          throw new MessageException ("Invalid chef state 15!", inMessage);
                                        break;

            case MessageType.PRPCS: System.out.printf("chef state = %d\n",inMessage.getChefState ());
                                    if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
                                        throw new MessageException ("Invalid chef state 16!", inMessage);
                                    break;

            case MessageType.GETFIRSTCOURSE: if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
                                                throw new MessageException ("Invalid chef state 17!", inMessage);
                                            break;

            case MessageType.PRCPRES:   if ((inMessage.getChefState () < ChefStates.PRPCS) || (inMessage.getChefState () > ChefStates.DSHPT))
                                            throw new MessageException ("Invalid chef state 18!", inMessage);
                                        break;

            case MessageType.PORDELIV:  if ((inMessage.getChefState () != ChefStates.DLVPT))
                                            throw new MessageException ("Invalid chef state 19!", inMessage);
                                        break;

            case MessageType.POREADY:   if ((inMessage.getChefState () < ChefStates.DSHPT) || (inMessage.getChefState () > ChefStates.DLVPT))
                                            throw new MessageException ("Invalid chef state 20!", inMessage);
                                        break;

            case MessageType.SETFIRSTCS:    if ((inMessage.getChefState () < ChefStates.WAFOR) || (inMessage.getChefState () > ChefStates.DLVPT))
                                                throw new MessageException ("Invalid chef state 21!", inMessage);
                                            break;

            case MessageType.ORDERCOMPREQ:  if ((inMessage.getChefState () != ChefStates.DLVPT))
                                                throw new MessageException ("Invalid chef state 22!", inMessage);
                                            break;

            case MessageType.CLEANREQ:  if ((inMessage.getChefState () < ChefStates.DLVPT) || (inMessage.getChefState () > ChefStates.CLSSV))
                                            throw new MessageException ("Invalid chef state 23!", inMessage);
                                        break;

            case MessageType.NOTEREQ:   if ((inMessage.getWaiterState () < WaiterStates.TKODR) || (inMessage.getWaiterState () > WaiterStates.PCODR))
                                            throw new MessageException ("Invalid waiter state 20!", inMessage);
                                        break;

            case MessageType.CHEFWAIT:    if(inMessage.getChefState() != ChefStates.DLVPT)
                                        throw new MessageException ("Invalid chef state 24", inMessage);
                                      break;   

            case MessageType.PORTIONCOLLECT:    if((inMessage.getWaiterState () < WaiterStates.APPST) || (inMessage.getWaiterState () > WaiterStates.WTFPT))
                                            throw new MessageException ("Invalid waiter state 19", inMessage);
                                            break;  

            case MessageType.SHUT:      // check nothing
                                        break;

            default:                    throw new MessageException ("Invalid message type 56!", inMessage);
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
                                                boolean firstCourse = kitchen.getFirstCourse();
                                                outMessage = new Message (MessageType.FIRSTCOURSE,
                                                    ((KitchenClientProxy) Thread.currentThread ()).getChefState (), firstCourse);
                                                break;

            case MessageType.PRCPRES:   ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        kitchen.proceedToPresentation();
                                        outMessage = new Message (MessageType.PRESDONE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;

            case MessageType.PORDELIV:  ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        boolean portionsDeliv = kitchen.haveAllPortionsBeenDelivered();
                                        outMessage = new Message (MessageType.PORDELIVDONE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState (), portionsDeliv);
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
                                            boolean orderComplete = kitchen.hasTheOrderBeenCompleted();
                                            outMessage = new Message (MessageType.ORDERCOMP,
                                                ((KitchenClientProxy) Thread.currentThread ()).getChefState (), orderComplete);
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

            
            case MessageType.CHEFWAIT:  ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefState ());
                                        kitchen.chefWaitForCollection();
                                        outMessage = new Message (MessageType.CHEFWAITDONE,
                                                ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;  

            case MessageType.PORTIONCOLLECT:    ((KitchenClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiterState ());
                                                kitchen.portionHasBeenCollected();
                                                outMessage = new Message (MessageType.PORTIONCOLLECTDONE,
                                                        ((KitchenClientProxy) Thread.currentThread ()).getWaiterState ());
                                                break;  

            case MessageType.SHUT:      kitchen.shutdown();
                                        outMessage = new Message (MessageType.SHUTDONE); 
                                        break;
        }
        return (outMessage);
    }
    
}
