package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.client.UpdateTokenCommand;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class RegisterCommand implements IServerCommand {
    private static final long serialVersionUID = 3488389607319749640L;

    private UserCreds creds;

    public RegisterCommand(UserCreds user){
        this.creds = user;
    }

    public Result<UpdateTokenCommand> call() {
        return new Result<UpdateTokenCommand>(() -> {
            AuthToken token = ServerFacade.getInstance().register(this.creds);
            return new UpdateTokenCommand(token);
        });
    }
}
