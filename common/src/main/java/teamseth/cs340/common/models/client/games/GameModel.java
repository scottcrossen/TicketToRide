package teamseth.cs340.common.models.client.games;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.util.client.Login;

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

    public void resetModel() {
        state = new InactiveGameState();
        setChanged();
        notifyObservers();
    }

    private IGameModelState state = new InactiveGameState();

    public void upsert(Set<Game> newGames) {
        // Check if any of the games include this user
        Iterator<Game> iterator0 = newGames.iterator();
        while (iterator0.hasNext()) {
            Game currentGame0 = iterator0.next();
            if (!currentGame0.getState().equals(GameState.DELETED) && !currentGame0.getState().equals(GameState.FINISHED) && currentGame0.hasPlayer(Login.getInstance().getUserId())) {
                this.state.setActive(currentGame0);
                setChanged();
                notifyObservers();
                return;
            }
        }
        state.addGames(newGames);
        if (newGames.size() > 0 && !state.hasActive()) {
            setChanged();
            notifyObservers();
        }
    }

    public HashSet<Game> getAll(){
        return state.getAll();
    }

    public Game getByName(String gameName) {
        for (Game game : state.getAll()) {
            if(game.name().contentEquals(gameName)) {
                return game;
            }
        }
        return null;
    }

    public void setActive(Game current) {
        this.state.setActive(current);
        setChanged();
        notifyObservers();
    }

    public void setActiveState(GameState state) throws ResourceNotFoundException {
        this.state.getActive().setState(state);
        setChanged();
        notifyObservers();
    }

    public void clearActive() {
        this.state.setInactive();
        setChanged();
        notifyObservers();
    }

    public Game getActive() throws ResourceNotFoundException {
        return state.getActive();
    }

    public void nextTurn() {
        state.nextTurn();
        setChanged();
        notifyObservers();
    }

    public boolean hasActive() {
        return this.state.hasActive();
    }

    private interface IGameModelState {
        Game getActive() throws ResourceNotFoundException;
        void setActive(Game active);
        void setInactive();
        boolean hasActive();
        void nextTurn();
        void addGames(Set<Game> newGames);
        HashSet<Game> getAll();
    }

    private class ActiveGameState implements IGameModelState {
        private Game active;
        public ActiveGameState(Game current) {
            this.active = current;
        }
        public Game getActive() {
            return active;
        }
        public void setActive(Game game) {
            state = new ActiveGameState(game);
        }
        public void setInactive() {
            state = new InactiveGameState();
        }
        public boolean hasActive() {
            return true;
        }
        public void nextTurn() { active.nextTurn(); }
        public void addGames(Set<Game> newGames) {
            return;
        }
        public HashSet<Game> getAll(){
            HashSet<Game> output = new HashSet<>();
            output.add(active);
            return output;
        }
    }

    private class InactiveGameState implements IGameModelState {
        public InactiveGameState() {
            games = new HashSet<Game>();
        }
        private HashSet<Game> games;
        public Game getActive() throws ResourceNotFoundException {
            throw new ResourceNotFoundException();
        }
        public void setActive(Game game) {
            state = new ActiveGameState(game);
        }
        public void setInactive() {
            state = new InactiveGameState();
        }
        public boolean hasActive() {
            return false;
        }
        public void nextTurn() { }
        public void addGames(Set<Game> newGames) {
            int addSize = newGames.size();
            // Overwrite old data with new data on uuid.
            Iterator<Game> iterator1 = games.iterator();
            while (iterator1.hasNext()) {
                Game currentGame1 = iterator1.next();
                Iterator<Game> iterator2 = newGames.iterator();
                while (iterator2.hasNext()) {
                    Game currentGame2 = iterator2.next();
                    if (currentGame1.getId().equals(currentGame2.getId())) {
                        iterator1.remove();
                    }
                }
            }
            // Add all remaining
            games.addAll(newGames);
        }
        public HashSet<Game> getAll(){
            return games;
        }
    }
}
