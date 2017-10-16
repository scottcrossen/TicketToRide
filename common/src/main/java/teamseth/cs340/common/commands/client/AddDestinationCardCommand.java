package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AddDestinationCardCommand implements IClientCommand {

    private DestinationCard card;

    public AddDestinationCardCommand(DestinationCard card) {
        this.card = card;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addDestinationCard(card); return null;});
    }

}
