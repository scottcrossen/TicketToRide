package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public abstract class QueueCommand implements IServerCommand {
    private static final long serialVersionUID = -7346493926381505176L;

    UUID historyId;
    private AuthToken token = Login.getInstance().getToken();

    public QueueCommand() throws ResourceNotFoundException {
        this.historyId = ClientModelRoot.games.getActive().getHistory();
    }

    public abstract IHistoricalCommand clientCommand() throws Exception;

    public Result call() {
        return new Result(() -> {
            IHistoricalCommand historicalCommand = clientCommand();
            if (historicalCommand != null) {
                System.out.println("Queuing historical command: " + historicalCommand.toString());
                ServerFacade.getInstance().addCommandToHistory(historyId, historicalCommand, token);
                System.out.println("Successfully queued command");
            }
            return null;
        });
    }
}
