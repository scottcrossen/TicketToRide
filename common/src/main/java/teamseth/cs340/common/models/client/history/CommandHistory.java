package teamseth.cs340.common.models.client.history;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CommandHistory {
    private static CommandHistory instance;

    public static CommandHistory getInstance() {
        if(instance == null) {
            instance = new CommandHistory();
        }
        return instance;
    }

    private List<IHistoricalCommand> history = new LinkedList<>();

    public void add(IHistoricalCommand command) {
        history.add(command);
    }

    public List<String> getHistory() throws ResourceNotFoundException {
        Map<UUID, String> playerNames = ClientModelRoot.games.getActive().getPlayerNames();
        List<String> compressedOutput = new LinkedList<>();
        Iterator<IHistoricalCommand> iterator = history.iterator();
        String last = null;
        int lastCount = 0;
        while (iterator.hasNext()) {
            IHistoricalCommand command = iterator.next();
            String next = playerNames.get(command.playerOwnedby()) + " " + command.getDescription();
            if (next.equals(last)) {
                lastCount++;
            } else if (last != null) {
                compressedOutput.add((lastCount > 1) ? String.format("%s (%d)", last, lastCount) : last);
                lastCount = 1;
                last = next;
            } else {
                lastCount = 1;
                last = next;
            }
        }
        if (history.size() > 0) compressedOutput.add((lastCount > 1) ? String.format("%s (%d)", last, lastCount) : last);
        return compressedOutput;
    }

    public Optional<UUID> getLastId() {
        if (history.size() > 0) {
            return Optional.of(history.get(history.size() - 1)).map((IHistoricalCommand command) -> command.getId());
        } else {
            return Optional.empty();
        }
    }
}
