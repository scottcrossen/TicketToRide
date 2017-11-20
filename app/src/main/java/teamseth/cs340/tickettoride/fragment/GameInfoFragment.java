package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import teamseth.cs340.common.commands.server.NextTurnCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;

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
    private boolean isTurn = false;
    private int cardsDrawn = Login.getInstance().getCardsDrawn();
    TextView trainCardsLeft;
    TextView destCardsLeft;
    TextView playerName;

    public void update() {

        trainCardsLeft.setText(Integer.toString(
                110 - ClientModelRoot.cards.others.getResourceAmountUsed() - 5 - ClientModelRoot.cards.getResourceCards().size()
        ));
        destCardsLeft.setText(Integer.toString(
                30 - ClientModelRoot.cards.others.getDestinationAmountUsed() - ClientModelRoot.cards.getDestinationCards().size()
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
        Toast.makeText(this.getContext(), "Game Info", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_game_info, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];
        getActivity().setTitle(title);

        //TODO set up the cards to update according to what is shown
        //TODO set onclicklisteners for the deck drawing, dest card drawing, and individual card picking

        try {
            if (ClientModelRoot.getInstance().games.getActive().getWhosTurnItIs().get().equals(Login.getUserId())){
                isTurn = true;
                Login.getInstance().setCardsDrawn(0);
            } else
            {
                isTurn = false;
            }

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

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

        //TODO set these to random cards

        //TODO set the text to the number of cards left in the respective decks
        update();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(0).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(0))){
                    Toast.makeText(getContext(), "You drew a " + cardColor + " card. Nice.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
                }
//                int cardIndex = 0;
//                try {
//                    drawCard(cardIndex);
//                } catch (ResourceNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(1).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(1))){
                    Toast.makeText(getContext(), "You drew a " + cardColor + "card. Nice.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
                }
//                int cardIndex = 1;
//                try {
//                    drawCard(cardIndex);
//                } catch (ResourceNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(2).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(2))){
                    Toast.makeText(getContext(), "You drew a " + cardColor + "card. Nice.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
                }
//                int cardIndex = 2;
//                try {
//                    drawCard(cardIndex);
//                } catch (ResourceNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(3).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(3))){
                    Toast.makeText(getContext(), "You drew a " + cardColor + "card. Nice.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
                }
//                int cardIndex = 3;
//                try {
//                    drawCard(cardIndex);
//                } catch (ResourceNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardColor = ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(4).toString().toLowerCase();
                if (PlayerTurnTracker.getInstance().drawFaceUpResourceCard(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(4))){
                    Toast.makeText(getContext(), "You drew a " + cardColor + " card. Nice.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
                }
//                int cardIndex = 4;
//                try {
//                    drawCard(cardIndex);
//                } catch (ResourceNotFoundException e) {
//                    e.printStackTrace();
//                }
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
//                String cardColor = ClientModelRoot.getInstance()
                if (PlayerTurnTracker.getInstance().drawFaceDownResourceCard(getContext())){
                    Toast.makeText(getContext(), "You drew a card from the deck. You're cool.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO do a check to ensure there are not 3 locomotives facing up at the same time

        return rootView;
    }

//    private void drawCard(int cardIndex) throws ResourceNotFoundException {
//        if(ClientModelRoot.getInstance().games.getActive().getWhosTurnItIs().equals(Login.getUserId())){
//            try {
////                Toast.makeText(getContext(), ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(cardIndex).toString() + ResourceColor.RAINBOW.toString() , Toast.LENGTH_SHORT).show();
//                if (ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(cardIndex).toString() == ResourceColor.RAINBOW.toString()) {
//                    if (Login.getInstance().getCardsDrawn() == 0){
//                        isTurn = false;
//                        new CommandTask(getContext()).execute(new DrawFaceUpCardCommand(ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(cardIndex)));
//                        new CommandTask(this.getContext()).execute(new NextTurnCommand());
//                    } else {
//                        Toast.makeText(getContext(), "You can't drawn this card now! Do you even know how to play TTR?!", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Login.getInstance().setCardsDrawn(Login.getInstance().getCardsDrawn() + 1);
////                    Toast.makeText(getContext(), Login.getInstance().getCardsDrawn() , Toast.LENGTH_SHORT).show();
//                    new CommandTask(getContext()).execute(new DrawFaceUpCardCommand(ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(cardIndex)));
//                }
////                this.getView().invalidate();
//            } catch (ResourceNotFoundException e) {
//                e.printStackTrace();
//            }
//            checkChangeTurn();
//            setImage(1);
//            setImage(2);
//            setImage(3);
//            setImage(4);
//            setImage(5);
//        } else
//        {
//            Toast.makeText(getContext(), "It's not your turn! You can't cheat in electronic board games!", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void checkChangeTurn() {
        if (Login.getInstance().getCardsDrawn() > 1 || !isTurn){
            try {
                new CommandTask(this.getContext()).execute(new NextTurnCommand());
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
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

    private void refreshView(){
    }
}
