package teamseth.cs340.common.models.server;

import teamseth.cs340.common.models.server.boards.BoardModel;
import teamseth.cs340.common.models.server.cards.CardModel;
import teamseth.cs340.common.models.server.chat.ChatModel;
import teamseth.cs340.common.models.server.games.GameModel;
import teamseth.cs340.common.models.server.history.HistoryModel;
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

    public static final UserModel users = UserModel.getInstance();
    public static final GameModel games = GameModel.getInstance();
    public static final ChatModel chat = ChatModel.getInstance();
    public static final CardModel cards = CardModel.getInstance();
    public static final HistoryModel history = HistoryModel.getInstance();
    public static final BoardModel board = BoardModel.getInstance();
}
