package teamseth.cs340.tickettoride.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
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

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import teamseth.cs340.common.commands.server.UpdateClientHistoryCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.client.board.Board;
import teamseth.cs340.common.models.client.cards.CurrentCards;
import teamseth.cs340.common.models.client.cards.FaceUpCards;
import teamseth.cs340.common.models.client.cards.OtherPlayerCards;
import teamseth.cs340.common.models.client.carts.PlayerCarts;
import teamseth.cs340.common.models.client.chat.CurrentChat;
import teamseth.cs340.common.models.client.games.GameModel;
import teamseth.cs340.common.models.client.history.CommandHistory;
import teamseth.cs340.common.models.client.points.PlayerPoints;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.Poller;
import teamseth.cs340.tickettoride.fragment.ChatFragment;
import teamseth.cs340.tickettoride.fragment.DemoFragment;
import teamseth.cs340.tickettoride.fragment.GameInfoFragment;
import teamseth.cs340.tickettoride.fragment.HistoryFragment;
import teamseth.cs340.tickettoride.fragment.IUpdatableFragment;
import teamseth.cs340.tickettoride.fragment.MapFragment;
import teamseth.cs340.tickettoride.fragment.NewDestCardsFragment;
import teamseth.cs340.tickettoride.fragment.OtherPlayersFragment;
import teamseth.cs340.tickettoride.fragment.PlayerFragment;
import teamseth.cs340.tickettoride.util.ActivityDecider;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;

/**
 * Created by Seth on 10/13/2017.
 */

public class MapActivity extends AppCompatActivity implements Observer, NewDestCardsFragment.Caller {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTabTitles;
    private Fragment fragment;

    @Override
    public void onNewDestCardsChosen() {
        selectItem(3);
    }

    public void disableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // use this to skip out on all the earlier activities.

        super.onCreate(savedInstanceState);


        try {
            Poller.getInstance(this.getApplicationContext()).addToJobs(new UpdateClientHistoryCommand());
        } catch (Exception e) {
        }

        setContentView(R.layout.activity_map);

        mTitle = mDrawerTitle = getTitle();
        mTabTitles = getResources().getStringArray(R.array.tabs_array);
        if (!Login.debug) mTabTitles = Arrays.copyOf(mTabTitles, mTabTitles.length - 1);
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
            case (android.R.id.home):
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            // Warning: If you were in the middle of a turn action be sure to finalize the turn before this executes.
            fm.popBackStack();
        } else {
            PlayerTurnTracker.getInstance().safeExit(getApplicationContext());
            Login.getInstance().logout();
            this.finish();
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
                if (Login.debug) {
                    fragment = new DemoFragment();
                    args.putInt(DemoFragment.ARG_TAB_NUMBER, position);
                } else {
                    fragment = new MapFragment();
                    args.putInt(MapFragment.ARG_TAB_NUMBER, position);
                }
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
        ClientModelRoot.cards.addObserver(this);
        ClientModelRoot.chat.addObserver(this);
        ClientModelRoot.history.addObserver(this);
        ClientModelRoot.board.addObserver(this);
        ClientModelRoot.points.addObserver(this);
        ClientModelRoot.carts.addObserver(this);
        ClientModelRoot.cards.faceUp.addObserver(this);
        ClientModelRoot.cards.others.addObserver(this);
        ClientModelRoot.games.addObserver(this);
        Login.getInstance().addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ClientModelRoot.cards.deleteObserver(this);
        ClientModelRoot.chat.deleteObserver(this);
        ClientModelRoot.history.deleteObserver(this);
        ClientModelRoot.board.deleteObserver(this);
        ClientModelRoot.points.deleteObserver(this);
        ClientModelRoot.carts.deleteObserver(this);
        ClientModelRoot.cards.faceUp.deleteObserver(this);
        ClientModelRoot.cards.others.deleteObserver(this);
        ClientModelRoot.games.deleteObserver(this);
        Login.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg) {
        try {
            if (ClientModelRoot.games.hasActive() && !ClientModelRoot.games.getActive().getState().equals(GameState.PLAYING) || Login.getInstance().getToken() == null) {
                Poller.getInstance(this.getApplicationContext()).reset();
                startActivity(new Intent(this, ActivityDecider.next()));
                this.finish();
            } else if (fragment instanceof IUpdatableFragment) {
                IUpdatableFragment updateFragment = (IUpdatableFragment) fragment;
                this.runOnUiThread(() -> {
                    if (updateFragment instanceof MapFragment) {
                        if (observable instanceof Board) updateFragment.update();
                        if (observable instanceof GameModel) updateFragment.update();
                    } else if (updateFragment instanceof ChatFragment) {
                        if (observable instanceof CurrentChat) updateFragment.update();
                    } else if (updateFragment instanceof GameInfoFragment) {
                        if (observable instanceof OtherPlayerCards) updateFragment.update();
                        else if (observable instanceof FaceUpCards) updateFragment.update();
                        else if (observable instanceof GameModel) updateFragment.update();
                        else if (observable instanceof CurrentCards) updateFragment.update();
                    } else if (updateFragment instanceof HistoryFragment) {
                        if (observable instanceof CommandHistory) updateFragment.update();
                    } else if (updateFragment instanceof OtherPlayersFragment) {
                        if (observable instanceof PlayerPoints) updateFragment.update();
                        else if (observable instanceof OtherPlayerCards) updateFragment.update();
                        else if (observable instanceof PlayerCarts) updateFragment.update();
                    } else if (updateFragment instanceof PlayerFragment) {
                        if (observable instanceof CurrentCards) updateFragment.update();
                        else if (observable instanceof PlayerPoints) updateFragment.update();
                        else if (observable instanceof PlayerCarts) updateFragment.update();
                        else if (observable instanceof PlayerPoints) updateFragment.update();
                    } else if (updateFragment instanceof DemoFragment) {
                    }
                });
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
