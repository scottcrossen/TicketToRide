package teamseth.cs340.server.main;

import teamseth.cs340.server.communicator.ServerCommunicator;
import java.util.concurrent.TimeUnit;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Main {
    public static void main(String[] args) {
        boolean fail = false;
        int failCount = 0;
        while (!fail && failCount < 10) {
            try {
                System.out.println("Starting server from monitor.");
                ServerCommunicator server = new ServerCommunicator();
                String[] inputArgs = {"8080"};
                server.main(inputArgs);
                fail=true;
            } catch (Exception e) {
                System.out.println("Server failure caught by monitor.");
                failCount ++;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception b) {
                    fail = true;
                }
            }
        }
    }
}
