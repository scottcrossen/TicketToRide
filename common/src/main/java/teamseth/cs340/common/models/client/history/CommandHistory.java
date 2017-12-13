package teamseth.cs340.common.models.client.history;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.InitialChooseDestinationCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CommandHistory extends Observable {
    private static CommandHistory instance;

    public static CommandHistory getInstance() {
        if(instance == null) {
            instance = new CommandHistory();
        }
        return instance;
    }

    private List<IHistoricalCommand> history = new LinkedList<>();
    private static Semaphore readers = new Semaphore(100);
    private static Semaphore writers = new Semaphore(1);

    public void resetModel() {
        deleteObservers();
        try {
            writers.acquire();
            history = new LinkedList<>();
        } catch (Exception e) {
        } finally {
            writers.release();
        }
        setChanged();
        notifyObservers();
    }

    public void add(IHistoricalCommand command) {
        try {
            writers.acquire();
            history.add(command);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writers.release();
        }
        setChanged();
        notifyObservers();
    }

    public List<String> getHistory() throws ResourceNotFoundException {
        List<String> output = new LinkedList<>();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            Map<UUID, String> playerNames = ClientModelRoot.games.getActive().getPlayerNames();
            Iterator<IHistoricalCommand> iterator = history.iterator();
            String last = null;
            int lastCount = 0;
            while (iterator.hasNext()) {
                IHistoricalCommand command = iterator.next();
                String next = playerNames.get(command.playerOwnedby()) + " " + command.getDescription();
                if (next.equals(last)) {
                    lastCount++;
                } else if (last != null) {
                    output.add((lastCount > 1) ? String.format("%s (%d)", last, lastCount) : last);
                    lastCount = 1;
                    last = next;
                } else {
                    lastCount = 1;
                    last = next;
                }
            }
            if (history.size() > 0)
                output.add((lastCount > 1) ? String.format("%s (%d)", last, lastCount) : last);
        } catch (Exception e) {
            output = new LinkedList<>();
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public Optional<UUID> getLastId() {
        Optional<UUID> output = Optional.empty();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            if (history.size() > 0) {
                output = Optional.of(history.get(history.size() - 1)).map((IHistoricalCommand command) -> command.getId());
            }
        } catch (Exception e) {
            output = Optional.empty();
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public boolean playerChoseInitialCards(UUID playerId) {
        boolean output = true;
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            output = history.stream().anyMatch((IHistoricalCommand command) ->
                (command instanceof InitialChooseDestinationCardCommand && command.playerOwnedby().equals(playerId))
            );
        } catch (Exception e) {
            output = true;
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public Optional<IHistoricalCommand> tailOption() {
        Optional<IHistoricalCommand> output = Optional.empty();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            if (history.size() > 0) {
                output = Optional.of(history.get(history.size() - 1));
            } else {
                output = Optional.empty();
            }
        } catch (Exception e) {
            output = Optional.empty();
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }
}
