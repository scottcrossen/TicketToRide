package teamseth.cs340.common.commands.server;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.ClaimRouteByPlayerCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.IncrementPlayerPointsCommand;
import teamseth.cs340.common.commands.client.RemoveTrainCartsCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.server.ServerFacade;
import teamseth.cs340.common.util.Result;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClaimRouteCommand implements IServerCommand {
    private static final long serialVersionUID = 1279009002627823014L;

    private AuthToken token = Login.getInstance().getToken();
    private Set<UUID> players;
    private UUID gameId;
    private CityName city1;
    private CityName city2;
    private ResourceColor color;
    private int colorNum;
    private UUID routeId;
    private UUID historyId;
    private UUID resourceDeckId;

    public ClaimRouteCommand(CityName city1, CityName city2, ResourceColor color) throws ResourceNotFoundException {
        this.players = ClientModelRoot.games.getActive().getPlayers();
        this.gameId = ClientModelRoot.games.getActive().getId();
        this.city1 = city1;
        this.city2 = city2;
        this.color = color;
        this.colorNum = (int) ClientModelRoot.cards.getResourceCards().stream().filter((ResourceColor currentColor) -> currentColor.equals(color)).count();
        this.historyId = ClientModelRoot.games.getActive().getHistory();
        this.routeId = ClientModelRoot.games.getActive().getRoutes();
        this.resourceDeckId = ClientModelRoot.games.getActive().getResourceDeck();
    }

    public Result<ReturnManyResourceCardsCommand> call() {
        return new Result<ReturnManyResourceCardsCommand>(() -> {
            int cost = ServerFacade.getInstance().claimRoute(this.routeId, this.city1, this.city2, this.color, this.colorNum, token);

            IHistoricalCommand historicalCommand = new ClaimRouteByPlayerCommand(city1, city2, color, players, token.getUser());
            if (historicalCommand != null) {
                ServerFacade.getInstance().addCommandToHistory(historyId, historicalCommand, token);
            }
            ServerFacade.getInstance().addCommandToHistory(historyId, new RemoveTrainCartsCommand(cost, players, token.getUser()), token);
            ServerFacade.getInstance().addCommandToHistory(historyId, new IncrementPlayerPointsCommand(getPointsFromLength(cost), players, token.getUser()), token);
            return new ReturnManyResourceCardsCommand(this.color, cost, resourceDeckId, historyId, token);
        });
    }

    private int getPointsFromLength(int length) {
        switch (length) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            case 5:
                return 10;
            case 6:
                return 15;
            default:
                return 0;
        }
    }
}