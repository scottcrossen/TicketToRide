package teamseth.cs340.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.commands.server.GetGameCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.GameLobbyFragment;
import teamseth.cs340.tickettoride.util.Toaster;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GameLobbyActivity extends AppCompatActivity implements Observer {

    Game activeGame;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        try {
            Poller.getInstance(this.getApplicationContext()).addToJobs(new GetGameCommand(ClientModelRoot.games.getActive().getId()));
        } catch (Exception e) {
            startActivity(new Intent(this, GameListActivity.class));
            finish();
        }

        try {
            activeGame = ClientModelRoot.games.getActive();
        } catch (Exception e) {
            startActivity(new Intent(getApplicationContext(), GameListActivity.class));
        }

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.LobbyFragment);

        if (fragment == null) {
            fragment = new GameLobbyFragment();
            fm.beginTransaction()
                    .add(R.id.LobbyFragment, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ClientModelRoot.games.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ClientModelRoot.games.deleteObserver(this);
    }

    public void updateGame() {
        try {
            if (!ClientModelRoot.games.getActive().getPlayers().equals(this.activeGame.getPlayers())) {
                this.activeGame = ClientModelRoot.games.getActive();
                ((GameLobbyFragment) fragment).setFields(activeGame);
            }
        } catch (ResourceNotFoundException e) {
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!ClientModelRoot.games.hasActive()) {
                Poller.getInstance(this.getApplicationContext()).reset();
                startActivity(new Intent(this, GameListActivity.class));
            } else if (ClientModelRoot.games.hasActive() && ClientModelRoot.games.getActive().getState().equals(GameState.START)) {
                Poller.getInstance(this.getApplicationContext()).reset();
                this.runOnUiThread(() -> Toaster.getInstance().makeToast(this.getApplicationContext(), "New game started."));
                startActivity(new Intent(this, ChooseDestCardsActivity.class));
            } else {
                this.runOnUiThread(() -> updateGame());
            }
        } catch (ResourceNotFoundException e) {
        }
    }
}
