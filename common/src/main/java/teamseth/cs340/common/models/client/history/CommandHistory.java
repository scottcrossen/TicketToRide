package teamseth.cs340.common.models.client.history;

import teamseth.cs340.common.models.client.games.GameModel;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CommandHistory {
    private static CommandHistory instance;

    public static CommandHistory getInstance() {
        if(instance == null) {
            instance = new CommandHistory();
        }
        return instance;
    }
}
