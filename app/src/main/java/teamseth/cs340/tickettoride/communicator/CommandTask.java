package teamseth.cs340.tickettoride.communicator;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Arrays;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.commands.client.IClientCommand;
import teamseth.cs340.common.commands.server.IServerCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.util.Toaster;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CommandTask extends AsyncTask<ICommand, Void, String> {

    Context context;
    public CommandTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(ICommand[] iCommands) {
        String output = Arrays.stream(iCommands).map((currentCommand) -> {
            Object currentObject = currentCommand;
            while (currentObject instanceof ICommand) {
                try {
                    Result result = new Result(() -> null);
                    if (currentObject instanceof IServerCommand) {
                        Logger.debug("Executing server-side command: " + currentObject.toString());
                        result = (Result) ClientCommunicator.post((IServerCommand) currentObject);
                        Logger.debug("Successfully executed server-side command");
                    } else if (currentObject instanceof IClientCommand) {
                        Logger.debug("Executing client-side command: " + currentObject.toString());
                        result = (Result) ((IClientCommand) currentObject).call();
                        Logger.debug("Successfully executed client-side command");
                    } else {
                        Logger.error("You screwed something up");
                    }
                    currentObject = result.get();
                } catch (ResourceNotFoundException e) {
                    Logger.error("An error occurred while executing command " + currentCommand.toString());
                    if (Login.getInstance().getToken() == null) {
                        currentObject = new Exception("Error: Login information not found.");
                    } else {
                        e.printStackTrace();
                        currentObject = e;
                    }
                } catch (UnauthorizedException e) {
                    Logger.error("An error occurred while executing command " + currentCommand.toString());
                    if (Login.getInstance().getToken() != null) {
                        Login.getInstance().logout();
                        currentObject = new Exception("Error: Token expired.");
                    } else {
                        e.printStackTrace();
                        currentObject = e;
                    }
                } catch (Exception e) {
                    Logger.error("An error occurred while executing command " + currentCommand.toString());
                    e.printStackTrace();
                    currentObject = e;
                }
            }
            return currentObject;
        }).filter((obj) -> obj instanceof Exception).findFirst().map((e) -> ((Exception) e).getMessage()).orElseGet(() -> "");
        return output;
    }

    protected void onPostExecute(String output) {
        if (output != "") Toaster.longT(this.context, output);
    }

}