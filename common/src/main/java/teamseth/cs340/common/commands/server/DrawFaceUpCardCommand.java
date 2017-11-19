package teamseth.cs340.common.commands.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.AddResourceCardCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.ReplaceFaceUpCardCommand;
import teamseth.cs340.common.commands.client.SeedFaceUpCardsCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
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
public class DrawFaceUpCardCommand implements IServerCommand {
    private static final long serialVersionUID = -3918296503032080679L;
    private UUID deckId;
    private AuthToken token = Login.getInstance().getToken();
    private ResourceColor card;
    private UUID historyId;
    private Set<UUID> players = ClientModelRoot.games.getActive().getPlayers();
    private UUID gameId;

    public DrawFaceUpCardCommand(ResourceColor card) throws ResourceNotFoundException {
        this.deckId = ClientModelRoot.games.getActive().getResourceDeck();
        this.card = card;
        this.historyId = ClientModelRoot.games.getActive().getHistory();
        this.gameId = ClientModelRoot.games.getActive().getId();
    }

    public List<IHistoricalCommand> clientCommand() throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        Optional<ResourceColor> newFaceUp = ServerFacade.getInstance().drawFaceUpCard(deckId, card, token);
        UUID user = token.getUser();
        List<IHistoricalCommand> output = new LinkedList<>();
        output.add(new AddResourceCardCommand(card, user));
        List<ResourceColor> newFaceUps = ServerFacade.getInstance().checkAndResuffleFaceUpCards(deckId, token);
        if (newFaceUps.size() > 0) {
            output.add(new SeedFaceUpCardsCommand(newFaceUps, players, user));
        } else {
            output.add(new ReplaceFaceUpCardCommand(card, newFaceUp, players, user));
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
