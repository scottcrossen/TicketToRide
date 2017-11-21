package teamseth.cs340.common.commands.server;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.RemoveTrainCartsCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class DecrementPlayerCartsCommand extends QueueCommand implements IServerCommand {
    private int numCarts;
    private Set<UUID> players = ClientModelRoot.games.getActive().getPlayers();
    private AuthToken token = Login.getInstance().getToken();
    private UUID cartId;

    public DecrementPlayerCartsCommand(int numCarts) throws ResourceNotFoundException {
        this.numCarts = numCarts;
        this.cartId = ClientModelRoot.games.getActive().getCarts();
    }

    @Override
    public IHistoricalCommand clientCommand() throws Exception {
        UUID user = token.getUser();
        ServerFacade.getInstance().decrementPlayerCarts(cartId, numCarts, token);
        return new RemoveTrainCartsCommand(numCarts, players, user);
    }
}
