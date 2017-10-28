package teamseth.cs340.common.commands.server;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.ClaimRouteByPlayerCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
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

    private AuthToken token = Login.getInstance().getToken();
    private Set<UUID> players;
    private UUID gameId;
    private CityName city1;
    private CityName city2;
    private ResourceColor color;
    private int colorNum;
    private UUID routeId;
    private UUID historyId;

    public ClaimRouteCommand(CityName city1, CityName city2, ResourceColor color) throws ResourceNotFoundException {
        this.players = ClientModelRoot.games.getActive().getPlayers();
        this.gameId = ClientModelRoot.games.getActive().getId();
        this.city1 = city1;
        this.city2 = city2;
        this.color = color;
        this.colorNum = (int) ClientModelRoot.cards.getResourceCards().stream().filter((ResourceColor currentColor) -> currentColor.equals(color)).count();
        this.historyId = ClientModelRoot.games.getActive().getHistory();
        this.routeId = ClientModelRoot.games.getActive().getRoutes();
    }

    public Result<ReturnManyResourceCardsCommand> call() {
        return new Result<ReturnManyResourceCardsCommand>(() -> {
            int cost = ServerFacade.getInstance().claimRoute(this.routeId, this.city1, this.city2, this.color, this.colorNum, token);

            IHistoricalCommand historicalCommand = new ClaimRouteByPlayerCommand(city1, city2, color, players, token.getUser());
            if (historicalCommand != null) {
                ServerFacade.getInstance().addCommandToHistory(historyId, historicalCommand, token);
            }
            return new ReturnManyResourceCardsCommand(this.color, cost);
        });
    }

}
