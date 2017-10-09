package teamseth.cs340.common.models.server.users;

import java.io.Serializable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UserCreds implements Serializable {
    private String username;
    private String password;

    public UserCreds(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return this.username;}
}
