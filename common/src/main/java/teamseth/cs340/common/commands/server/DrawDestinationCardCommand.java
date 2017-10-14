package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.AddDestinationCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DrawDestinationCardCommand implements IServerCommand {

    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();

    public DrawDestinationCardCommand() throws ResourceNotFoundException {
        this.deckId = ClientModelRoot.games.getActive().getDestinationDeck();
    }

    public Result<AddDestinationCardCommand> call() {
        return new Result(() -> {
            DestinationCard newCard = ServerFacade.getInstance().drawDestinationCard(deckId, token);
            return new AddDestinationCardCommand(newCard);
        });
    }
}
