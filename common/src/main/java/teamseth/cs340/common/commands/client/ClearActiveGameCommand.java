package teamseth.cs340.common.commands.client;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClearActiveGameCommand implements IClientCommand {
    private static final long serialVersionUID = 8745654196668034605L;

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().clearActiveGame(); return null;});
    }
}
