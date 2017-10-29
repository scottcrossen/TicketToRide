package teamseth.cs340.common.commands.server;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.RemoveResourceCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ReturnManyResourceCardsCommand implements IServerCommand {
    private UUID deckId;
    private AuthToken token;
    private ResourceColor card;
    private int amount;
    private UUID historyId;

    public ReturnManyResourceCardsCommand(ResourceColor card, int amount, AuthToken token) throws ResourceNotFoundException {
        this.deckId = ClientModelRoot.games.getActive().getResourceDeck();
        this.card = card;
        this.historyId = ClientModelRoot.games.getActive().getHistory();
        this.amount = amount;
        this.token = token;
    }

    public List<IHistoricalCommand> clientCommand() throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        ServerFacade.getInstance().returnResourceCard(deckId, card, token);
        UUID user = token.getUser();
        List<IHistoricalCommand> output = new LinkedList<>();
        IntStream.rangeClosed(1,amount).forEach((int i) -> output.add(new RemoveResourceCardCommand(card, user)));
        return output;
    }

    public Result call() {
        return new Result(() -> {
            List<IHistoricalCommand> historicalCommands = clientCommand();
            for (IHistoricalCommand command : historicalCommands) {
                ServerFacade.getInstance().addCommandToHistory(historyId, command, token);
            }
            return null;
        });
    }

}
