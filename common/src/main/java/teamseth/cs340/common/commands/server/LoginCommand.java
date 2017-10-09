package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.client.UpdateTokenCommand;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class LoginCommand implements IServerCommand {
    private static final long serialVersionUID = 2775134029108708839L;

    private UserCreds creds;

    public LoginCommand(UserCreds user){
        this.creds = user;
    }

    public Result<UpdateTokenCommand> call() {
        return new Result<UpdateTokenCommand>(() -> new UpdateTokenCommand(ServerFacade.getInstance().login(this.creds)));
    }
}
