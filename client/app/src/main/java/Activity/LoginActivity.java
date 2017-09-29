package Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import Model.*;
import Fragment.LoginFragment;

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
                    .add(R.id.personFragment, new LoginFragment())//.newInstance(p))
                    .commit();
        }
    }
}