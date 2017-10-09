package teamseth.cs340.tickettoride.communicator;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.commands.client.IClientCommand;
import teamseth.cs340.common.commands.server.IServerCommand;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CommandTask extends AsyncTask<ICommand, Void, Void> {

    @Override
    protected Void doInBackground(ICommand[] iCommands) {
        List<Object> output = Arrays.stream(iCommands).map((currentCommand) -> {
            Object currentObject = currentCommand;
            while (currentObject instanceof ICommand) {
                try {
                    Result result = new Result(() -> null);
                    if (currentObject instanceof IServerCommand) {
                        System.out.println("Executing server-side command: " + currentCommand.toString());
                        result = (Result) ClientCommunicator.post((IServerCommand) currentObject);
                        System.out.println("Successfully executed server-side command");
                    } else if (currentObject instanceof IClientCommand) {
                        System.out.println("Executing client-side command: " + currentCommand.toString());
                        result = (Result) ((IClientCommand) currentObject).call();
                        System.out.println("Successfully executed client-side command");
                    } else {
                        System.err.println("You screwed something up");
                    }
                    currentObject = result.get();
                } catch (Exception e) {
                    System.out.println("An error occured while executing a command");
                    e.printStackTrace();
                    currentObject = null;
                }
            }
            return currentObject;
        }).collect(Collectors.toList());;
        return null; // change this to return the list of results if you like
    }
}