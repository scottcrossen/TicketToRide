package teamseth.cs340.common.commands.server;

import java.time.Instant;
import java.util.Set;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ListGamesAfterCommand implements ICommand {

    private Instant instant;

    public ListGamesAfterCommand(Instant instant) {
        this.instant = instant;
    }

    public Result<Set<Game>> call() {
        return new Result<Set<Game>>(() -> ServerFacade.getInstance().listGamesAfter(instant));
    }
}
