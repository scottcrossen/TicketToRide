package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;
import teamseth.cs340.tickettoride.util.Toaster;

/**
 * Created by Seth on 10/14/2017.
 */

public class GameInfoFragment extends Fragment implements IUpdatableFragment {
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
    ImageView card1;
    ImageView card2;
    ImageView card3;
    ImageView card4;
    ImageView card5;
    TextView trainCardsLeft;
    TextView destCardsLeft;
    TextView playerName;


    public void update() {
        int trainCardsLeftNum = 110 - ClientModelRoot.cards.others.getResourceAmountUsed() - 5 - ClientModelRoot.cards.getResourceCards().size();
        trainCardsLeft.setText(Integer.toString(
                trainCardsLeftNum < 0 ? 0 : trainCardsLeftNum
        ));
        int destCardsLeftNum = 30 - ClientModelRoot.cards.others.getDestinationAmountUsed() - ClientModelRoot.cards.getDestinationCards().size();
        destCardsLeft.setText(Integer.toString(
                destCardsLeftNum < 0 ? 0 : destCardsLeftNum
        ));
        try {
            UUID currentPlayerTurn = ClientModelRoot.games.getActive().getWhosTurnItIs().get();
            playerName.setText(ClientModelRoot.games.getActive().getPlayerNames().get(currentPlayerTurn));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setImage(1);
        setImage(2);
        setImage(3);
        setImage(4);
        setImage(5);
    }

    public GameInfoFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_info, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];
        getActivity().setTitle(title);

        card1 = (ImageView) rootView.findViewById(R.id.card1);
        card2 = (ImageView) rootView.findViewById(R.id.card2);
        card3 = (ImageView) rootView.findViewById(R.id.card3);
        card4 = (ImageView) rootView.findViewById(R.id.card4);
        card5 = (ImageView) rootView.findViewById(R.id.card5);
        ImageView trainCardDeck = (ImageView) rootView.findViewById(R.id.card_back_train);
        ImageView destinationCardDeck = (ImageView) rootView.findViewById(R.id.destination_card_back);
        trainCardsLeft = (TextView) rootView.findViewById(R.id.cards_in_deck);
        destCardsLeft = (TextView) rootView.findViewById(R.id.destination_cards_in_deck);
        playerName = (TextView) rootView.findViewById(R.id.player_name);

        update();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(0).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(0))){
                    if (Login.debug) Toaster.shortT(getContext(), "You drew a " + cardColor + " card.");
                } else if (!PlayerTurnTracker.getInstance().isPlayerTurn()) {
                    Toaster.shortT(getContext(), "It's not your turn.");
                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(1).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(1))){
                    if (Login.debug) Toaster.shortT(getContext(), "You drew a " + cardColor + " card.");
                } else if (!PlayerTurnTracker.getInstance().isPlayerTurn()) {
                    Toaster.shortT(getContext(), "It's not your turn.");
                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(2).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(2))){
                    if (Login.debug) Toaster.shortT(getContext(), "You drew a " + cardColor + " card.");
                } else if (!PlayerTurnTracker.getInstance().isPlayerTurn()) {
                    Toaster.shortT(getContext(), "It's not your turn.");
                }
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(3).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(3))){
                    if (Login.debug) Toaster.shortT(getContext(), "You drew a " + cardColor + " card.");
                } else if (!PlayerTurnTracker.getInstance().isPlayerTurn()) {
                    Toaster.shortT(getContext(), "It's not your turn.");
                }
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(4).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(4))){
                    if (Login.debug) Toaster.shortT(getContext(), "You drew a " + cardColor + " card.");
                } else if (!PlayerTurnTracker.getInstance().isPlayerTurn()) {
                    Toaster.shortT(getContext(), "It's not your turn.");
                }
            }
        });

        destinationCardDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerTurnTracker.getInstance().drawDestinationCard(getContext())){
                    NewDestCardsFragment prepDestCards = new NewDestCardsFragment();
                    Fragment getDestCards = prepDestCards;
                    FragmentManager fragmentManager = getFragmentManager();
                    getDestCards.setHasOptionsMenu(false);
                    getDestCards.setMenuVisibility(false);

                    fragmentManager.beginTransaction().replace(R.id.content_frame, getDestCards).commit();
                }
            }
        });

        trainCardDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerTurnTracker.getInstance().drawFaceDownResourceCard(getContext())){
                    if (Login.debug) Toaster.shortT(getContext(), "You drew a random card.");
                } else if (!PlayerTurnTracker.getInstance().isPlayerTurn()) {
                    Toaster.shortT(getContext(), "It's not your turn.");
                }
            }
        });

        return rootView;
    }

    private void setImage(int cardNum) {
        ImageView card = null;
        switch (cardNum) {
            case 1:
                card = card1;
                break;
            case 2:
                card = card2;
                break;
            case 3:
                card = card3;
                break;
            case 4:
                card = card4;
                break;
            case 5:
                card = card5;
                break;
        }
        if (card != null && cardNum <= ClientModelRoot.cards.faceUp.getFaceUpCards().size()) {
            card.setImageResource(chooseColor(cardNum - 1));
        } else if (card != null) {
            card.setVisibility(View.INVISIBLE);
        }
    }

    private int chooseColor(int pos){
        switch (ClientModelRoot.cards.faceUp.getFaceUpCards().get(pos)) {
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
