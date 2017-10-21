package teamseth.cs340.common.commands;

import java.util.Set;
import java.util.UUID;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IHistoricalCommand {
    UUID getId(); // For sorting and requesting
    Set<UUID> playersVisibleTo(); // Only allowed to be viewed by these people
    UUID playerOwnedby();
    String getDescription(); // For use with history display
}
