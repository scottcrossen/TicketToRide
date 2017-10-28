package teamseth.cs340.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.commands.server.UpdateClientHistoryCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.OtherPlayersInfoFragment;

/**
 * Created by ajols on 10/28/2017.
 */

public class OtherPlayersInfoActivity extends AppCompatActivity implements Observer {

    Game activeGame;
    Fragment fragment;

    private boolean destinationCardsDrawn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_players_info);

        /*try {
            Poller.getInstance(this.getApplicationContext()).addToJobs(new UpdateClientHistoryCommand());
        } catch (Exception e) {
            startActivity(new Intent(this, GameListActivity.class));
            finish();
        }*/

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.other_players_info_fragment_container);

        if (fragment == null) {
            fragment = new OtherPlayersInfoFragment();
            fm.beginTransaction()
                    .add(R.id.other_players_info_fragment_container, fragment)
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

    @Override
    public void update(Observable o, Object arg) {
        /*if (!destinationCardsDrawn) {
            ((ChooseDestCardsFragment) fragment).setDestinationCards(ClientModelRoot.cards.getDestinationCards());
            destinationCardsDrawn = true;
        } else try {
            if (ClientModelRoot.games.getActive().getState() == GameState.PLAYING) {
                Poller.getInstance(this.getApplicationContext()).reset();
                // TODO: Go to new activity.
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
