package teamseth.cs340.common.commands.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.RemoveResourceCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ReturnManyResourceCardsCommand implements IServerCommand {
    private static final long serialVersionUID = 7217755804173741901L;
    private UUID deckId;
    private AuthToken token;
    private ArrayList<ResourceColor> cards;
    private UUID historyId;
    private UUID gameId;

    public ReturnManyResourceCardsCommand(ArrayList<ResourceColor> cards, UUID gameId, UUID deckId, UUID historyId, AuthToken token) throws ResourceNotFoundException {
        this.deckId = deckId;
        this.cards = cards;
        this.gameId = gameId;
        this.historyId = historyId;
        this.token = token;
    }

    public List<IHistoricalCommand> clientCommand() throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        UUID user = token.getUser();
        List<IHistoricalCommand> output = new LinkedList<>();
        for (ResourceColor card : cards) {
            ServerFacade.getInstance().returnResourceCard(deckId, card, token);
            output.add(new RemoveResourceCardCommand(card, user));
        }
        return output;
    }

    public Result call() {
        return new Result(() -> {
            List<IHistoricalCommand> historicalCommands = clientCommand();
            for (IHistoricalCommand command : historicalCommands) {
                ServerFacade.getInstance().addCommandToHistory(gameId, historyId, command, token);
            }
            return null;
        });
    }

}
