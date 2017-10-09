package teamseth.cs340.common.commands.server;

import java.util.HashSet;
import java.util.UUID;

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
public class StartGameCommand implements IServerCommand {
    private static final long serialVersionUID = 8978879028087301674L;

    private UUID gameId;
    private AuthToken token = Login.getInstance().getToken();

    public StartGameCommand(UUID gameId) {
        this.gameId = gameId;
    }

    public Result<UpdateGamesCommand> call() {
        return new Result(() -> {
            ServerFacade.getInstance().startGame(this.gameId, token);
            HashSet<Game> games = new HashSet<Game>();
            games.add(ServerFacade.getInstance().getGame(gameId));
            return new UpdateGamesCommand(games);
        });
    }
}
