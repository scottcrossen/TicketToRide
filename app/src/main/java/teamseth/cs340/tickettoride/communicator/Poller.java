package teamseth.cs340.tickettoride.communicator;

import android.content.Context;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.commands.IUpdateCommand;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Poller {
    private static Poller instance;

    public static Poller getInstance(Context context) {
        if(instance == null) {
            instance = new Poller(context);
        }
        return instance;
    }

    public Poller(Context context){
        this.context = context;
    }

    protected List<ICommand> currentCommands = new ArrayList<ICommand>();
    private int frequency = 5;
    private Context context = null;

    private class PollTasker extends CommandTask {

        public PollTasker() {
            super(Poller.this.context);
        }
        @Override
        protected String doInBackground(ICommand... iCommands) {
            Instant lastUpdateTime = null;
            while (currentCommands.size() > 0) {
                Iterator<ICommand> iterator = currentCommands.iterator();
                while(iterator.hasNext() && lastUpdateTime != null) {
                    ICommand current = iterator.next();
                    if (current instanceof IUpdateCommand) ((IUpdateCommand) current).setLastUpdateTime(lastUpdateTime);
                }
                ICommand[] commandArray = new ICommand[currentCommands.size()];
                commandArray = currentCommands.toArray(commandArray);
                super.doInBackground(commandArray);
                lastUpdateTime = Instant.now();
                try {
                    TimeUnit.SECONDS.sleep(frequency);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(String output) {
        }
    }

    public void reset(){
        currentCommands = null;
    }

    public void addToJobs(ICommand command){
        currentCommands.add(command);
        if (currentCommands.size() == 1) (new PollTasker()).execute();
    }
}
