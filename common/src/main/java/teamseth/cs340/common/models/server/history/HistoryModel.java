package teamseth.cs340.common.models.server.history;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.NotYourTurnException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class HistoryModel extends AuthAction implements IModel<CommandHistory> {
    private static HistoryModel instance;

    public static HistoryModel getInstance() {
        if(instance == null) {
            instance = new HistoryModel();
        }
        return instance;
    }

    private HashSet<CommandHistory> histories = new HashSet<>();

    private CommandHistory get(UUID historyId) throws ResourceNotFoundException {
        CommandHistory output = histories.stream().filter(history -> history.getId().equals(historyId)).findFirst().orElseThrow(() -> new ResourceNotFoundException());
        return output;
    }

    public LinkedList<IHistoricalCommand> getCommandsAfter(UUID historyId, Optional<UUID> afterId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        UUID userId = token.getUser();
        CommandHistory history = get(historyId);
        return history.getByPlayer(userId, afterId);
    }

    public LinkedList<IHistoricalCommand> getAllCommands(UUID historyId) throws ResourceNotFoundException {
        return get(historyId).getAll();
    }

    public void addCommandToHistory(UUID gameId, UUID historyId, IHistoricalCommand command, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException, NotYourTurnException {
        UUID playerId = token.getUser();
        // Uncomment the following to block player actions that aren't on their turn.
        /*boolean isPlayersTurn = ServerModelRoot.getInstance().games.get(gameId).getWhosTurnItIs().map((UUID currentPlayer) -> currentPlayer.equals(playerId)).orElseGet(() -> true);
        if (isPlayersTurn) {
            forceAddCommandToHistory(historyId, command, token);
        } else {
            throw new NotYourTurnException();
        }*/
        forceAddCommandToHistory(historyId, command, token);
    }

    public void forceAddCommandToHistory(UUID historyId, IHistoricalCommand command, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException {
        AuthAction.user(token);
        CommandHistory history = get(historyId);
        history.insert(command);
    }

    public void upsert(CommandHistory newHistory, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            get(newHistory.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            histories.add(newHistory);
        }
    }

}
