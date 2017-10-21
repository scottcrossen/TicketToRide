package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.AddResourceCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DrawResourceCardCommand extends QueueCommand implements IServerCommand {

    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();

    public DrawResourceCardCommand() throws ResourceNotFoundException {
        this.deckId = ClientModelRoot.games.getActive().getResourceDeck();
    }

    public IHistoricalCommand clientCommand() throws Exception {
        ResourceColor newCard = ServerFacade.getInstance().drawResourceCard(deckId, token);
        return new AddResourceCardCommand(newCard, token.getUser());
    }
}
