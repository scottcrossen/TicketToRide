package teamseth.cs340.server.communicator;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import teamseth.cs340.common.models.server.ServerModelRoot;
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
        new ServerCommunicator().run(portNumber);
    }

    private void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating models");

        ServerModelRoot serverFacade = new ServerModelRoot();

        System.out.println("Creating contexts");

        server.createContext("/execCommand", new CommandHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }
}
