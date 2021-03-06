package teamseth.cs340.common.root.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.games.GameState;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IClient {
    public void addGames(Set<Game> newGames);
    public void setActiveGame(Game active);
    public void clearActiveGame();
    public void addDestinationCard(DestinationCard destinationCard);
    public void addResourceCard(ResourceColor resourceCard);
    public void addMessages(ArrayList<Message> newMessages);
    public void addHistory(IHistoricalCommand command);
    public void removeDestinationCard(DestinationCard destinationCard);
    public void removeResourceCard(ResourceColor resourceCard);
    public void setActiveState(GameState state) throws ResourceNotFoundException;
    public void addPlayerDestinationCard(UUID playerId);
    public void addPlayerResourceCard(UUID playerId);
    public void claimRouteByPlayer(UUID userId, CityName city1, CityName city2, List<ResourceColor> colors, Optional<ResourceColor> routeColor) throws ModelActionException;
    public void seedCards(List<ResourceColor> cards);
    public void replaceCard(ResourceColor oldCard, Optional<ResourceColor> newCard) throws ResourceNotFoundException;
    public void incrementPlayerPoints(UUID playerId, int amount);
    public void addPlayerTrainCarts(UUID playerId, int amount);
    public void removePlayerTrainCarts(UUID playerId, int amount);
    public void nextTurn();
    public void setTurn(UUID playerId);
    public void updatePlayerPointsByDestinationCard(UUID playerId, DestinationCard card) throws ResourceNotFoundException;
    public void setPlayerLongestPath(UUID playerId);
}
