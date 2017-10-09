package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.ClearActiveGameCommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class LeaveGameCommand implements IServerCommand {
    private static final long serialVersionUID = 3612840123064228177L;

    private UUID gameId;
    private AuthToken token = Login.getInstance().getToken();

    public LeaveGameCommand(UUID id) {
        this.gameId = id;
    }

    @Override
    public Result<ClearActiveGameCommand> call() {
        return new Result<ClearActiveGameCommand>(() -> {
            ServerFacade.getInstance().leaveGame(gameId, token);
            return new ClearActiveGameCommand();
        });
    }
}
