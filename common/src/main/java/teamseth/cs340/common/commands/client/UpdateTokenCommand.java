package teamseth.cs340.common.commands.client;

import java.util.UUID;

import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UpdateTokenCommand implements IClientCommand {
    private static final long serialVersionUID = 3323425385098081746L;

    private AuthToken token;
    private UUID userId;

    public UpdateTokenCommand(AuthToken token) throws UnauthorizedException {
        this.token = token;
        this.userId = token.getUser();
    }

    @Override
    public Result call() {
        return new Result(() -> {
            Login.getInstance().setToken(this.token);
            Login.getInstance().setUserId(this.userId);
            return null;
        });
    }
}
