package teamseth.cs340.common.models.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import teamseth.cs340.common.models.server.boards.BoardModel;
import teamseth.cs340.common.models.server.cards.CardModel;
import teamseth.cs340.common.models.server.carts.CartModel;
import teamseth.cs340.common.models.server.chat.ChatModel;
import teamseth.cs340.common.models.server.games.GameModel;
import teamseth.cs340.common.models.server.history.HistoryModel;
import teamseth.cs340.common.models.server.users.UserModel;
import teamseth.cs340.common.util.Logger;

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
    public static final CartModel carts = CartModel.getInstance();

    public static final boolean loadDataFromPersistence() {
        String[] myStringArray = {"a","b","c"};
        List<CompletableFuture<Boolean>> futures = new ArrayList<>(7);
        futures.add(users.loadAllFromPersistence());
        futures.add(games.loadAllFromPersistence());
        futures.add(chat.loadAllFromPersistence());
        futures.add(cards.loadAllFromPersistence());
        futures.add(history.loadAllFromPersistence());
        futures.add(board.loadAllFromPersistence());
        futures.add(carts.loadAllFromPersistence());
        CompletableFuture<List<Boolean>> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
            .thenApply(v -> futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList())
            );
        List<Boolean> joinedList = allFutures.join();
        boolean output = joinedList.stream().reduce(true, (Boolean val1, Boolean val2) -> (val1 != null && val2 != null && val1 == val2));
        if (!output) {
            Logger.error("Cannot recover state from persistence: Error returned from one or more queries");
        }
        return output;
    }
}
