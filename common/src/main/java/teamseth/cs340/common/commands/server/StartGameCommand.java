package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Config;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class StartGameCommand implements IServerCommand {
    private static final long serialVersionUID = 8978879028087301674L;

    private UUID gameId;
    private AuthToken token = Config.getInstance().getToken();

    public StartGameCommand(UUID gameId) {
        this.gameId = gameId;
    }

    public Result call() {
        return new Result(() -> {ServerFacade.getInstance().startGame(this.gameId, token); return null;});
    }
}
