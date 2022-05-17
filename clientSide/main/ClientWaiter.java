package clientSide.main;
import clientSide.entities.*;
import clientSide.stubs.*;

/**
 *    Client side of the Assignment 2 - Waiter.
 *    Static solution Attempt (number of threads controlled by global constants - ExecConst)
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class ClientWaiter {
    /**
     *    Main method.
     *
     *    @param args runtime arguments
     */
    public static void main(String[] args) {
        Waiter waiter;
        KitchenStub kitchen;
        TableStub table;
        BarStub bar;
        kitchen = new KitchenStub("l040101-ws01.ua.pt", 22150);
        table = new TableStub("l040101-ws01.ua.pt", 22152);
        bar = new BarStub("l040101-ws01.ua.pt", 22151);
        waiter = new Waiter(WaiterStates.APPST, bar, kitchen, table);

        /*start thread*/
        waiter.start();

        /* wait for the end */
        try {
            waiter.join();	
        } catch (InterruptedException e) {}
        System.out.println("The Waiter just terminated");
	}
}
