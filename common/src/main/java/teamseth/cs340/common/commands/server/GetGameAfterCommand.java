package teamseth.cs340.common.commands.server;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.IUpdatableCommand;
import teamseth.cs340.common.commands.client.UpdateGamesCommand;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GetGameAfterCommand implements IServerCommand, IUpdatableCommand {
    private static final long serialVersionUID = 1586106672328611185L;

    public void applyUpdate(Instant time) {
        this.instant = time;
    }

    private Instant instant;

    private UUID gameId;

    public GetGameAfterCommand(Instant instant) {
        this.instant = instant;
    }

    public GetGameAfterCommand(UUID id) {
        this.gameId = id;
    }

    @Override
    public Result<UpdateGamesCommand> call() {
        return new Result(() -> {
            HashSet<Game> games = new HashSet<Game>();
            Optional<Game> optionGame = ServerFacade.getInstance().getGameAfter(gameId, instant);
            optionGame.map((game) -> games.add(game));
            return new UpdateGamesCommand(games);
        });
    }
}
