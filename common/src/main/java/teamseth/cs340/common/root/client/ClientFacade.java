package teamseth.cs340.common.root.client;

import java.util.Set;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClientFacade implements IClient {
    private static  ClientFacade instance;

    public static  ClientFacade getInstance() {
        if(instance == null) {
            instance = new  ClientFacade();
        }
        return instance;
    }

    private ClientModelRoot model = ClientModelRoot.getInstance();

    public void addGames(Set<Game> newGames) {
        model.games.upsert(newGames);
    }
}
