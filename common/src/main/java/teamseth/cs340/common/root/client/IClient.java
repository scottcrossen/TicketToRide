package teamseth.cs340.common.root.client;

import java.util.ArrayList;
import java.util.Set;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.models.server.games.Game;

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
}
