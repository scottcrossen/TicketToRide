package teamseth.cs340.common.commands.client;

import java.util.Set;
import java.util.UUID;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IHistoricalCommand extends IClientCommand {
    UUID getId(); // For sorting and requesting
    Set<UUID> playersVisibleTo(); // Only allowed to be viewed by these people
    UUID playerOwnedby();
    String getDescription(); // For use with history display
    IHistoricalCommand getAlternate();
}
