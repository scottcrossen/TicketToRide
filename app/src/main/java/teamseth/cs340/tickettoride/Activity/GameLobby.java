package teamseth.cs340.tickettoride.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import teamseth.cs340.tickettoride.Fragment.GameLobbyFragment;
import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 9/29/2017.
 */
public class GameLobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.game_lobby_fragment_container);

        if (fragment == null) {
            fragment = new GameLobbyFragment();
            fm.beginTransaction()
                    .add(R.id.game_lobby_fragment_container, fragment)
                    .commit();
        }
    }


}
