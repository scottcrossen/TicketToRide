package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.client.UpdateTokenCommand;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * Created by mike on 10/9/17.
 */

public class LogoutCommand implements IServerCommand {
    private static final long serialVersionUID = 4657399921120007892L;

    private UserCreds creds;

    public LogoutCommand(UserCreds user){
        this.creds = user;
    }

    public Result<UpdateTokenCommand> call() {
        return new Result<UpdateTokenCommand>(() -> {
            AuthToken token = ServerFacade.getInstance().login(this.creds);
            return new UpdateTokenCommand(token);
        });
    }
}
