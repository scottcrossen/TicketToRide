package teamseth.cs340.tickettoride.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.fragment.GameFinishFragment;
import teamseth.cs340.tickettoride.util.ActivityDecider;

/**
 * Created by ajols on 11/11/2017.
 */

public class GameFinishActivity extends AppCompatActivity implements Observer {
    Fragment fragment;

    @Override
    public void onResume() {
        super.onResume();
        ClientModelRoot.games.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ClientModelRoot.games.deleteObserver(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finish);

        FragmentManager fm = getFragmentManager();
        fragment = fm.findFragmentById(R.id.game_finish_fragment_container);

        if (fragment == null) {
            fragment = new GameFinishFragment();
            fm.beginTransaction()
                    .add(R.id.game_finish_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!ClientModelRoot.games.hasActive()) {
                startActivity(new Intent(this, ActivityDecider.next()));
                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



