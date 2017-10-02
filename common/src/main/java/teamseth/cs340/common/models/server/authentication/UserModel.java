package teamseth.cs340.common.models.server.authentication;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;

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

    private Set<User> users = new TreeSet<User>();

    public AuthToken login(UserCreds credentials) throws ResourceNotFoundException, UnauthorizedException {
        // Check if user exists
        User user = getByName(credentials);
        // Check if user is authorized
        if (user.credentials != credentials) throw new UnauthorizedException();
        // Generate auth token
        AuthToken token = new AuthToken(user);
        // Add auth token to list of model
        AuthTokenModel.getInstance().upsert(token);
        // Return the token
        return token;
    }

    public AuthToken register(UserCreds credentials) throws UnauthorizedException {
        try {
            User user = getByName(credentials);
            AuthToken token = new AuthToken(user);
            AuthTokenModel.getInstance().upsert(token);
            return token;
        } catch (ResourceNotFoundException e){
            User user = new User(credentials);
            users.add(user);
            AuthToken token = new AuthToken(user);
            AuthTokenModel.getInstance().upsert(token);
            return token;
        }
    }

    private User getByName(UserCreds creds) throws ResourceNotFoundException {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User currentUser = iterator.next();
            if (creds.username == currentUser.credentials.username) {
                return currentUser;
            }
        }
        throw new ResourceNotFoundException();
    }
}
