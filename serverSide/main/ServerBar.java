package serverSide.main;
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
        int portNumb = -1;                                          // port nunber for listening to service requests

        String reposServerName;                                        // name of the platform where is located the server for the general repository
        int reposPortNumb = -1;                                        // port nunber where the server for the general repository is listening to service requests


        if (args.length != 3)
            { GenericIO.writelnString ("Wrong number of parameters!");
            System.exit (1);
            }
        try
            { portNumb = Integer.parseInt (args[0]);
            }
        catch (NumberFormatException e)
            { GenericIO.writelnString ("args[0] is not a number!");
                System.exit (1);
            }
        if ((portNumb < 4000) || (portNumb >= 65536))
            { GenericIO.writelnString ("args[0] is not a valid port number!");
            System.exit (1);
            }
        reposServerName = args[1];
        try
            { reposPortNumb = Integer.parseInt (args[2]);
            }
        catch (NumberFormatException e)
        { 
            GenericIO.writelnString ("args[2] is not a number!");
            System.exit (1);
        }
        if ((reposPortNumb < 4000) || (reposPortNumb >= 65536))
        { 
            GenericIO.writelnString ("args[2] is not a valid port number!");
            System.exit (1);
        }

        serverCom = new ServerCom (portNumb);
        serverCom.start ();                
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        GeneralReposStub generalReposStub = new GeneralReposStub(reposServerName, reposPortNumb);
        Table table = new Table(generalReposStub);
        Kitchen kitchen = new Kitchen(generalReposStub);
        Bar bar = new Bar(generalReposStub, table, kitchen);
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
