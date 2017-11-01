package teamseth.cs340.common.commands.server;

import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.InitialChooseDestinationCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
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
public class InitialReturnDestinationCardCommand extends QueueCommand implements IServerCommand {
    private static final long serialVersionUID = -8210195450387873999L;

    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();
    private DestinationCard card;
    private AttemptStartPlayCommand execAfter;

    public InitialReturnDestinationCardCommand(Optional<DestinationCard> card) throws ResourceNotFoundException {
        super();
        this.deckId = ClientModelRoot.games.getActive().getDestinationDeck();
        this.card = card.orElseGet(() -> null);
        this.execAfter = new AttemptStartPlayCommand();
    }

    public InitialReturnDestinationCardCommand(DestinationCard card) throws ResourceNotFoundException {
        super();
        this.deckId = ClientModelRoot.games.getActive().getDestinationDeck();
        this.card = card;
    }

    public IHistoricalCommand clientCommand() throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        ServerFacade.getInstance().returnDestinationCard(deckId, card, token);
        return new InitialChooseDestinationCardCommand(Optional.ofNullable(card), token.getUser());
    }

    @Override
    public Result<AttemptStartPlayCommand> call() {
        super.call();
        return new Result<>(() -> execAfter);
    }
}
