package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.root.server.models.authentication.AuthToken;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class StartGame implements ICommand {

    private UUID gameId;
    private AuthToken token;

    public StartGame(UUID gameId, AuthToken token) {
        this.gameId = gameId;
        this.token = token;
    }

    public Result call() {
        return new Result(() -> {ServerFacade.getInstance().startGame(this.gameId, this.token); return null;});
    }
}
