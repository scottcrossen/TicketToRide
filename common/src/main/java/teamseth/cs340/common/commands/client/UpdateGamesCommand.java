package teamseth.cs340.common.commands.client;

import java.util.HashSet;

import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UpdateGamesCommand implements IClientCommand {
    private static final long serialVersionUID = 8664794899514283942L;

    private HashSet<Game> newGames;

    public UpdateGamesCommand(HashSet<Game> newGames) {
        this.newGames = newGames;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addGames(newGames); return null;});
    }
}
