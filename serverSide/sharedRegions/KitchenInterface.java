package serverSide.sharedRegions;

import commInfra.Message;
import commInfra.MessageException;
import commInfra.MessageType;
import serverSide.entities.KitchenClientProxy;
import serverSide.main.SharedRegionInterface;
import clientSide.entities.*;

public class KitchenInterface implements SharedRegionInterface {

    /**
    *  Reference to the kitchen.
    */

    private final Kitchen kitchen;

    public KitchenInterface(Kitchen kitchen)
    {
        this.kitchen = kitchen;
    }

    @Override
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                                     // outgoing message

        /* validation of the incoming message */

        switch(inMessage.getMsgType())
        {
            case MessageType.WAFOR:     if (inMessage.getChefState () != ChefStates.WAFOR)
                                          throw new MessageException ("Invalid chef state!", inMessage);
                                        break;

            case MessageType.SHUT:      // check nothing
                                        break;

            default:                    throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ())

        { 
            case MessageType.WAFOR:     ((KitchenClientProxy) Thread.currentThread ()).setChefState (inMessage.getChefState ());
                                        kitchen.watchTheNews();
                                        outMessage = new Message (MessageType.ORDERDONE,
                                            ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;
            case MessageType.SHUT:      kitchen.shutdown();
                                        outMessage = new Message (MessageType.SHUTDONE); 
                                        break;
        }
        return (outMessage);
    }

    @Override
    public boolean hasShutdown() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
