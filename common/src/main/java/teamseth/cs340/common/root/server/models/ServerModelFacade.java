package teamseth.cs340.common.root.server.models;

import teamseth.cs340.common.root.server.models.authentication.UserModel;
import teamseth.cs340.common.root.server.models.games.GameModel;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ServerModelFacade {
    private static ServerModelFacade instance;

    public static ServerModelFacade getInstance() {
        if(instance == null) {
            instance = new ServerModelFacade();
        }
        return instance;
    }

    public static UserModel users = UserModel.getInstance();
    public static GameModel games = GameModel.getInstance();
}
