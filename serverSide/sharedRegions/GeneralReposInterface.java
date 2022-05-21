package serverSide.sharedRegions;
import clientSide.entities.ChefStates;
import clientSide.entities.StudentStates;
import clientSide.entities.Waiter;
import clientSide.entities.WaiterStates;
import commInfra.*;
import serverSide.main.Constants;
/**
 *  Interface to the General Repository of Information.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralReposInterface
{
    /**
     *  Reference to the general repository.
     */
    private final GeneralRepos repos;

    /**
     *  Instantiation of an interface to the general repository.
     *
     *    @param repos reference to the general repository
     */
    public GeneralReposInterface(GeneralRepos repos)
    {
       this.repos = repos;
    }
    
    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *
     *    @param inMessage service request
     *    @return service reply
     *    @throws MessageException if the incoming message is not valid
     */
    public Message processAndReply (Message inMessage) throws MessageException
    {
        Message outMessage = null;                                     // mensagem de resposta

        /* validation of the incoming message */

        switch (inMessage.getMsgType ())
        { 
        
            case MessageType.SETNFIC: if (inMessage.getLogFName () == null)
                                        throw new MessageException ("Name of the logging file is not present!", inMessage);
                                        else if (inMessage.getNIter () <= 0)
                                            throw new MessageException ("Wrong number of iterations!", inMessage);
                                    break;

            case MessageType.SETCHEFSTATE:  if ((inMessage.getChefState() < ChefStates.WAFOR) || (inMessage.getChefState() > ChefStates.CLSSV))
                                                throw new MessageException ("Invalid chef state!", inMessage);
                                            break;

            case MessageType.SETWAITERSTATE:    if ((inMessage.getWaiterState() < WaiterStates.APPST) || (inMessage.getWaiterState() > WaiterStates.RECPM))
                                                    throw new MessageException ("Invalid waiter state!", inMessage);
                                                break;

            case MessageType.SETSTUDENTSTATE:   if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                                    throw new MessageException ("Invalid student id!", inMessage);
                                                else if ((inMessage.getStudentState() < StudentStates.GGTRT) || (inMessage.getStudentState() > StudentStates.GGHOM))
                                                    throw new MessageException ("Invalid student state!", inMessage);
                                                break;

            case MessageType.SETSTATES: if ((inMessage.getChefState() < ChefStates.WAFOR) || (inMessage.getChefState() > ChefStates.CLSSV))
                                            throw new MessageException ("Invalid chef state!", inMessage);
                                        
                                        if ((inMessage.getWaiterState() < WaiterStates.APPST) || (inMessage.getWaiterState() > WaiterStates.RECPM))
                                            throw new MessageException ("Invalid waiter state!", inMessage);
                                        
                                        if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() >= Constants.N))
                                            throw new MessageException ("Invalid student id!", inMessage);
                                        else if ((inMessage.getStudentState() < StudentStates.GGTRT) || (inMessage.getStudentState() > StudentStates.GGHOM))
                                            throw new MessageException ("Invalid student state!", inMessage);
                                        break;

            case MessageType.SHUT:     // check nothing
                                        break;

            default:                   throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ())

        { 
            case MessageType.SETNFIC:   repos.initSimul(inMessage.getLogFName (), inMessage.getNIter ());
                                        outMessage = new Message (MessageType.NFICDONE);
                                        break;

            case MessageType.SETCHEFSTATE:  repos.setChefState (inMessage.getChefState());
                                            outMessage = new Message (MessageType.SACK);
                                            break; 

            case MessageType.SETWAITERSTATE:    repos.setWaiterState (inMessage.getWaiterState());
                                                outMessage = new Message (MessageType.SACK);
                                                break; 

            case MessageType.SETSTUDENTSTATE:   repos.setStudentState(inMessage.getStudentID(), inMessage.getStudentState());
                                                outMessage = new Message (MessageType.SACK);
                                                break; 

            case MessageType.SETSTATES: repos.setChefWaiterStudentState (inMessage.getChefState(), inMessage.getWaiterState(), inMessage.getStudentID(), inMessage.getStudentState());
                                        outMessage = new Message (MessageType.SACK);
                                        break;

            case MessageType.SHUT:  repos.shutdown();
                                    outMessage = new Message (MessageType.SHUTDONE);
                                    break; 
        }

        return (outMessage);
    }
}
