package teamseth.cs340.common.models.server.users;

import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UserModel implements IModel<User> {
    private static UserModel instance;

    public static UserModel getInstance() {
        if(instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    private HashSet<User> users = new HashSet<User>();

    public AuthToken login(UserCreds credentials) throws ResourceNotFoundException, UnauthorizedException {
        // Check if user exists
        User user = getByName(credentials);
        // Check if user is authorized
        if (!user.getUserCreds().equals(credentials)) throw new UnauthorizedException();
        // Generate auth token
        AuthToken token = new AuthToken(user);
        // Return the token
        return token;
    }

    public AuthToken register(UserCreds credentials) throws UnauthorizedException {
        try {
            User user = getByName(credentials);
            AuthToken token = new AuthToken(user);
            return token;
        } catch (ResourceNotFoundException e){
            User user = new User(credentials);
            users.add(user);
            AuthToken token = new AuthToken(user);
            return token;
        }
    }

    private User getByName(UserCreds creds) throws ResourceNotFoundException {
        return users.stream().filter((user) -> user.getUserCreds().getUsername().equals(creds.getUsername())).findFirst().orElseThrow(() -> new ResourceNotFoundException());
    }

    public User getById(UUID id) throws ResourceNotFoundException {
        return users.stream().filter((user) -> user.getId().equals(id)).findFirst().orElseThrow(() -> new ResourceNotFoundException());
    }
}
