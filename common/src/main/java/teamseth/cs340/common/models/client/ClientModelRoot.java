package teamseth.cs340.common.models.client;

import teamseth.cs340.common.models.client.board.Board;
import teamseth.cs340.common.models.client.cards.CurrentCards;
import teamseth.cs340.common.models.client.chat.CurrentChat;
import teamseth.cs340.common.models.client.games.GameModel;
import teamseth.cs340.common.models.client.history.CommandHistory;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClientModelRoot {
    private static ClientModelRoot instance;

    public static ClientModelRoot getInstance() {
        if(instance == null) {
            instance = new ClientModelRoot();
        }
        return instance;
    }

    public static final GameModel games = GameModel.getInstance();
    public static final CurrentCards cards = CurrentCards.getInstance();
    public static final CurrentChat chat = CurrentChat.getInstance();
    public static final CommandHistory history = CommandHistory.getInstance();
    public static final Board board = Board.getInstance();
}
