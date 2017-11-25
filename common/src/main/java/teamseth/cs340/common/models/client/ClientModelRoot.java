package teamseth.cs340.common.models.client;

import teamseth.cs340.common.models.client.board.Board;
import teamseth.cs340.common.models.client.cards.CurrentCards;
import teamseth.cs340.common.models.client.carts.PlayerCarts;
import teamseth.cs340.common.models.client.chat.CurrentChat;
import teamseth.cs340.common.models.client.games.GameModel;
import teamseth.cs340.common.models.client.history.CommandHistory;
import teamseth.cs340.common.models.client.points.PlayerPoints;

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
    public static final PlayerPoints points = PlayerPoints.getInstance();
    public static final PlayerCarts carts = PlayerCarts.getInstance();
    public void resetAll() {
        ClientModelRoot.cards.deleteObservers();
        ClientModelRoot.chat.deleteObservers();
        ClientModelRoot.history.deleteObservers();
        ClientModelRoot.board.deleteObservers();
        ClientModelRoot.points.deleteObservers();
        ClientModelRoot.carts.deleteObservers();
        ClientModelRoot.cards.faceUp.deleteObservers();
        ClientModelRoot.cards.others.deleteObservers();
        ClientModelRoot.games.deleteObservers();
        games.resetModel();
        cards.resetModel();
        chat.resetModel();
        history.resetModel();
        board.resetModel();
        points.resetModel();
        carts.resetModel();
    }
}
