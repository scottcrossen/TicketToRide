package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AddResourceCardCommand implements IClientCommand {

    private ResourceColor card;

    public AddResourceCardCommand(ResourceColor card) {
        this.card = card;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addResourceCard(card); return null;});
    }
}
