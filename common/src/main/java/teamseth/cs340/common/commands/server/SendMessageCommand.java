
package teamseth.cs340.common.commands.server;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.AddMessagesCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SendMessageCommand extends QueueOffTurnCommand implements IServerCommand {

    private Message message;
    private UUID chatRoom;
    private AuthToken token = Login.getInstance().getToken();
    private Set<UUID> players = ClientModelRoot.games.getActive().getPlayers();

    public SendMessageCommand(Message message) throws ResourceNotFoundException, UnauthorizedException {
        this.message = message;
        this.chatRoom = ClientModelRoot.games.getActive().getChatRoom();
    }

    public IHistoricalCommand clientCommand() throws Exception {
        ServerFacade.getInstance().sendMessage(chatRoom, message, token);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        return new AddMessagesCommand(messages, players, token.getUser());
    }

}
