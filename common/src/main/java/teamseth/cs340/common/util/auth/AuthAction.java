package teamseth.cs340.common.util.auth;

import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.util.Logger;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AuthAction {
    private static final long serialVersionUID = 4606139126406410445L;
    protected static void user(AuthToken token) throws UnauthorizedException {
        if (!token.isValid(AuthType.user) && !token.isValid(AuthType.admin)) {
            Logger.info("Unauthorized access attempted");
            throw new UnauthorizedException();
        }
    }
    protected static void admin(AuthToken token) throws UnauthorizedException {
        if (!token.isValid(AuthType.admin)) {
            Logger.info("Unauthorized access attempted");
            throw new UnauthorizedException();
        }
    }
}
