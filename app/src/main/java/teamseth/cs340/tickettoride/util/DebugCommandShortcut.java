package teamseth.cs340.tickettoride.util;

import android.content.Context;

import java.util.Optional;
import java.util.Random;

import teamseth.cs340.common.commands.server.CreateGameCommand;
import teamseth.cs340.common.commands.server.InitialReturnDestinationCardCommand;
import teamseth.cs340.common.commands.server.RegisterCommand;
import teamseth.cs340.common.commands.server.StartGameCommand;
import teamseth.cs340.common.commands.server.UpdateClientHistoryCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DebugCommandShortcut implements Runnable {
    private static DebugCommandShortcut instance;
    Context context;

    public DebugCommandShortcut(Context context) {
        this.context = context;
    }

    public static DebugCommandShortcut getInstance(Context context) {
        if(instance == null) {
            instance = new DebugCommandShortcut(context);
        }
        return instance;
    }

    public void run() {
        System.out.println("Starting command queue");
        System.out.println("Running register command");
        String username = Long.toString(new Random().nextLong());
        String password = Long.toString(new Random().nextLong());
        new CommandTask(this.context).execute(new RegisterCommand(new UserCreds(username, password)));
        while (Login.getInstance().getToken() == null) pause();
        System.out.println("Running create game command");
        new CommandTask(this.context).execute(new CreateGameCommand());
        while (!ClientModelRoot.games.hasActive()) pause();
        try {
            System.out.println("Running start game command");
            new CommandTask(this.context).execute(new StartGameCommand(ClientModelRoot.games.getActive().getId()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        while (ClientModelRoot.getInstance().cards.getDestinationCards().size() == 0) {
            try {
                Thread.sleep(1000);
                new CommandTask(this.context).execute(new UpdateClientHistoryCommand());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println("Running destination card return command");
            new CommandTask(this.context).execute(new InitialReturnDestinationCardCommand(Optional.empty()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Finished command queue");
    }

    private void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
