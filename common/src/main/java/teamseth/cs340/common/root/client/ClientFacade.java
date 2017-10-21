package teamseth.cs340.common.root.client;

import java.util.ArrayList;
import java.util.Set;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.chat.Message;
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

    public void setActiveGame(Game current) { model.games.setActive(current); }

    public void clearActiveGame() { model.games.clearActive(); }

    public void addDestinationCard(DestinationCard destinationCard) { model.cards.addDestinationCard(destinationCard); }

    public void addResourceCard(ResourceColor resourceCard) { model.cards.addResourceCard(resourceCard); }

    public void addMessages(ArrayList<Message> newMessages) { model.chat.addMessages(newMessages); }

    public void addHistory(IHistoricalCommand command) { model.history.add(command); }

    public void removeDestinationCard(DestinationCard destinationCard){ model.cards.removeDestinationCard(destinationCard); }

    public void removeResourceCard(ResourceColor resourceCard) { model.cards.removeResourceCard(resourceCard); }
}

