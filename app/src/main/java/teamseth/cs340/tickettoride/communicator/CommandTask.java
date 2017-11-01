package teamseth.cs340.tickettoride.communicator;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Arrays;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.commands.client.IClientCommand;
import teamseth.cs340.common.commands.server.IServerCommand;
import teamseth.cs340.common.util.Result;
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
                        System.out.println("Executing server-side command: " + currentObject.toString());
                        result = (Result) ClientCommunicator.post((IServerCommand) currentObject);
                        System.out.println("Successfully executed server-side command");
                    } else if (currentObject instanceof IClientCommand) {
                        System.out.println("Executing client-side command: " + currentObject.toString());
                        result = (Result) ((IClientCommand) currentObject).call();
                        System.out.println("Successfully executed client-side command");
                    } else {
                        System.err.println("You screwed something up");
                    }
                    currentObject = result.get();
                } catch (Exception e) {
                    System.out.println("An error occured while executing command " + currentCommand.toString());
                    System.out.println(e.toString());
                    currentObject = e;
                }
            }
            return currentObject;
        }).filter((obj) -> obj instanceof Exception).findFirst().map((e) -> ((Exception) e).getMessage()).orElseGet(() -> "");
        return output;
    }

    protected void onPostExecute(String output) {
        System.out.println(output);
        if (output != "") Toaster.getInstance().makeToast(this.context, output);
    }

}