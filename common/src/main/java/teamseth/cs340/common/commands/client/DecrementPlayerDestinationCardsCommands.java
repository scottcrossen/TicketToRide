package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DecrementPlayerDestinationCardsCommands extends AlternativeHistoryCommand implements IHistoricalCommand {
    private static final long serialVersionUID = 1904933804967342602L;

    public DecrementPlayerDestinationCardsCommands(IHistoricalCommand command) {
        super(command);
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().removePlayerDestinationCard(this.owner); return null;});
    }


}
