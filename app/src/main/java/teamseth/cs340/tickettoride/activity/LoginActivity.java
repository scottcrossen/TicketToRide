package teamseth.cs340.tickettoride.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.fragment.LoginFragment;
import teamseth.cs340.tickettoride.util.ActivityDecider;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class LoginActivity extends FragmentActivity implements Observer {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fm = this.getSupportFragmentManager();

        loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFragment);

        if (loginFragment == null) {
            fm.beginTransaction()
                    .add(R.id.loginFragment, new LoginFragment())//.newInstance(p))
                    .commit();
        }

        Login.getInstance().addObserver(this);
        //if the user is found in the database and login is pressed, go to GameListActivity
        //if user not found in database and login pressed, toast saying no user found
        //if register pressed, make new user in database,
    }

    @Override
    public void update(Observable observable, Object o) {
        this.runOnUiThread(() -> {
            if (Login.getInstance().getToken() != null)
                startActivity(new Intent(this, ActivityDecider.next()));
        });
    }

    @Override
    public void onDestroy() {
        PlayerTurnTracker.getInstance().safeExit(null);
        Login.getInstance().logout();
        super.onDestroy();
    }
}
