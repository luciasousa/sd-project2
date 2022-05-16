package clientSide.main;
import clientSide.entities.*;
import clientSide.stubs.*;

/**
 *    Client side of the Assignment 2 - Student.
 *    Static solution Attempt (number of threads controlled by global constants - ExecConst)
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class ClientStudent {
     /**
     *    Main method.
     *
     *    @param args runtime arguments
     */
    public static void main(String[] args) {
        Student[] student = new Student[Constants.N];
        TableStub table;
        BarStub bar;
        table = new TableStub();
        bar = new BarStub();
        for (int i = 0; i < Constants.N; i++)
            student[i] = new Student(i,StudentStates.GGTRT, table, bar);

        /*start thread*/
        for (int i = 0; i < Constants.N; i++) student[i].start();

        /* wait for the end */
        for (int i = 0; i < Constants.N; i++)
        { 
            try{ 
                student[i].join();
            }
            catch (InterruptedException e) {}
            System.out.println("The Student "+(i)+" just terminated");
        }
	}
}
