package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Config;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UpdateTokenCommand implements IClientCommand {
    private static final long serialVersionUID = 3323425385098081746L;

    private AuthToken token;

    public UpdateTokenCommand(AuthToken token) {
        this.token = token;
    }

    @Override
    public Result call() {
        return new Result(() -> {Config.getInstance().setToken(this.token); return null;});
    }
}
