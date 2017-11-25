package teamseth.cs340.tickettoride.activity;

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
import teamseth.cs340.common.commands.server.ListGamesCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.FragmentChangeListener;
import teamseth.cs340.tickettoride.fragment.GameListFragment;
import teamseth.cs340.tickettoride.util.ActivityDecider;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GameListActivity extends AppCompatActivity implements FragmentChangeListener, Observer {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        Poller.getInstance(this.getApplicationContext()).addToJobs(new ListGamesAfterCommand(Instant.now()));
        (new CommandTask(getApplicationContext())).execute(new ListGamesCommand());
        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.game_list_fragment_container);

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

    private void backPressed() {
        PlayerTurnTracker.getInstance().safeLeave(getApplicationContext());
        Login.getInstance().logout();
        this.finish();
    }

    @Override
    public void onBackPressed()
    {
        backPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                parentIntent.putExtra("message", "filter");
                startActivity(parentIntent);
                backPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
    public void update(Observable observable, Object o) {
        this.runOnUiThread(() -> {
            if (ClientModelRoot.games.hasActive()) {
                Poller.getInstance(this.getApplicationContext()).reset();
                try {
                    startActivity(new Intent(this, ActivityDecider.next()));
                    this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (fragment instanceof GameListFragment) ((GameListFragment) fragment).update();
            }
        });
    }
}

