package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class PlayerFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public PlayerFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Player", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        TextView yellowCardText = (TextView) rootView.findViewById(R.id.yellowTrainCardsLeft);
        //TODO set the text for all of the cards by grabbing from the model
        //yellowCardText.setText();
        yellowCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView blackCardText = (TextView) rootView.findViewById(R.id.blackTrainCardsLeft);
        blackCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView blueCardText = (TextView) rootView.findViewById(R.id.blueTrainCardsLeft);
        blueCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView greenCardText = (TextView) rootView.findViewById(R.id.greenTrainCardsLeft);
        greenCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView redCardText = (TextView) rootView.findViewById(R.id.redTrainCardsLeft);
        redCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView purpleCardText = (TextView) rootView.findViewById(R.id.purpleTrainCardsLeft);
        purpleCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView orangeCardText = (TextView) rootView.findViewById(R.id.orangeTrainCardsLeft);
        orangeCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView locomotiveCardText = (TextView) rootView.findViewById(R.id.locomotiveCardsLeft);
        locomotiveCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        TextView whiteCardText = (TextView) rootView.findViewById(R.id.whiteTrainCardsLeft);
        whiteCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView scoreText = (TextView) rootView.findViewById(R.id.currentScore);
        //TODO-Scott set score from model
        //scoreText.setText();
        //ClientModelRoot

        TextView trainCarsRemainingText = (TextView) rootView.findViewById(R.id.trainCarsRemaining);
        //TODO-Scott set score from model
        //trainCarsRemainingText.setText();

        getActivity().setTitle(title);

        ListView listview = (ListView) rootView.findViewById(R.id.destination_card_list);

        String[] destinationCardList = new String[] { "proof", "that", "this", "list",
        "works"};

        //TODO add the list of destination cards to this list so they will display

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                destinationCardList);

        listview.setAdapter(listViewAdapter);

        return rootView;
    }
}
