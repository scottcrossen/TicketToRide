package teamseth.cs340.common.models.server;

import teamseth.cs340.common.models.server.cards.CardModel;
import teamseth.cs340.common.models.server.chat.ChatModel;
import teamseth.cs340.common.models.server.games.GameModel;
import teamseth.cs340.common.models.server.users.UserModel;

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
    public static ChatModel chat = ChatModel.getInstance();
    public static CardModel cards = CardModel.getInstance();
}
