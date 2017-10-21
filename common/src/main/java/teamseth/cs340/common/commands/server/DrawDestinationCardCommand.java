package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.IHistoricalCommand;
import teamseth.cs340.common.commands.client.AddDestinationCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DrawDestinationCardCommand extends QueueCommand implements IServerCommand {

    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();

    public DrawDestinationCardCommand() throws ResourceNotFoundException {
        super();
        this.deckId = ClientModelRoot.games.getActive().getDestinationDeck();
    }

    public IHistoricalCommand clientCommand() throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        DestinationCard newCard = ServerFacade.getInstance().drawDestinationCard(deckId, token);
        return new AddDestinationCardCommand(newCard, token.getUser());
    }
}
