package serverSide.main;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

/**
 *    Server side of the General Repository Shared Region.
 *
 *    Request serialization variant.
 *    It waits for the start of the message exchange.
 */
public class ServerGeneralRepos {

    /**
     *  Flag signaling the service is active.
     */

    public static boolean waitConnection;
   
    /**
     *    Main method.
     *
     *    @param args runtime arguments
     */

    public static void main (String[] args)
    {
        /* service is established */

        ServerCom serverCom, sconi;                                        // communication channels
        int portNumb = 22153;                                          // port nunber for listening to service requests

        serverCom = new ServerCom (portNumb);
        serverCom.start ();                             // service is instantiated
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        GeneralRepos generalRepos = new GeneralRepos("logger");
        GeneralReposInterface generalReposInterface = new GeneralReposInterface(generalRepos);
        
        /* service request processing */
                                        // service provider agent
        waitConnection = true;
        while (waitConnection){
        try {
            sconi = serverCom.accept ();                                     // enter listening procedure
            GeneralReposClientProxy generalReposClientProxy = new GeneralReposClientProxy (sconi, generalReposInterface);            // start a service provider agent to address
            generalReposClientProxy.start ();      
        } 
        catch(SocketTimeoutException ste) {}
        }
        
        GenericIO.writelnString ("Service is closed!");
    }
}
