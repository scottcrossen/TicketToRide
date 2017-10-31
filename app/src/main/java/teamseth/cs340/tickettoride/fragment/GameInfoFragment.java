package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class GameInfoFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public GameInfoFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Game Info", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_game_info, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        getActivity().setTitle(title);

        final int blackCard = R.drawable.black_train_card;
        final int greenCard = R.drawable.green_train_card;
        final int redCard = R.drawable.red_train_card;
        final int yellowCard = R.drawable.yellow_train_card;
        final int orangeCard = R.drawable.orange_train_card;
        final int blueCard = R.drawable.blue_train_card;
        final int purpleCard = R.drawable.purple_train_card;
        final int whiteCard = R.drawable.white_train_card;
        final int wildCard = R.drawable.locomotive;

        //TODO set up the cards to update according to what is shown
        //TODO set onclicklisteners for the deck drawing, dest card drawing, and individual card picking

        ImageView card1 = (ImageView) rootView.findViewById(R.id.card1);
        ImageView card2 = (ImageView) rootView.findViewById(R.id.card2);
        ImageView card3 = (ImageView) rootView.findViewById(R.id.card3);
        ImageView card4 = (ImageView) rootView.findViewById(R.id.card4);
        ImageView card5 = (ImageView) rootView.findViewById(R.id.card5);
        ImageView trainCardDeck = (ImageView) rootView.findViewById(R.id.cardBack);
        ImageView destinationCardDeck = (ImageView) rootView.findViewById(R.id.destination_card_back);

        //TODO set these to random cards
        card1.setImageResource(blackCard);
        card2.setImageResource(redCard);
        card3.setImageResource(orangeCard);
        card4.setImageResource(wildCard);
        card5.setImageResource(greenCard);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
                Toast.makeText(getContext(), "Chose Card 1", Toast.LENGTH_SHORT).show();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
            }
        });

        destinationCardDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO give player option of 3 destination cards, player must keep 1-3 of these
                //TODO unused cards return to bottom of deck
            }
        });

        trainCardDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add top card to players hand, traindeck decrements
            }
        });

        return rootView;
    }
}
