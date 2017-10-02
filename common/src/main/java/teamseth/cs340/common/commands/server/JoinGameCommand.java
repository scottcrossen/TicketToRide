package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.models.server.authentication.AuthToken;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class JoinGameCommand implements ICommand {

    private UUID gameId;
    private UUID userId;
    private AuthToken token;

    public JoinGameCommand(UUID gameId, UUID userId, AuthToken token) {
        this.gameId = gameId;
        this.userId = userId;
        this.token = token;
    }

    public Result call() {
        return new Result(() -> {ServerFacade.getInstance().joinGame(this.gameId, this.userId, this.token); return null;});
    }
}
