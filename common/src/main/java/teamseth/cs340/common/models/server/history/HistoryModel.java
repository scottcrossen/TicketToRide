package teamseth.cs340.common.models.server.history;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.commands.client.ChangeTurnCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.ILogoutOkayCommand;
import teamseth.cs340.common.commands.client.InitialChooseDestinationCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.NotYourTurnException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.IServerModel;
import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.IDeltaCommand;
import teamseth.cs340.common.persistence.PersistenceAccess;
import teamseth.cs340.common.persistence.PersistenceTask;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class HistoryModel extends AuthAction implements IServerModel<CommandHistory> {
    private static HistoryModel instance;

    public static HistoryModel getInstance() {
        if(instance == null) {
            instance = new HistoryModel();
        }
        return instance;
    }

    private HashSet<CommandHistory> histories = new HashSet<>();

    public CompletableFuture<Boolean> loadAllFromPersistence() {
        CompletableFuture<List<CommandHistory>> persistentData = PersistenceAccess.getObjects(ModelObjectType.HISTORY);
        return persistentData.thenApply((List<CommandHistory> newData) -> {
            histories.addAll(newData);
            return true;
        });
    }

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
        PersistenceTask.save(history, new IDeltaCommand<CommandHistory>() {
            @Override
            public CommandHistory call(CommandHistory oldState) {
                try {
                    oldState.insert(command);
                } catch (Exception e) {
                }
                return oldState;
            }
        });
    }

    public void upsert(CommandHistory newHistory, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            get(newHistory.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            histories.add(newHistory);
            PersistenceTask.save(newHistory, new IDeltaCommand<CommandHistory>() {
                @Override
                public CommandHistory call(CommandHistory oldState) {
                    return newHistory;
                }
            });
        }
    }

    public boolean logoutStateOkay(UUID historyId, UUID playerId) {
        try {
            List<IHistoricalCommand> history = get(historyId).getAll();
            int lastPlayerTurnChangePos;
            for (lastPlayerTurnChangePos = history.size() - 1; lastPlayerTurnChangePos >= 0; lastPlayerTurnChangePos--) {
                IHistoricalCommand currentCommand = history.get(lastPlayerTurnChangePos);
                if (currentCommand instanceof ChangeTurnCommand) {
                    if (((ChangeTurnCommand) currentCommand).getPlayerTurn().equals(playerId)) {
                        break;
                    } else {
                        return true;
                    }
                }
            }
            lastPlayerTurnChangePos = lastPlayerTurnChangePos < 0 ? 0 : lastPlayerTurnChangePos;
            for (int i = lastPlayerTurnChangePos; i < history.size(); i++) {
                if (!(history.get(i) instanceof ILogoutOkayCommand)) {
                    return false;
                }
            }
            return true;
        } catch (ResourceNotFoundException e) {
            return true;
        }
    }

    public boolean playerHasChosenInitialCards(UUID historyId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException {
        AuthAction.user(token);
        UUID playerId = token.getUser();
        return getAllCommands(historyId).stream().anyMatch((IHistoricalCommand command) ->
                command instanceof InitialChooseDestinationCardCommand && command.playerOwnedby().equals(playerId)
        );
    }
}
