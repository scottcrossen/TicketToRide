package teamseth.cs340.tickettoride.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.common.models.client.ClientModelRoot;

public class GameLobbyFragment extends Fragment
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);
        return v;
    }
}


