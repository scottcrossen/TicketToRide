package teamseth.cs340.common.models.client.games;

import java.util.Iterator;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.games.Game;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GameModel extends Observable implements IModel<Game> {
    private static GameModel instance;

    public static GameModel getInstance() {
        if(instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    private Set<Game> games = new TreeSet<Game>();

    public void upsert(Set<Game> newGames) {
        // Overwrite old data with new data on uuid.
        Iterator<Game> iterator1 = games.iterator();
        while (iterator1.hasNext()) {
            Game currentGame1 = iterator1.next();
            Iterator<Game> iterator2 = games.iterator();
            while (iterator2.hasNext()) {
                Game currentGame2 = iterator2.next();
                if (currentGame1.getId() == currentGame2.getId()) {
                    currentGame1 = currentGame2;
                    iterator2.remove();
                }
            }
        }
        // Add all the remaining.
        games.addAll(newGames);
        setChanged();
        notifyObservers();
    }
}
