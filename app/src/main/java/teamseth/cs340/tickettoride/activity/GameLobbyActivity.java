package teamseth.cs340.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.tickettoride.fragment.GameLobbyFragment;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;

/**
 * Created by Seth on 9/29/2017.
 */
public class GameLobbyActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        ClientModelRoot.getInstance().games.addObserver(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.LobbyFragment);

        if (fragment == null) {
            fragment = new GameLobbyFragment();
            fm.beginTransaction()
                    .add(R.id.LobbyFragment, fragment)
                    .commit();
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!ClientModelRoot.getInstance().games.hasActive()) {
                Poller.getInstance(this.getApplicationContext()).reset();
                startActivity(new Intent(this, GameListActivity.class));
            } else if (ClientModelRoot.getInstance().games.hasActive() && ClientModelRoot.getInstance().games.getActive().getState().equals(GameState.START)) {
                Poller.getInstance(this.getApplicationContext()).reset();
                startActivity(new Intent(this, GameActivity.class));
            }
        } catch (ResourceNotFoundException e) {
        }
    }
}
