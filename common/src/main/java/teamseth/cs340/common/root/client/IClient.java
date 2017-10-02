package teamseth.cs340.common.root.client;

import java.util.Set;

import teamseth.cs340.common.models.server.games.Game;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IClient {
    public void addGames(Set<Game> newGames);
}