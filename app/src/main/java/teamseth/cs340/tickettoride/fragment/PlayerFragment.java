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

import java.util.List;
import java.util.stream.Collectors;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.util.client.Login;
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
        yellowCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.YELLOW)).count()));
        yellowCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView blackCardText = (TextView) rootView.findViewById(R.id.blackTrainCardsLeft);
        blackCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.BLACK)).count()));
        blackCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView blueCardText = (TextView) rootView.findViewById(R.id.blueTrainCardsLeft);
        blueCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.BLUE)).count()));
        blueCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView greenCardText = (TextView) rootView.findViewById(R.id.greenTrainCardsLeft);
        greenCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.GREEN)).count()));
        greenCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView redCardText = (TextView) rootView.findViewById(R.id.redTrainCardsLeft);
        redCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.RED)).count()));
        redCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView purpleCardText = (TextView) rootView.findViewById(R.id.purpleTrainCardsLeft);
        purpleCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.PURPLE)).count()));
        purpleCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView orangeCardText = (TextView) rootView.findViewById(R.id.orangeTrainCardsLeft);
        orangeCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.ORANGE)).count()));
        orangeCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView locomotiveCardText = (TextView) rootView.findViewById(R.id.locomotiveCardsLeft);
        locomotiveCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.RAINBOW)).count()));
        locomotiveCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView whiteCardText = (TextView) rootView.findViewById(R.id.whiteTrainCardsLeft);
        whiteCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.WHITE)).count()));
        whiteCardText.setShadowLayer(15, 0, 0, Color.BLACK);

        TextView scoreText = (TextView) rootView.findViewById(R.id.currentScore);
        scoreText.setText(Integer.toString(ClientModelRoot.getInstance().points.getTotalPlayerPoints(Login.getUserId())));

        TextView trainCarsRemainingText = (TextView) rootView.findViewById(R.id.trainCarsRemaining);
        trainCarsRemainingText.setText(Integer.toString(ClientModelRoot.getInstance().carts.getPlayerCarts(Login.getUserId())));

        getActivity().setTitle(title);

        ListView listview = (ListView) rootView.findViewById(R.id.destination_card_list);
        List<String> destinationCards = ClientModelRoot.getInstance().cards.getDestinationCards().stream().map((DestinationCard card) -> card.toString()).collect(Collectors.toList());
        String[] destinationCardList = new String[destinationCards.size()];
        destinationCards.toArray(destinationCardList);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                destinationCardList);

        listview.setAdapter(listViewAdapter);

        return rootView;
    }
}
