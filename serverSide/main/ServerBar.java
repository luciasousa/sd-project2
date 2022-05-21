package serverSide.main;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

/**
 *    Server side of the Bar.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class ServerBar {
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
        serverCom.start ();                
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        GeneralReposStub generalReposStub = new GeneralReposStub("l040101-ws08.ua.pt", 22153);
        Bar bar = new Bar(generalReposStub);
        BarInterface barInterface = new BarInterface(bar);
        
        /* service request processing */
                                        // service provider agent
        waitConnection = true;
        while (waitConnection)
        { 
        try {
            sconi = serverCom.accept ();                                     // enter listening procedure
            BarClientProxy barClientProxy = new BarClientProxy(sconi, barInterface);            // start a service provider agent to address
            barClientProxy.start ();      
        } 
        catch(SocketTimeoutException ste) {}
        }
        
        GenericIO.writelnString ("Service is closed!");
    }
}
