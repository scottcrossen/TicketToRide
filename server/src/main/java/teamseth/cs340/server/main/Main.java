package teamseth.cs340.server.main;

import java.util.concurrent.TimeUnit;

import teamseth.cs340.common.util.Logger;
import teamseth.cs340.server.communicator.ServerCommunicator;

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
                Logger.info("Starting fail-safe monitor.");
                ServerCommunicator server = new ServerCommunicator();
                String[] inputArgs = {"8081"};
                for (int i = 0; i < args.length; i++) {
                    if ((args[i].equals("-p") || args[i].equals("--port")) && i != (args.length - 1)) {
                        inputArgs[0] = args[i+1];
                        break;
                    }
                }
                server.main(inputArgs);
                fail=true;
            } catch (Exception e) {
                Logger.error("Server failure caught by monitor.");
                e.printStackTrace();
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
