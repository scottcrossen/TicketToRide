package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CreateGameCommand implements ICommand {

    private UUID userId;
    private AuthToken token;

    public CreateGameCommand(UUID userId, AuthToken token) {
        this.userId = userId;
        this.token = token;
    }

    public Result call() {
        return new Result(() -> {ServerFacade.getInstance().createGame(this.userId, this.token); return null;});
    }
}
