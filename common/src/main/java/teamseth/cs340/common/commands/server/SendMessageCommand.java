
package teamseth.cs340.common.commands.server;

import java.util.UUID;

import teamseth.cs340.common.commands.client.AddMessagesCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SendMessageCommand implements IServerCommand {

    Message message;
    UUID chatRoom;
    private AuthToken token = Login.getInstance().getToken();
    IServerCommand execAfter;

    public SendMessageCommand(Message message) throws ResourceNotFoundException, UnauthorizedException {
        this.message = message;
        this.chatRoom = ClientModelRoot.games.getActive().getChatRoom();
        this.execAfter = new GetNewMessagesCommand();
    }

    public Result<AddMessagesCommand> call() {
        return new Result(() -> {
            ServerFacade.getInstance().sendMessage(chatRoom, message, token);
            return execAfter.call();
        });
    }
}
