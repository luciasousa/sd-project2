package serverSide.entities;
import serverSide.main.*;
import serverSide.sharedRegions.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

/**
 *  Interface to the General Repository of Information.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposClientProxy {
    /**
   *  Reference to the general repository.
   */

   private final GeneralRepos repos;

   /**
    *  Instantiation of an interface to the general repository.
    *
    *    @param repos reference to the general repository
    */
 
    public GeneralReposInterface (GeneralRepos repos)
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
           
       }
 
      /* processing */
 
       switch (inMessage.getMsgType ())
 
       { 
           
       }
 
      return (outMessage);
    }
}
