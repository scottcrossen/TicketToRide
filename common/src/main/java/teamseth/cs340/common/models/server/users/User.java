package teamseth.cs340.common.models.server.users;

import java.io.Serializable;
import java.util.UUID;

import teamseth.cs340.common.persistence.IStorable;
import teamseth.cs340.common.util.auth.AuthType;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class User implements Serializable, Comparable<User>, IStorable {
    private static final long serialVersionUID = 3867417489812381413L;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (credentials != null ? !credentials.equals(user.credentials) : user.credentials != null)
            return false;
        if (privilege != user.privilege) return false;
        return id != null ? id.equals(user.id) : user.id == null;

    }
}
