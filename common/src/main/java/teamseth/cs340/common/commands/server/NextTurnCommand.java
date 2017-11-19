package teamseth.cs340.common.commands.server;

import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.commands.client.ChangeTurnCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class NextTurnCommand extends QueueCommand implements IServerCommand {

    private UUID gameId;
    private AuthToken token = Login.getInstance().getToken();
    private HashSet<UUID> allPlayers = ClientModelRoot.getInstance().games.getActive().getPlayers();

    public NextTurnCommand() throws ResourceNotFoundException {
        this.gameId = ClientModelRoot.getInstance().games.getActive().getId();
    }

    public IHistoricalCommand clientCommand() throws Exception {
        ServerFacade.getInstance().nextPlayerTurn(gameId, token);
        return new ChangeTurnCommand(allPlayers, token.getUser());
    }
}
