package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import teamseth.cs340.common.commands.server.CreateGameCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.adapter.GameListAdapter;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by mike on 10/4/17.
 */

public class GameListFragment extends Fragment implements View.OnClickListener
{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private GameListAdapter mAdapter;
    private ArrayList<String> mGameList = new ArrayList<>();
    private Button createBtn;

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
        for (Game game : ClientModelRoot.games.getAll())
        {
            mGameList.add(game.name());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);
        createBtn = v.findViewById(R.id.create_game);
        createBtn.setOnClickListener(this);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.game_list_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new GameListAdapter(getContext(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_game:
                onCreatePressed();
                break;
        }
    }

    public void onCreatePressed() {
        new CommandTask(this.getContext()).execute(new CreateGameCommand());
    }

    public void update() {
        mAdapter.update();
    }
}


