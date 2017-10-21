package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.RemoveResourceCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ReturnResourceCard extends QueueCommand implements IServerCommand {
    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();
    private ResourceColor card;

    public ReturnResourceCard(ResourceColor card) throws ResourceNotFoundException {
        super();
        this.deckId = ClientModelRoot.games.getActive().getDestinationDeck();
        this.card = card;
    }

    public IHistoricalCommand clientCommand() throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        ServerFacade.getInstance().returnResourceCard(deckId, card, token);
        return new RemoveResourceCardCommand(card, token.getUser());
    }
}
