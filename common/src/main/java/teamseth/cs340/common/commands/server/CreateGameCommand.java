package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.client.SetActiveGameCommand;
import teamseth.cs340.common.commands.client.UpdateGamesCommand;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CreateGameCommand implements IServerCommand {
    private static final long serialVersionUID = 1076140290157434203L;
    private AuthToken token = Login.getInstance().getToken();

    public Result<UpdateGamesCommand> call() {
        return new Result(() -> {
            Game newGame = ServerFacade.getInstance().createGame(token);
            return new SetActiveGameCommand(newGame);
        });
    }
}
