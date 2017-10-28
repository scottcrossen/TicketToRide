package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class IncrementPlayerDestinationCardsCommand extends AlternativeHistoryCommand implements IClientCommand, IHistoricalCommand {
    public IncrementPlayerDestinationCardsCommand(IHistoricalCommand command) {
        super(command);
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addPlayerDestinationCard(this.owner); return null;});
    }

}
