package teamseth.cs340.common.util;

import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.authentication.AuthToken;
import teamseth.cs340.common.models.server.authentication.AuthTokenModel;
import teamseth.cs340.common.models.server.authentication.AuthType;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AuthAction {
    protected static void user(AuthToken token) throws UnauthorizedException {
        if (!AuthTokenModel.getInstance().authorized(token, AuthType.user)) throw new UnauthorizedException();
    }
}
