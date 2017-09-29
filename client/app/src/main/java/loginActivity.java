import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Seth on 9/29/2017.
 */
public class loginActivity extends AppCompatActivity {

    private LoginFragment loginFragment;

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFragment);
        ModelApp.SINGLETON.setIconMapPart("n");
        if (ModelApp.SINGLETON.getCurrentUser() == null) {
            ModelApp.SINGLETON.start(this);
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.loginFragment, loginFragment)
                        .commit();
            }
        } else {
            FragmentManager fm2 = this.getSupportFragmentManager();
            mapFragment = (MapFragment) fm2.findFragmentById(R.id.mapFragment);
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.mapFragment, mapFragment)
                        .commit();
            }
        }
    }
}
