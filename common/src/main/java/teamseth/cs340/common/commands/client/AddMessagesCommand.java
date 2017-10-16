package teamseth.cs340.common.commands.client;

import java.util.ArrayList;

import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AddMessagesCommand implements IClientCommand {

    private ArrayList<Message> messages;

    public AddMessagesCommand(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addMessages(messages); return null;});
    }
}
