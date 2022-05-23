package clientSide.main;
import clientSide.entities.*;
import clientSide.stubs.*;
import genclass.GenericIO;

/**
 *    Client side of the Assignment 2 - Chef.
 *    Static solution Attempt (number of threads controlled by global constants - ExecConst)
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class ClientChef {
    /**
     *    Main method.
     *
     *    @param args runtime arguments
     */
    public static void main(String[] args) {
        Chef chef;
        KitchenStub kitchen;
        BarStub bar;
        GeneralReposStub generalRepos;
        String kitchenServerName = "";
        String barServerName = "";
        String generalRepoServerName = "";
        int kitchenServerPort = -1;
        int barServerPort = -1;
        int generalRepoServerPort = -1;
        
        if (args.length != 6)
        { GenericIO.writelnString ("Wrong number of parameters!");
          System.exit (1);
        }
        generalRepoServerName = args[0];
	     try
	     { generalRepoServerPort = Integer.parseInt (args[1]);
	     }
	     catch (NumberFormatException e)
	     { GenericIO.writelnString ("args[1] is not a number!");
	       System.exit (1);
	     }
	     if ((generalRepoServerPort < 4000) || (generalRepoServerPort >= 65536))
	        { GenericIO.writelnString ("args[1] is not a valid port number!");
	          System.exit (1);
	        }
	     kitchenServerName = args[2];
	     try
	     { kitchenServerPort = Integer.parseInt (args[3]);
	     }
	     catch (NumberFormatException e)
	     { GenericIO.writelnString ("args[3] is not a number!");
	       System.exit (1);
	     }
	     if ((kitchenServerPort < 4000) || (kitchenServerPort >= 65536))
	        { GenericIO.writelnString ("args[3] is not a valid port number!");
	          System.exit (1);
	        }
	     barServerName = args[4];
	     try
	     { barServerPort = Integer.parseInt (args[5]);
	     }
	     catch (NumberFormatException e)
	     { GenericIO.writelnString ("args[5] is not a number!");
	       System.exit (1);
	     }
	     if ((barServerPort < 4000) || (barServerPort >= 65536))
	        { GenericIO.writelnString ("args[5] is not a valid port number!");
	          System.exit (1);
	        }

        
        kitchen = new KitchenStub(kitchenServerName, kitchenServerPort);
        bar = new BarStub(barServerName, barServerPort);
        generalRepos = new GeneralReposStub(generalRepoServerName,generalRepoServerPort);
        chef = new Chef(ChefStates.WAFOR, kitchen, bar);

        /*start thread*/
        chef.start();

        /* wait for the end */
        try{ 
            chef.join();
        }
        catch (InterruptedException e) {}
        System.out.println("The Chef just terminated");
        System.out.println("End of the Simulation");
		kitchen.shutdown ();
        bar.shutdown();
        generalRepos.shutdown ();
	}
}
