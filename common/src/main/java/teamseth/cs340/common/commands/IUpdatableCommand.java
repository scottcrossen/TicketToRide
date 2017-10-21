package teamseth.cs340.common.commands;

import java.time.Instant;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IUpdatableCommand {
    public void applyUpdate(Instant time);
}
