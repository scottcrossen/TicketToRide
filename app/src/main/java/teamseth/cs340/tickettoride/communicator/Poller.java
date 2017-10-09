package teamseth.cs340.tickettoride.communicator;

import android.content.Context;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    ScheduledExecutorService cron;

    public void reset(){
        currentCommands = new ArrayList<ICommand>();
        this.stop();
    }

    public void addToJobs(ICommand command){
        currentCommands.add(command);
        if (currentCommands.size() == 1) this.start();
    }


    private void start()
    {
        cron = Executors.newScheduledThreadPool(1);
        cron.scheduleAtFixedRate(new Runnable() {
            Instant lastUpdateTime = null;
            @Override
            public void run() {
                if (currentCommands.size() > 0) {
                    Iterator<ICommand> iterator = currentCommands.iterator();
                    while (iterator.hasNext() && lastUpdateTime != null) {
                        ICommand current = iterator.next();
                        if (current instanceof IUpdateCommand)
                            ((IUpdateCommand) current).setLastUpdateTime(lastUpdateTime);
                    }
                    ICommand[] commandArray = new ICommand[currentCommands.size()];
                    commandArray = currentCommands.toArray(commandArray);
                    (new CommandTask(Poller.this.context)).execute(commandArray);
                    lastUpdateTime = Instant.now();
                }
            }
        }, 0, frequency, TimeUnit.SECONDS);
    }
    private void stop() {
        cron.shutdown();
    }
}
