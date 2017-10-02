package teamseth.cs340.common.root.server.models.authentication;

import java.util.UUID;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class User {
    public UserCreds credentials;
    public AuthType privilege = AuthType.user;
    public UUID id;

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
}
