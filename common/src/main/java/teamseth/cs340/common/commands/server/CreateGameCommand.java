package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Config;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CreateGameCommand implements IServerCommand {
    private static final long serialVersionUID = 1076140290157434203L;
    private AuthToken token = Config.getInstance().getToken();

    public Result call() {
        return new Result(() -> {ServerFacade.getInstance().createGame(token); return null;});
    }
}
