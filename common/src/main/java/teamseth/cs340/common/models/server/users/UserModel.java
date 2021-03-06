package teamseth.cs340.common.models.server.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.IServerModel;
import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.IDeltaCommand;
import teamseth.cs340.common.persistence.PersistenceAccess;
import teamseth.cs340.common.persistence.PersistenceTask;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UserModel implements IServerModel<User>, Serializable {
    private static UserModel instance;

    public static UserModel getInstance() {
        if(instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    private HashSet<User> users = new HashSet<User>();

    public CompletableFuture<Boolean> loadAllFromPersistence() {
        CompletableFuture<List<User>> persistentData = PersistenceAccess.getObjects(ModelObjectType.USER);
        return persistentData.thenApply((List<User> newData) -> {
            users.addAll(newData);
            return true;
        });
    }

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
            PersistenceTask.save(user, new IDeltaCommand<User>() {
                @Override
                public User call(User oldState) {
                    return user;
                }
            });
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
