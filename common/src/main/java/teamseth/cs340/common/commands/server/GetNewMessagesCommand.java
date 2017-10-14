package teamseth.cs340.common.commands.server;

import java.util.ArrayList;
import java.util.UUID;

import teamseth.cs340.common.commands.client.AddMessagesCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GetNewMessagesCommand implements IServerCommand {

    int lastSize = ClientModelRoot.chat.getMessages().size();
    UUID chatRoom;

    public GetNewMessagesCommand() throws ResourceNotFoundException {
        this.chatRoom = ClientModelRoot.games.getActive().getChatRoom();
    }

    public Result<AddMessagesCommand> call() {
        return new Result(() -> {
            ArrayList<Message> newMessages = ServerFacade.getInstance().getMessagesAfter(chatRoom, lastSize);
            return new AddMessagesCommand(newMessages);
        });
    }

}
