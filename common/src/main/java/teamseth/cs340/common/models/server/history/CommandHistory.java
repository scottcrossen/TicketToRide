package teamseth.cs340.common.models.server.history;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CommandHistory implements Serializable {
    private UUID id = UUID.randomUUID();

    private LinkedList<IHistoricalCommand> commandQueue = new LinkedList<IHistoricalCommand>();

    public LinkedList<IHistoricalCommand> getByPlayer(UUID playerId, Optional<UUID> afterId) throws ResourceNotFoundException {
        LinkedList<IHistoricalCommand> output = new LinkedList<>();
        Iterator<IHistoricalCommand> iterator = commandQueue.iterator();
        boolean found = !afterId.isPresent();
        while (iterator.hasNext()) {
            IHistoricalCommand current = iterator.next();
            if (found && current.playersVisibleTo().contains(playerId)) {
                output.add(current);
            } else if (found && !current.playersVisibleTo().contains(playerId)) {
                output.add(current.getAlternate());
            } else if (afterId.map((UUID gameId) -> current.getId().equals(gameId)).orElseGet(() -> true)) {
                found = true;
            }
        }
        return output;
    }

    private IHistoricalCommand get(UUID commandId) throws ResourceNotFoundException {
        IHistoricalCommand output = commandQueue.stream().filter(command -> command.getId().equals(commandId)).findFirst().orElseThrow(() -> new ResourceNotFoundException());
        return output;
    }

    public void insert(IHistoricalCommand command) throws ModelActionException {
        try {
            get(command.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            commandQueue.add(command);
        }
    }

    public UUID getId() {
        return this.id;
    }
}
