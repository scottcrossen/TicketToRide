package teamseth.cs340.common.commands.server;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.IUpdatableCommand;
import teamseth.cs340.common.commands.client.ExecQueuedHistoryCommand;
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
public class UpdateClientHistoryCommand implements IServerCommand, IUpdatableCommand {
    private static final long serialVersionUID = -4115084785849443752L;

    private UUID historyId;
    // Optional isn't serializable. I hate java.
    private UUID lastCommandId = ClientModelRoot.history.getLastId().orElseGet(() -> null);
    private AuthToken token = Login.getInstance().getToken();

    public UpdateClientHistoryCommand() throws ResourceNotFoundException {
        historyId = ClientModelRoot.games.getActive().getHistory();
    }

    public Result<ExecQueuedHistoryCommand> call() {
        return new Result(() -> {
            LinkedList<IHistoricalCommand> commands = ServerFacade.getInstance().getCommandsAfter(historyId, Optional.ofNullable(lastCommandId), token);
            return new ExecQueuedHistoryCommand(commands);
        });
    }

    public void applyUpdate(Instant time) {
        lastCommandId = ClientModelRoot.history.getLastId().orElseGet(() -> null);
    }
}
