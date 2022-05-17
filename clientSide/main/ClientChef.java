package clientSide.main;
import clientSide.entities.*;
import clientSide.stubs.*;

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
        kitchen = new KitchenStub("l040101-ws01.ua.pt", 22150);
        bar = new BarStub("l040101-ws01.ua.pt", 22151);
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
	}
}
