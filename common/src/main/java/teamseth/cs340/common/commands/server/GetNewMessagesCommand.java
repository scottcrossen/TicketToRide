package teamseth.cs340.common.commands.server;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.AddMessagesCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GetNewMessagesCommand implements IServerCommand {

    int lastSize = ClientModelRoot.chat.getMessages().size();
    UUID chatRoom;
    Set<UUID> allPlayers;
    UUID owner;

    public GetNewMessagesCommand() throws ResourceNotFoundException, UnauthorizedException {
        this.chatRoom = ClientModelRoot.games.getActive().getChatRoom();
        this.allPlayers = ClientModelRoot.games.getActive().getPlayers();
        this.owner = Login.getInstance().getToken().getUser();
    }

    public Result<AddMessagesCommand> call() {
        return new Result(() -> {
            ArrayList<Message> newMessages = ServerFacade.getInstance().getMessagesAfter(chatRoom, lastSize);
            return new AddMessagesCommand(newMessages, allPlayers, owner);
        });
    }

}
