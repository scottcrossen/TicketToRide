package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.client.cards.OtherPlayerCards;
import teamseth.cs340.common.models.client.carts.PlayerCarts;
import teamseth.cs340.common.models.client.points.PlayerPoints;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class OtherPlayersFragment extends Fragment implements IUpdatableFragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    private ScrollView otherPlayersContainer;

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

    private LinearLayout otherPlayer1View;
    private LinearLayout otherPlayer2View;
    private LinearLayout otherPlayer3View;
    private LinearLayout otherPlayer4View;

    private TextView noOtherPlayers;

    private View rootView;

    public OtherPlayersFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_other_players_info, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        getActivity().setTitle(title);

        otherPlayersContainer = rootView.findViewById(R.id.otherPlayersContainer);

        otherPlayer1 = rootView.findViewById(R.id.otherPlayer1);
        otherPlayer1Cards = rootView.findViewById(R.id.otherPlayer1Cards);
        otherPlayer1TrainCars = rootView.findViewById(R.id.otherPlayer1TrainCars);
        otherPlayer1DestCards = rootView.findViewById(R.id.otherPlayer1DestCards);
        otherPlayer1Score = rootView.findViewById(R.id.otherPlayer1Score);
        otherPlayer1View = rootView.findViewById(R.id.other_player_1_view);
        otherPlayer1View.setVisibility(rootView.GONE);

        otherPlayer2 = rootView.findViewById(R.id.otherPlayer2);
        otherPlayer2Cards = rootView.findViewById(R.id.otherPlayer2Cards);
        otherPlayer2TrainCars = rootView.findViewById(R.id.otherPlayer2TrainCars);
        otherPlayer2DestCards = rootView.findViewById(R.id.otherPlayer2DestCards);
        otherPlayer2Score = rootView.findViewById(R.id.otherPlayer2Score);
        otherPlayer2View = rootView.findViewById(R.id.other_player_2_view);
        otherPlayer2View.setVisibility(rootView.GONE);

        otherPlayer3 = rootView.findViewById(R.id.otherPlayer3);
        otherPlayer3Cards = rootView.findViewById(R.id.otherPlayer3Cards);
        otherPlayer3TrainCars = rootView.findViewById(R.id.otherPlayer3TrainCars);
        otherPlayer3DestCards = rootView.findViewById(R.id.otherPlayer3DestCards);
        otherPlayer3Score = rootView.findViewById(R.id.otherPlayer3Score);
        otherPlayer3View = rootView.findViewById(R.id.other_player_3_view);
        otherPlayer3View.setVisibility(rootView.GONE);

        otherPlayer4 = rootView.findViewById(R.id.otherPlayer4);
        otherPlayer4Cards = rootView.findViewById(R.id.otherPlayer4Cards);
        otherPlayer4TrainCars = rootView.findViewById(R.id.otherPlayer4TrainCars);
        otherPlayer4DestCards = rootView.findViewById(R.id.otherPlayer4DestCards);
        otherPlayer4Score = rootView.findViewById(R.id.otherPlayer4Score);
        otherPlayer4View = rootView.findViewById(R.id.other_player_4_view);
        otherPlayer4View.setVisibility(rootView.GONE);

        noOtherPlayers = rootView.findViewById(R.id.noOtherPlayers);
        noOtherPlayers.setVisibility(View.INVISIBLE);

        update();

        return rootView;
    }

    public void update() {
        try {
            HashMap<UUID, String> otherPlayerNames = ClientModelRoot.getInstance().games.getActive().getPlayerNames();
            OtherPlayerCards otherPlayerCards = ClientModelRoot.getInstance().cards.others;
            PlayerPoints otherPlayerPoints = ClientModelRoot.getInstance().points;
            PlayerCarts otherPlayerCarts = ClientModelRoot.getInstance().carts;
            Set<UUID> players = ClientModelRoot.getInstance().games
                    .getActive().getPlayers().stream().filter((UUID id) -> !id.equals(Login.getInstance().getUserId())).collect(Collectors.toSet());
            Iterator<UUID> iterator = players.iterator();
            if (iterator.hasNext()) {
                UUID nextPlayer = iterator.next();
                otherPlayer1.setText(otherPlayerNames.get(nextPlayer));
                otherPlayer1Cards.setText(Integer.toString(otherPlayerCards.getPlayerResourceCards(nextPlayer)));
                otherPlayer1TrainCars.setText(Integer.toString(otherPlayerCarts.getPlayerCarts(nextPlayer)));
                otherPlayer1DestCards.setText(Integer.toString(otherPlayerCards.getPlayerDestintationCard(nextPlayer)));
                otherPlayer1Score.setText(Integer.toString(otherPlayerPoints.getTotalPlayerPoints(nextPlayer)));
                otherPlayer1View.setVisibility(rootView.VISIBLE);
            } else {
                otherPlayersContainer.setVisibility(View.GONE);
                noOtherPlayers.setVisibility(View.VISIBLE);
            }
            if (iterator.hasNext()) {
                UUID nextPlayer = iterator.next();
                otherPlayer2.setText(otherPlayerNames.get(nextPlayer));
                otherPlayer2Cards.setText(Integer.toString(otherPlayerCards.getPlayerResourceCards(nextPlayer)));
                otherPlayer2TrainCars.setText(Integer.toString(otherPlayerCarts.getPlayerCarts(nextPlayer)));
                otherPlayer2DestCards.setText(Integer.toString(otherPlayerCards.getPlayerDestintationCard(nextPlayer)));
                otherPlayer2Score.setText(Integer.toString(otherPlayerPoints.getTotalPlayerPoints(nextPlayer)));
                otherPlayer2View.setVisibility(rootView.VISIBLE);
            }
            if (iterator.hasNext()) {
                UUID nextPlayer = iterator.next();
                otherPlayer3.setText(otherPlayerNames.get(nextPlayer));
                otherPlayer3Cards.setText(Integer.toString(otherPlayerCards.getPlayerResourceCards(nextPlayer)));
                otherPlayer3TrainCars.setText(Integer.toString(otherPlayerCarts.getPlayerCarts(nextPlayer)));
                otherPlayer3DestCards.setText(Integer.toString(otherPlayerCards.getPlayerDestintationCard(nextPlayer)));
                otherPlayer3Score.setText(Integer.toString(otherPlayerPoints.getTotalPlayerPoints(nextPlayer)));
                otherPlayer3View.setVisibility(rootView.VISIBLE);
            }
            if (iterator.hasNext()) {
                UUID nextPlayer = iterator.next();
                otherPlayer4.setText(otherPlayerNames.get(nextPlayer));
                otherPlayer4Cards.setText(Integer.toString(otherPlayerCards.getPlayerResourceCards(nextPlayer)));
                otherPlayer4TrainCars.setText(Integer.toString(otherPlayerCarts.getPlayerCarts(nextPlayer)));
                otherPlayer4DestCards.setText(Integer.toString(otherPlayerCards.getPlayerDestintationCard(nextPlayer)));
                otherPlayer4Score.setText(Integer.toString(otherPlayerPoints.getTotalPlayerPoints(nextPlayer)));
                otherPlayer4View.setVisibility(rootView.VISIBLE);
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
