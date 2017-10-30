package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class OtherPlayersFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    private TextView otherPlayer1;
    private TextView otherPlayer1Cards;
    private TextView otherPlayer1TrainCars;
    private TextView otherPlayer1DestCards;
    private TextView otherPlayer1Score;

    private TextView otherPlayer2;
    private TextView otherPlayer2Cards;
    private TextView otherPlayer2TrainCars;
    private TextView otherPlayer2DestCards;
    private TextView otherPlayer2Score;

    private TextView otherPlayer3;
    private TextView otherPlayer3Cards;
    private TextView otherPlayer3TrainCars;
    private TextView otherPlayer3DestCards;
    private TextView otherPlayer3Score;

    private TextView otherPlayer4;
    private TextView otherPlayer4Cards;
    private TextView otherPlayer4TrainCars;
    private TextView otherPlayer4DestCards;
    private TextView otherPlayer4Score;

    public OtherPlayersFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Other Players", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_other_players_info, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        getActivity().setTitle(title);

        otherPlayer1 = rootView.findViewById(R.id.otherPlayer1);
        otherPlayer1Cards = rootView.findViewById(R.id.otherPlayer1Cards);
        otherPlayer1TrainCars = rootView.findViewById(R.id.otherPlayer1TrainCars);
        otherPlayer1DestCards = rootView.findViewById(R.id.otherPlayer1DestCards);
        otherPlayer1Score = rootView.findViewById(R.id.otherPlayer1Score);

        otherPlayer2 = rootView.findViewById(R.id.otherPlayer2);
        otherPlayer2Cards = rootView.findViewById(R.id.otherPlayer2Cards);
        otherPlayer2TrainCars = rootView.findViewById(R.id.otherPlayer2TrainCars);
        otherPlayer2DestCards = rootView.findViewById(R.id.otherPlayer2DestCards);
        otherPlayer2Score = rootView.findViewById(R.id.otherPlayer2Score);

        otherPlayer3 = rootView.findViewById(R.id.otherPlayer3);
        otherPlayer3Cards = rootView.findViewById(R.id.otherPlayer3Cards);
        otherPlayer3TrainCars = rootView.findViewById(R.id.otherPlayer3TrainCars);
        otherPlayer3DestCards = rootView.findViewById(R.id.otherPlayer3DestCards);
        otherPlayer3Score = rootView.findViewById(R.id.otherPlayer3Score);

        otherPlayer4 = rootView.findViewById(R.id.otherPlayer4);
        otherPlayer4Cards = rootView.findViewById(R.id.otherPlayer4Cards);
        otherPlayer4TrainCars = rootView.findViewById(R.id.otherPlayer4TrainCars);
        otherPlayer4DestCards = rootView.findViewById(R.id.otherPlayer4DestCards);
        otherPlayer4Score = rootView.findViewById(R.id.otherPlayer4Score);

        return rootView;
    }

    public void setPlayerInfo()
    {
        //TODO: Scott: set the number of cards, train cars, destination cards, and score for the other players in the game
    }
}
