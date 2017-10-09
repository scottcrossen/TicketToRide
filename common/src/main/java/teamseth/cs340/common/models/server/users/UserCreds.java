package teamseth.cs340.common.models.server.users;

import java.io.Serializable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UserCreds implements Serializable {
    private static final long serialVersionUID = -6099562618130159037L;
    private String username;
    private String password;

    public UserCreds(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return this.username;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCreds userCreds = (UserCreds) o;

        if (username != null ? !username.equals(userCreds.username) : userCreds.username != null)
            return false;
        return password != null ? password.equals(userCreds.password) : userCreds.password == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
