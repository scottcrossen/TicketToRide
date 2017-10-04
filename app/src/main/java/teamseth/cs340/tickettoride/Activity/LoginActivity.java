package teamseth.cs340.tickettoride.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import teamseth.cs340.tickettoride.Fragment.LoginFragment;
import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 9/29/2017.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentManager fm = this.getSupportFragmentManager();

        LoginFragment loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFragment);

        if (loginFragment == null) {
            fm.beginTransaction()
                    .add(R.id.loginFragment, new LoginFragment())//.newInstance(p))
                    .commit();
        }
        //if the user is found in the database and login is pressed, go to GameListActivity
        //if user not found in database and login pressed, toast saying no user found
        //if register pressed, make new user in database
    }
}
