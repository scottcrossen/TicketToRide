package teamseth.cs340.tickettoride.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.commands.server.UpdateClientHistoryCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.client.board.Board;
import teamseth.cs340.common.models.client.chat.CurrentChat;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.ChatFragment;
import teamseth.cs340.tickettoride.fragment.DemoFragment;
import teamseth.cs340.tickettoride.fragment.GameInfoFragment;
import teamseth.cs340.tickettoride.fragment.HistoryFragment;
import teamseth.cs340.tickettoride.fragment.MapFragment;
import teamseth.cs340.tickettoride.fragment.OtherPlayersFragment;
import teamseth.cs340.tickettoride.fragment.PlayerFragment;

/**
 * Created by Seth on 10/13/2017.
 */

public class MapActivity extends AppCompatActivity implements Observer {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTabTitles;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // use this to skip out on all the earlier activities.

        //DebugCommandShortcut.getInstance(getApplicationContext()).run();

        super.onCreate(savedInstanceState);


        try {
            Poller.getInstance(this.getApplicationContext()).addToJobs(new UpdateClientHistoryCommand());
        } catch (Exception e) {
        }


        setContentView(R.layout.activity_map);

        mTitle = mDrawerTitle = getTitle();
        mTabTitles = getResources().getStringArray(R.array.tabs_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTabTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        fragment = new MapFragment();
        Bundle args = new Bundle();
        switch(position) {
            default:
                fragment = new MapFragment();
                args.putInt(MapFragment.ARG_TAB_NUMBER, position);
                break;
            case 1:
                fragment = new PlayerFragment();
                args.putInt(PlayerFragment.ARG_TAB_NUMBER, position);
                break;
            case 2:
                fragment = new OtherPlayersFragment();
                args.putInt(OtherPlayersFragment.ARG_TAB_NUMBER, position);
                break;
            case 3:
                fragment = new GameInfoFragment();
                args.putInt(GameInfoFragment.ARG_TAB_NUMBER, position);
                break;
            case 4:
                fragment = new HistoryFragment();
                args.putInt(HistoryFragment.ARG_TAB_NUMBER, position);
                break;
            case 5:
                fragment = new ChatFragment();
                args.putInt(ChatFragment.ARG_TAB_NUMBER, position);
                break;
            case 6:
                fragment = new DemoFragment();
                args.putInt(ChatFragment.ARG_TAB_NUMBER, position);
                break;
            case 0:
                fragment = new MapFragment();
                args.putInt(MapFragment.ARG_TAB_NUMBER, position);
                break;
        }
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mTabTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //ClientModelRoot.cards.addObserver(this);
        ClientModelRoot.chat.addObserver(this);
        //ClientModelRoot.history.addObserver(this);
        ClientModelRoot.board.addObserver(this);
        //ClientModelRoot.points.addObserver(this);
        //ClientModelRoot.carts.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //ClientModelRoot.cards.deleteObserver(this);
        ClientModelRoot.chat.deleteObserver(this);
        //ClientModelRoot.history.deleteObserver(this);
        ClientModelRoot.board.deleteObserver(this);
        //ClientModelRoot.points.deleteObserver(this);
        //ClientModelRoot.carts.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (fragment instanceof MapFragment) {
            if (o instanceof Board) ((MapFragment) fragment).update();
        } else if (fragment instanceof ChatFragment) {
            if (o instanceof CurrentChat) ((ChatFragment) fragment).update();
        } else if (fragment instanceof GameInfoFragment) {

        } else if (fragment instanceof HistoryFragment) {

        } else if (fragment instanceof OtherPlayersFragment) {

        } else if (fragment instanceof PlayerFragment) {

        } else if (fragment instanceof DemoFragment) {

        }
    }
}
