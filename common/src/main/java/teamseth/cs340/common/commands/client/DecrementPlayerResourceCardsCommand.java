package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DecrementPlayerResourceCardsCommand extends AlternativeHistoryCommand implements IHistoricalCommand {
    private static final long serialVersionUID = -4109997883164048409L;

    public DecrementPlayerResourceCardsCommand(IHistoricalCommand command) {
        super(command);
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().removePlayerResourceCard(this.owner); return null;});
    }

}
