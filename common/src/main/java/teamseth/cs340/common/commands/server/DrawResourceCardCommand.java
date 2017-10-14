package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.AddResourceCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DrawResourceCardCommand implements IServerCommand {

    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();

    public DrawResourceCardCommand() throws ResourceNotFoundException {
        this.deckId = ClientModelRoot.games.getActive().getResourceDeck();
    }

    public Result<AddResourceCardCommand> call() {
        return new Result(() -> {
            ResourceColor newCard = ServerFacade.getInstance().drawResourceCard(deckId, token);
            return new AddResourceCardCommand(newCard);
        });
    }
}
