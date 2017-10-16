package teamseth.cs340.common.commands.server;

import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.commands.client.UpdateGamesCommand;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GetGameCommand implements IServerCommand {
    private static final long serialVersionUID = 5488765114231986305L;

    private UUID gameId;

    public GetGameCommand(UUID id) {
        this.gameId = id;
    }

    public Result<UpdateGamesCommand> call() {
        return new Result(() -> {
            HashSet<Game> games = new HashSet<Game>();
            games.add(ServerFacade.getInstance().getGame(gameId));
            return new UpdateGamesCommand(games);
        });
    }
}
