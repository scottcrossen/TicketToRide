package teamseth.cs340.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import teamseth.cs340.common.commands.server.UpdateClientHistoryCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.ChooseDestCardsFragment;
import teamseth.cs340.tickettoride.fragment.SingleTextFragment;
import teamseth.cs340.tickettoride.util.ActivityDecider;
import teamseth.cs340.tickettoride.util.Toaster;

/**
 * Created by ajols on 10/14/2017.
 */

public class ChooseDestCardsActivity extends AppCompatActivity implements Observer, ChooseDestCardsFragment.Caller {

    Fragment fragment;

    @Override
    public void onBackPressed()
    {
        backPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void backPressed() {
        Login.getInstance().logout();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dest_cards);

        try {
            Poller.getInstance(this.getApplicationContext()).addToJobs(new UpdateClientHistoryCommand());
        } catch (Exception e) {
            startActivity(new Intent(this, GameListActivity.class));
            finish();
        }

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.choose_dest_cards_fragment_container);

        if (fragment == null) {
            fragment = ClientModelRoot.history.playerChoseInitialCards(Login.getUserId()) ? SingleTextFragment.newV4Instance(Optional.empty(), "Waiting for other players...") : new ChooseDestCardsFragment();
            fm.beginTransaction()
                    .add(R.id.choose_dest_cards_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ClientModelRoot.cards.addObserver(this);
        ClientModelRoot.games.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ClientModelRoot.cards.deleteObserver(this);
        ClientModelRoot.games.deleteObserver(this);
    }

    public void onFragmentSuccess() {
    }

    @Override
    public void update(Observable o, Object arg) {
        if (fragment instanceof ChooseDestCardsFragment) {
            if (ClientModelRoot.history.playerChoseInitialCards(Login.getUserId())) {
                Fragment newFragment = SingleTextFragment.newV4Instance(Optional.empty(), "Waiting for other players...");
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.choose_dest_cards_fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit();
                fragment = newFragment;
            } else {
                ((ChooseDestCardsFragment) fragment).setDestinationCards(ClientModelRoot.cards.getDestinationCards());
            }
        }
        try {
            if (!ClientModelRoot.games.getActive().getState().equals(GameState.START) ) {
                Poller.getInstance(this.getApplicationContext()).reset();
                this.runOnUiThread(() -> Toaster.getInstance().makeToast(this.getApplicationContext(), "Starting."));
                startActivity(new Intent(this, ActivityDecider.next()));
                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
