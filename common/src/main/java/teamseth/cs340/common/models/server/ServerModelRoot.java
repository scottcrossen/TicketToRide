package teamseth.cs340.common.models.server;

import teamseth.cs340.common.models.server.users.UserModel;
import teamseth.cs340.common.models.server.games.GameModel;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ServerModelRoot {
    private static ServerModelRoot instance;

    public static ServerModelRoot getInstance() {
        if(instance == null) {
            instance = new ServerModelRoot();
        }
        return instance;
    }

    public static UserModel users = UserModel.getInstance();
    public static GameModel games = GameModel.getInstance();
}
