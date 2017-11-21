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

public class PlayerFragment extends Fragment implements IUpdatableFragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public PlayerFragment() {
        // Empty constructor required for fragment subclasses
    }
    TextView yellowCardText;
    TextView blackCardText;
    TextView blueCardText;
    TextView greenCardText;
    TextView redCardText;
    TextView purpleCardText;
    TextView orangeCardText;
    TextView locomotiveCardText;
    TextView whiteCardText;
    TextView scoreText;
    TextView trainCarsRemainingText;
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        yellowCardText = (TextView) rootView.findViewById(R.id.yellowTrainCardsLeft);
        blackCardText = (TextView) rootView.findViewById(R.id.blackTrainCardsLeft);
        blueCardText = (TextView) rootView.findViewById(R.id.blueTrainCardsLeft);
        greenCardText = (TextView) rootView.findViewById(R.id.greenTrainCardsLeft);
        redCardText = (TextView) rootView.findViewById(R.id.redTrainCardsLeft);
        purpleCardText = (TextView) rootView.findViewById(R.id.purpleTrainCardsLeft);
        orangeCardText = (TextView) rootView.findViewById(R.id.orangeTrainCardsLeft);
        locomotiveCardText = (TextView) rootView.findViewById(R.id.locomotiveCardsLeft);
        whiteCardText = (TextView) rootView.findViewById(R.id.whiteTrainCardsLeft);
        scoreText = (TextView) rootView.findViewById(R.id.currentScore);
        trainCarsRemainingText = (TextView) rootView.findViewById(R.id.trainCarsRemaining);
        listview = (ListView) rootView.findViewById(R.id.destination_card_list);

        getActivity().setTitle(title);

        update();

        return rootView;
    }

    public void update() {
        yellowCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.YELLOW)).count()));
        yellowCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        blackCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.BLACK)).count()));
        blackCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        blueCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.BLUE)).count()));
        blueCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        greenCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.GREEN)).count()));
        greenCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        redCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.RED)).count()));
        redCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        purpleCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.PURPLE)).count()));
        purpleCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        orangeCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.ORANGE)).count()));
        orangeCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        locomotiveCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.RAINBOW)).count()));
        locomotiveCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        whiteCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.WHITE)).count()));
        whiteCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        scoreText.setText(Integer.toString(ClientModelRoot.getInstance().points.getTotalPlayerPoints(Login.getUserId())));
        trainCarsRemainingText.setText(Integer.toString(ClientModelRoot.getInstance().carts.getPlayerCarts(Login.getUserId())));
        List<String> destinationCards = ClientModelRoot.getInstance().cards.getDestinationCards().stream().map((DestinationCard card) -> card.toString()).collect(Collectors.toList());
        String[] destinationCardList = new String[destinationCards.size()];
        destinationCards.toArray(destinationCardList);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                destinationCardList);

        listview.setAdapter(listViewAdapter);

    }
}
