package teamseth.cs340.tickettoride.util;

import android.content.Context;

import java.util.Optional;
import java.util.Random;

import teamseth.cs340.common.commands.server.CreateGameCommand;
import teamseth.cs340.common.commands.server.DrawResourceCardCommand;
import teamseth.cs340.common.commands.server.InitialReturnDestinationCardCommand;
import teamseth.cs340.common.commands.server.RegisterCommand;
import teamseth.cs340.common.commands.server.StartGameCommand;
import teamseth.cs340.common.commands.server.UpdateClientHistoryCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.Logger;
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
        Logger.debug("Starting command queue");
        Logger.debug("Running register command");
        String username = Long.toString(new Random().nextLong());
        String password = Long.toString(new Random().nextLong());
        new CommandTask(this.context).execute(new RegisterCommand(new UserCreds(username, password)));
        while (Login.getInstance().getToken() == null) pause();
        Logger.debug("Running create game command");
        new CommandTask(this.context).execute(new CreateGameCommand());
        while (!ClientModelRoot.games.hasActive()) pause();
        try {
            Logger.debug("Running start game command");
            new CommandTask(this.context).execute(new StartGameCommand(ClientModelRoot.games.getActive().getId()));
        } catch (Exception e) {
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
            Logger.debug("Running destination card return command");
            new CommandTask(this.context).execute(new InitialReturnDestinationCardCommand(Optional.empty()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        try {
        Logger.debug("Giving tons of cards to player");
            for (int i=0; i<=40; i++) {
                new CommandTask(this.context).execute(new DrawResourceCardCommand());
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        Logger.debug("Finished command queue");
    }

    private void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
