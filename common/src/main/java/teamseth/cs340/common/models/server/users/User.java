package teamseth.cs340.common.models.server.users;

import java.util.UUID;

import teamseth.cs340.common.util.auth.AuthType;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class User {
    private UserCreds credentials;
    private AuthType privilege = AuthType.user;
    private UUID id;

    public User(UserCreds credentials) {
        this.credentials = credentials;
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null) return false;
        else if (o.getClass() == UserCreds.class) return this.credentials == o;
        else if (o.getClass() == User.class) return this.id == ((User)o).id;
        else return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    public UUID getId() { return this.id;}
    public UserCreds getUserCreds() { return this.credentials; }
    public AuthType getAuthType() { return this.privilege; }
}
