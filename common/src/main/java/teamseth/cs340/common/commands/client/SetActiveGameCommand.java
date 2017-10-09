package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SetActiveGameCommand implements IClientCommand {
    private static final long serialVersionUID = 4441655404263985712L;

    private Game newGame;

    public SetActiveGameCommand(Game newGame) {
        this.newGame = newGame;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().setActiveGame(newGame); return null;});
    }
}
