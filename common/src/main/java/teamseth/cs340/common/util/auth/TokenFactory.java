package teamseth.cs340.common.util.auth;

import java.util.Optional;

import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.util.server.Config;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public final class TokenFactory {
    /**
     * Factory method for creating auth tokens.
     * @param userOpt   An optional parameter for a user. 'Some' for users 'None' for system token.
     * @return          The requested token.
     */
    public static final AuthToken createToken(Optional<User> userOpt) {
        // Return normal token for users and system token for non-users.
        return userOpt.map((User user) -> new AuthToken(user)).orElseGet(() -> Config.getInstance().getSystemToken());
    }
}
