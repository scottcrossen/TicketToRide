package teamseth.cs340.common.root.server.models.authentication;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UserCreds {
    String username;
    String password;

    public UserCreds(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
