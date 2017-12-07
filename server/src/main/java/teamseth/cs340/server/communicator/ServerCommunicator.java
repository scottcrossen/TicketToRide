package teamseth.cs340.server.communicator;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import teamseth.cs340.common.models.server.ServerModelRoot;
import teamseth.cs340.common.plugin.PersistanceAccess;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.server.Config;
import teamseth.cs340.server.communicator.controllers.CommandHandler;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ServerCommunicator {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    public static void main(String[] args) {
        String portNumber = args[0];
        new ServerCommunicator().run(portNumber, args);
    }

    private void run(String portNumber, String[] args) {

        Logger.info("Initializing HTTP Server on port " + portNumber.toString());

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        Logger.info("Creating models and globals");

        Config config = Config.getInstance();
        ServerModelRoot serverFacade = ServerModelRoot.getInstance();
        PersistanceAccess.getInstance().initialize(args);

        Logger.info("Creating contexts");

        server.createContext("/command", new CommandHandler());

        Logger.info("Starting server");

        server.start();

        Logger.info("Server started");
    }
}
