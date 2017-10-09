package teamseth.cs340.common.util.client;

import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Config {
    private static Config instance;
    public static  Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private static AuthToken token;
    private static String serverPort = "8081";
    private static String serverHost = "10.0.2.2";

    public static AuthToken getToken() {
        return token;
    }

    public static void setToken(AuthToken token) {
        Config.token = token;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static void setServerPort(String serverPort) {
        Config.serverPort = serverPort;
    }


    public static String getServerHost() {
        return serverHost;
    }

    public static void setServerHost(String serverHost) {
        Config.serverHost = serverHost;
    }
}
