package teamseth.cs340.tickettoride.util;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.activity.ChooseDestCardsActivity;
import teamseth.cs340.tickettoride.activity.GameListActivity;
import teamseth.cs340.tickettoride.activity.GameLobbyActivity;
import teamseth.cs340.tickettoride.activity.LoginActivity;
import teamseth.cs340.tickettoride.activity.MapActivity;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class ActivityDecider {
    public static Class<?> next() {
        if (Login.getInstance().getToken() == null)
            return LoginActivity.class;
        try {
            GameState state = ClientModelRoot.games.getActive().getState();
            switch(state) {
                case PREGAME:
                    return GameLobbyActivity.class;
                case START:
                    return ChooseDestCardsActivity.class;
                case PLAYING:
                    return MapActivity.class;
                default:
                    return GameListActivity.class;
            }
        } catch(Exception e) {
            return GameListActivity.class;
        }
    }

}
