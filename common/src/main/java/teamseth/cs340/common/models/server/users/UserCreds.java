package teamseth.cs340.common.models.server.users;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UserCreds {
    private String username;
    private String password;

    public UserCreds(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return this.username;}
}
