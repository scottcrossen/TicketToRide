package teamseth.cs340.tickettoride.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.time.Instant;
import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.commands.server.ListGamesAfterCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.Fragment.GameListFragment;
import teamseth.cs340.tickettoride.Interface.FragmentChangeListener;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;

/**
 * Created by Seth on 9/29/2017.
 */
public class GameListActivity extends AppCompatActivity implements FragmentChangeListener, Observer {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        ClientModelRoot.getInstance().games.addObserver(this);
        Poller.getInstance().addToJobs(new ListGamesAfterCommand(Instant.now()));
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.game_list_fragment_container);

        if (fragment == null) {
            fragment = new GameListFragment();
            fm.beginTransaction()
                    .add(R.id.game_list_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {

    }

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                parentIntent.putExtra("message", "filter");
                startActivity(parentIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (ClientModelRoot.getInstance().games.hasActive()) {
            Poller.getInstance().reset();
            startActivity(new Intent(this, GameLobbyActivity.class));
        }
    }
}

