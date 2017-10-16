package teamseth.cs340.tickettoride.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.time.Instant;
import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.commands.server.ListGamesAfterCommand;
import teamseth.cs340.common.commands.server.ListGamesCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.ChooseDestCardsFragment;

/**
 * Created by ajols on 10/14/2017.
 */

public class ChooseDestCardsActivity extends AppCompatActivity implements Observer {

    Game activeGame;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dest_cards);

        ClientModelRoot.getInstance().games.addObserver(this);
        Poller.getInstance(this.getApplicationContext()).addToJobs(new ListGamesAfterCommand(Instant.now()));
        (new CommandTask(getApplicationContext())).execute(new ListGamesCommand());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.choose_dest_cards_fragment_container);

        if (fragment == null) {
            fragment = new ChooseDestCardsFragment();
            fm.beginTransaction()
                    .add(R.id.choose_dest_cards_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
