package teamseth.cs340.common.commands.server;

import teamseth.cs340.common.commands.ICommand;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class RegisterCommand implements ICommand {

    private UserCreds creds;

    public RegisterCommand(UserCreds user){
        this.creds = user;
    }

    public Result<AuthToken> call() {
        return new Result<AuthToken>(() -> ServerFacade.getInstance().register(this.creds));
    }
}
