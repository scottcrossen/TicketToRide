package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class GameInfoFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";
    private final int blackCard = R.drawable.black_train_card;
    private final int greenCard = R.drawable.green_train_card;
    private final int redCard = R.drawable.red_train_card;
    private final int yellowCard = R.drawable.yellow_train_card;
    private final int orangeCard = R.drawable.orange_train_card;
    private final int blueCard = R.drawable.blue_train_card;
    private final int purpleCard = R.drawable.purple_train_card;
    private final int whiteCard = R.drawable.white_train_card;
    private final int wildCard = R.drawable.locomotive;

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

        //TODO set up the cards to update according to what is shown
        //TODO set onclicklisteners for the deck drawing, dest card drawing, and individual card picking

        ImageView card1 = (ImageView) rootView.findViewById(R.id.card1);
        ImageView card2 = (ImageView) rootView.findViewById(R.id.card2);
        ImageView card3 = (ImageView) rootView.findViewById(R.id.card3);
        ImageView card4 = (ImageView) rootView.findViewById(R.id.card4);
        ImageView card5 = (ImageView) rootView.findViewById(R.id.card5);
        ImageView trainCardDeck = (ImageView) rootView.findViewById(R.id.card_back_train);
        ImageView destinationCardDeck = (ImageView) rootView.findViewById(R.id.destination_card_back);
        TextView trainCardsLeft = (TextView) rootView.findViewById(R.id.cards_in_deck);
        TextView destCardsLeft = (TextView) rootView.findViewById(R.id.destination_cards_in_deck);

        //TODO set these to random cards
        card1.setImageResource(chooseColor(0));
        card2.setImageResource(chooseColor(1));
        card3.setImageResource(chooseColor(2));
        card4.setImageResource(chooseColor(3));
        card5.setImageResource(chooseColor(4));

        //TODO set the text to the number of cards left in the respective decks
        trainCardsLeft.setText(Integer.toString(ClientModelRoot.getInstance().cards.getResourceCards().size()));
        destCardsLeft.setText(Integer.toString(ClientModelRoot.getInstance().cards.getDestinationCards().size()));

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
                Toast.makeText(getContext(), "Chose Card 1", Toast.LENGTH_SHORT).show();
                card1.setImageResource(chooseColor(0));
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
                card2.setImageResource(chooseColor(1));
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
                card3.setImageResource(chooseColor(2));
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
                card4.setImageResource(chooseColor(3));
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add card to players hand, and change for a new card from the top of the deck
                card5.setImageResource(chooseColor(4));
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

        //TODO do a check to ensure there are not 3 locomotives facing up at the same time

        return rootView;
    }

    private int chooseColor(int pos){
        switch (ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(pos)) {
            case PURPLE:
                return purpleCard;
            case GREEN:
                return greenCard;
            case WHITE:
                return whiteCard;
            case RAINBOW:
                return wildCard;
            case YELLOW:
                return yellowCard;
            case RED:
                return redCard;
            case ORANGE:
                return orangeCard;
            case BLACK:
                return blackCard;
            case BLUE:
                return blueCard;
        }
        return orangeCard;
    }
}
