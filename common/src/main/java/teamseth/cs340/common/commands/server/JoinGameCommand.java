package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.SetActiveGameCommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class JoinGameCommand implements IServerCommand {
    private static final long serialVersionUID = 6582928080311780721L;

    private UUID gameId;
    private AuthToken token = Login.getInstance().getToken();

    public JoinGameCommand(UUID gameId) {
        this.gameId = gameId;
    }

    public Result<SetActiveGameCommand> call() {
        return new Result<SetActiveGameCommand>(() -> {
            ServerFacade.getInstance().joinGame(this.gameId, token);
            return new SetActiveGameCommand(ServerFacade.getInstance().getGame(gameId));
        });
    }
}
