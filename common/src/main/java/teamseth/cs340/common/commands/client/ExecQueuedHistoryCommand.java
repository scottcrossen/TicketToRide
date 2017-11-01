package teamseth.cs340.common.commands.client;

import java.util.List;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ExecQueuedHistoryCommand implements IClientCommand {
    private static final long serialVersionUID = 8623181933770058388L;
    List<IHistoricalCommand> commands;

    public ExecQueuedHistoryCommand(List<IHistoricalCommand> commands) {
        this.commands = commands;
    }

    public Result call() {
        return new Result(() -> {
            commands.stream().forEach((command) -> {
                System.out.println("Executing client-side historical command: " + command.toString());
                ClientFacade.getInstance().addHistory(command);
                System.out.println("Successfully executed client-side historical command");
                command.call();
            });
            return null;
        });
    }
}
