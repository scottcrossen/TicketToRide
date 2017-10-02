package teamseth.cs340.common.util;

import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.root.server.models.authentication.AuthToken;
import teamseth.cs340.common.root.server.models.authentication.AuthTokenModel;
import teamseth.cs340.common.root.server.models.authentication.AuthType;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AuthAction {
    protected static void user(AuthToken token) throws UnauthorizedException {
        if (!AuthTokenModel.getInstance().authorized(token, AuthType.user)) throw new UnauthorizedException();
    }
}
