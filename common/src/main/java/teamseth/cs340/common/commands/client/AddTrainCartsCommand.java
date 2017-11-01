package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AddTrainCartsCommand implements IHistoricalCommand {
    private static final long serialVersionUID = -3062231583906476942L;
    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private int points;
    private UUID owner;

    public AddTrainCartsCommand(int points, Set<UUID> allPlayers, UUID owner) {
        this.players = allPlayers;
        this.owner = owner;
        this.points = points;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().addPlayerTrainCarts(owner, points); return null;});
    }

    public UUID getId() {
        return id;
    }

    public Set<UUID> playersVisibleTo() {
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }

    public String getDescription() {
        return "got more train carts";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
