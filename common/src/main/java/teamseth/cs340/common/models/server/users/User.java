package teamseth.cs340.common.models.server.users;

import java.io.Serializable;
import java.util.UUID;

import teamseth.cs340.common.util.auth.AuthType;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class User implements Serializable, Comparable<User> {
    private UserCreds credentials;
    private AuthType privilege = AuthType.user;
    private UUID id = UUID.randomUUID();

    public User(UserCreds credentials) {
        this.credentials = credentials;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    public UUID getId() { return this.id;}
    public UserCreds getUserCreds() { return this.credentials; }
    public AuthType getAuthType() { return this.privilege; }

    public int compareTo(User user) {
        return this.id.compareTo(user.id);
    }
}
