package teamseth.cs340.common.commands.server;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.SetGameStateCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AttemptStartPlayCommand extends QueueCommand implements IServerCommand {

    private AuthToken token = Login.getInstance().getToken();
    private Set<UUID> players;
    private UUID gameId;

    public AttemptStartPlayCommand() throws ResourceNotFoundException {
        super();
        players = ClientModelRoot.games.getActive().getPlayers();
        gameId = ClientModelRoot.games.getActive().getId();
    }

    public IHistoricalCommand clientCommand() throws Exception {
        if (ServerFacade.getInstance().attemptStartGame(gameId, token)) {
            return new SetGameStateCommand(GameState.PLAYING, players, token.getUser());
        } else {
            return null;
        }
    }
}
