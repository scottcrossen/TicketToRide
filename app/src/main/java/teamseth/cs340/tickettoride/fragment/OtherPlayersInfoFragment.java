package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import teamseth.cs340.tickettoride.R;

/**
 * Created by ajols on 10/28/2017.
 */

public class OtherPlayersInfoFragment extends Fragment implements View.OnClickListener {

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

    private TextView otherPlayer5;
    private TextView otherPlayer5Cards;
    private TextView otherPlayer5TrainCars;
    private TextView otherPlayer5DestCards;
    private TextView otherPlayer5Score;

    private Button testStuff;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //chooseDestCardsBtn.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other_players_info, container, false);

        otherPlayer1 = v.findViewById(R.id.otherPlayer1);
        otherPlayer1Cards = v.findViewById(R.id.otherPlayer1Cards);
        otherPlayer1TrainCars = v.findViewById(R.id.otherPlayer1TrainCars);
        otherPlayer1DestCards = v.findViewById(R.id.otherPlayer1DestCards);
        otherPlayer1Score = v.findViewById(R.id.otherPlayer1Score);

        otherPlayer2 = v.findViewById(R.id.otherPlayer2);
        otherPlayer2Cards = v.findViewById(R.id.otherPlayer2Cards);
        otherPlayer2TrainCars = v.findViewById(R.id.otherPlayer2TrainCars);
        otherPlayer2DestCards = v.findViewById(R.id.otherPlayer2DestCards);
        otherPlayer2Score = v.findViewById(R.id.otherPlayer2Score);

        otherPlayer3 = v.findViewById(R.id.otherPlayer3);
        otherPlayer3Cards = v.findViewById(R.id.otherPlayer3Cards);
        otherPlayer3TrainCars = v.findViewById(R.id.otherPlayer3TrainCars);
        otherPlayer3DestCards = v.findViewById(R.id.otherPlayer3DestCards);
        otherPlayer3Score = v.findViewById(R.id.otherPlayer3Score);

        otherPlayer4 = v.findViewById(R.id.otherPlayer4);
        otherPlayer4Cards = v.findViewById(R.id.otherPlayer4Cards);
        otherPlayer4TrainCars = v.findViewById(R.id.otherPlayer4TrainCars);
        otherPlayer4DestCards = v.findViewById(R.id.otherPlayer4DestCards);
        otherPlayer4Score = v.findViewById(R.id.otherPlayer4Score);

        otherPlayer5 = v.findViewById(R.id.otherPlayer5);
        otherPlayer5Cards = v.findViewById(R.id.otherPlayer5Cards);
        otherPlayer5TrainCars = v.findViewById(R.id.otherPlayer5TrainCars);
        otherPlayer5DestCards = v.findViewById(R.id.otherPlayer5DestCards);
        otherPlayer5Score = v.findViewById(R.id.otherPlayer5Score);

        testStuff = v.findViewById(R.id.testStuff);
        testStuff.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testStuff:
                onButtonClicked();
                break;
        }
    }

    public void onButtonClicked()
    {
        String hello = "Other Player 1";
        int youKnow = 15;

        otherPlayer1.setText(hello);
        otherPlayer1Cards.setText(String.valueOf(youKnow));
        otherPlayer1TrainCars.setText(String.valueOf(youKnow));
        otherPlayer1DestCards.setText(String.valueOf(youKnow));
        otherPlayer1Score.setText(String.valueOf(youKnow));
    }
}
