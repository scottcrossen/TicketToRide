package teamseth.cs340.tickettoride.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.UUID;

import teamseth.cs340.common.commands.server.LeaveGameCommand;
import teamseth.cs340.common.commands.server.StartGameCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.activity.GameListActivity;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GameLobbyFragment extends Fragment implements View.OnClickListener
{

    Game activeGame;
    private Button startBtn;
    private Button quitBtn;
    private TextView player1Name;
    private TextView player1Status;
    private TextView player2Name;
    private TextView player2Status;
    private TextView player3Name;
    private TextView player3Status;
    private TextView player4Name;
    private TextView player4Status;
    private TextView player5Name;
    private TextView player5Status;

    public GameLobbyFragment() {
        try {
            activeGame = ClientModelRoot.getInstance().games.getActive();
        } catch (Exception e) {
            startActivity(new Intent(getContext(), GameListActivity.class));
        }
    }

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

        player1Name = v.findViewById(R.id.textView1);
        player1Status = v.findViewById(R.id.textView2);
        player2Name = v.findViewById(R.id.textView3);
        player2Status = v.findViewById(R.id.textView4);
        player3Name = v.findViewById(R.id.textView5);
        player3Status = v.findViewById(R.id.textView6);
        player4Name = v.findViewById(R.id.textView7);
        player4Status = v.findViewById(R.id.textView8);
        player5Name = v.findViewById(R.id.textView9);
        player5Status = v.findViewById(R.id.textView10);
        setFields();

        startBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        return v;
    }

    public void setFields() {
        if (activeGame != null) {
            HashMap<UUID, String> playerNames = activeGame.getPlayerNames();
            UUID[] players = activeGame.getPlayers().toArray(new UUID[activeGame.getPlayers().size()]);
            if (players.length > 0) {
                player1Name.setText(playerNames.get(players[0]));
                player1Status.setText("Ready");
            } else {
                player1Name.setText("Player 1");
                player1Status.setText("Waiting on Player 1");
            }
            if (players.length > 1) {
                player2Name.setText(playerNames.get(players[1]));
                player2Status.setText("Ready");
            } else {
                player2Name.setText("Player 2");
                player2Status.setText("Waiting on Player 2");
            }
            if (players.length > 2) {
                player3Name.setText(playerNames.get(players[2]));
                player3Status.setText("Ready");
            } else {
                player3Name.setText("Player 3");
                player3Status.setText("Waiting on Player 3");
            }
            if (players.length > 3) {
                player4Name.setText(playerNames.get(players[3]));
                player4Status.setText("Ready");
            } else {
                player4Name.setText("Player 4");
                player4Status.setText("Waiting on Player 4");
            }
            if (players.length > 4) {
                player5Name.setText(playerNames.get(players[4]));
                player5Status.setText("Ready");
            } else {
                player5Name.setText("Player 5");
                player5Status.setText("Waiting on Player 5");
            }
        }
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

    public void updateGame() {
        try {
            if (!ClientModelRoot.getInstance().games.getActive().equals(this.activeGame)) {
                this.activeGame = ClientModelRoot.getInstance().games.getActive();
                setFields();
            }
        } catch (ResourceNotFoundException e) {
        }
    }
}


