package teamseth.cs340.common.util.client;

import java.io.Serializable;
import java.util.Observable;
import java.util.UUID;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Login extends Observable implements Serializable {
    private static final long serialVersionUID = 7466912604359963018L;
    private static Login instance;
    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    private static AuthToken token;
    private static String serverPort = "8081";
    private static String serverHost = "10.0.2.2";
    private static String username;
    private static String password;
    private int cardsDrawn = 0;
    private static UUID player;

    public int getCardsDrawn() {
        return cardsDrawn;
    }

    public void setCardsDrawn(int cardsDrawn) {
        this.cardsDrawn = cardsDrawn;
    }

    public static UUID getUserId() { return player;}

    public static void setUserId(UUID player) { Login.player = player; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Login.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Login.password = password;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        Login.token = token;
        setChanged();
        notifyObservers();
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        Login.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        Login.serverHost = serverHost;
    }

    public void logout() {
        this.token = null;
        ClientModelRoot.getInstance().resetAll();
    }
}
