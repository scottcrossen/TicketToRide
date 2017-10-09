package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import teamseth.cs340.common.commands.server.LeaveGameCommand;
import teamseth.cs340.common.commands.server.StartGameCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

public class GameLobbyFragment extends Fragment implements View.OnClickListener
{

    private Button startBtn;
    private Button quitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_lobby, container, false);
        startBtn = v.findViewById(R.id.start_button);
        quitBtn = v.findViewById(R.id.quit_button);

        startBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                onStartPressed();
                break;
            case R.id.quit_button:
                onQuitPressed();
                break;
        }
    }

    public void onStartPressed() {
        try {
            new CommandTask(this.getContext()).execute(new StartGameCommand(ClientModelRoot.getInstance().games.getActive().getId()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onQuitPressed() {
        try {
            new CommandTask(this.getContext()).execute(new LeaveGameCommand(ClientModelRoot.getInstance().games.getActive().getId()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}


