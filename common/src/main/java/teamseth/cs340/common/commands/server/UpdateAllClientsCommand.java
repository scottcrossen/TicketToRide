package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UpdateAllClientsCommand extends QueueOffTurnCommand implements IServerCommand {

    IHistoricalCommand command;

    public UpdateAllClientsCommand(IHistoricalCommand command) throws ResourceNotFoundException {
        this.command = command;
    }

    public IHistoricalCommand clientCommand() throws Exception {
        return this.command;
    }
}
