package serverSide.main;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

/**
 *    Server side of the Kitchen Shared Region.
 *
 *    Request serialization variant.
 *    It waits for the start of the message exchange.
 */
public class ServerKitchen {
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
        int portNumb = 22150;                                          // port nunber for listening to service requests

        serverCom = new ServerCom (portNumb);
        serverCom.start ();                             // service is instantiated
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        GeneralReposStub generalReposStub = new GeneralReposStub("l040101-ws08.ua.pt", 22153);
        Kitchen kitchen = new Kitchen(generalReposStub);
        KitchenInterface kitchenInterface = new KitchenInterface(kitchen);
        
        /* service request processing */
                                        // service provider agent
        waitConnection = true;
        while (waitConnection)
        { 
        try {
            sconi = serverCom.accept ();                                     // enter listening procedure
            KitchenClientProxy kitchenClientProxy = new KitchenClientProxy (sconi, kitchenInterface);            // start a service provider agent to address
            kitchenClientProxy.start ();      
        } 
        catch(SocketTimeoutException ste) {}
        }
        
        GenericIO.writelnString ("Service is closed!");
    }
}
