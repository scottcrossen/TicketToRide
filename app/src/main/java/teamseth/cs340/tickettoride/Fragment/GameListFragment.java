package teamseth.cs340.tickettoride.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import teamseth.cs340.tickettoride.Adapter.GameListAdapter;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.R;

/**
 * Created by mike on 10/4/17.
 */

public class GameListFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private GameListAdapter mAdapter;
    private ArrayList<String> mGameList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mGameList.size() == 0)
        {
            requestFilters();
        }
        setHasOptionsMenu(true);
    }

    private void requestFilters()
    {
        // TODO: 10/4/17 refactor 
//        for(String eventType : GameModel.SINGLETON.getAllEventTypes())
//        {
//            mGameList.add(eventType);
//        }
//        mGameList.add("Mother's Side");
//        mGameList.add("Father's Side");
//        mGameList.add("Male");
//        mGameList.add("Female");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.game_list_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new GameListAdapter(mGameList);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
}


