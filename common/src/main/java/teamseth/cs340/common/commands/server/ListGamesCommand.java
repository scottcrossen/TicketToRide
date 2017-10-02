package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.commands.client.UpdateGamesCommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ListGamesCommand implements ICommand {
    public Result<UpdateGamesCommand> call() {
        return new Result<UpdateGamesCommand>(() -> new UpdateGamesCommand(ServerFacade.getInstance().listGames()));
    }
}
