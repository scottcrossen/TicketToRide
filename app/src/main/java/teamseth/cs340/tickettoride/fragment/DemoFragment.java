package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import teamseth.cs340.common.commands.client.IncrementPlayerPointsCommand;
import teamseth.cs340.common.commands.server.ClaimRouteCommand;
import teamseth.cs340.common.commands.server.DecrementPlayerCartsCommand;
import teamseth.cs340.common.commands.server.DrawDestinationCardCommand;
import teamseth.cs340.common.commands.server.DrawFaceUpCardCommand;
import teamseth.cs340.common.commands.server.DrawResourceCardCommand;
import teamseth.cs340.common.commands.server.NextTurnCommand;
import teamseth.cs340.common.commands.server.ReturnDestinationCardCommand;
import teamseth.cs340.common.commands.server.ReturnResourceCardCommand;
import teamseth.cs340.common.commands.server.UpdateAllClientsCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DemoFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_TAB_NUMBER = "tab_number";

    private Button playerPts;
    private Button drawInvTrainCards;
    private Button drawVisTrainCards;
    private Button removeTrainCards;
    private Button addDestCards;
    private Button removeDestCards;
    private Button addTrainCarts;
    private Button removeTrainCarts;
    private Button claimRoute;
    private Button changeTurn;
    private Button removeAllCarts;
    
    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_demo, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);

        playerPts = rootView.findViewById(R.id.player_points);
        playerPts.setOnClickListener(this);
        drawInvTrainCards = rootView.findViewById(R.id.draw_inv_train_cards);
        drawInvTrainCards.setOnClickListener(this);
        drawVisTrainCards = rootView.findViewById(R.id.draw_vis_train_cards);
        drawVisTrainCards.setOnClickListener(this);
        removeTrainCards = rootView.findViewById(R.id.remove_train_cards);
        removeTrainCards.setOnClickListener(this);
        addDestCards = rootView.findViewById(R.id.add_dest_cards);
        addDestCards.setOnClickListener(this);
        removeDestCards = rootView.findViewById(R.id.remove_dest_cards);
        removeDestCards.setOnClickListener(this);
        addTrainCarts = rootView.findViewById(R.id.add_train_carts);
        addTrainCarts.setOnClickListener(this);
        removeTrainCarts = rootView.findViewById(R.id.remove_train_carts);
        removeTrainCarts.setOnClickListener(this);
        claimRoute = rootView.findViewById(R.id.claim_route);
        claimRoute.setOnClickListener(this);
        changeTurn = rootView.findViewById(R.id.change_turn);
        changeTurn.setOnClickListener(this);
        removeAllCarts = rootView.findViewById(R.id.reduce_carts_zero);
        removeAllCarts.setOnClickListener(this);
        
        
        return rootView;
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.player_points:
                    new CommandTask(this.getContext()).execute(new UpdateAllClientsCommand(new IncrementPlayerPointsCommand(1, ClientModelRoot.getInstance().games.getActive().getPlayers(), Login.getUserId())));
                    break;
                case R.id.draw_inv_train_cards:
                    new CommandTask(this.getContext()).execute(new DrawResourceCardCommand());
                    break;
                case R.id.draw_vis_train_cards:
                    new CommandTask(this.getContext()).execute(new DrawFaceUpCardCommand(ClientModelRoot.getInstance().cards.faceUp.getFaceUpCards().get(0)));
                    break;
                case R.id.remove_train_cards:
                    if (ClientModelRoot.getInstance().cards.getResourceCards().size() > 0) {
                        new CommandTask(this.getContext()).execute(new ReturnResourceCardCommand(ClientModelRoot.getInstance().cards.getResourceCards().get(0)));
                    }
                    break;
                case R.id.add_dest_cards:
                    new CommandTask(this.getContext()).execute(new DrawDestinationCardCommand());
                    break;
                case R.id.remove_dest_cards:
                    if (ClientModelRoot.getInstance().cards.getDestinationCards().size() > 0) {
                        new CommandTask(this.getContext()).execute(new ReturnDestinationCardCommand(ClientModelRoot.getInstance().cards.getDestinationCards().get(0)));
                    }
                    break;
                case R.id.add_train_carts:
                    //new CommandTask(this.getContext()).execute(new UpdateAllClientsCommand(new AddTrainCartsCommand(1, ClientModelRoot.getInstance().games.getActive().getPlayers(), Login.getUserId())));
                    Logger.warn("Deprecated method access attempted.");
                    break;
                case R.id.remove_train_carts:
                    new CommandTask(this.getContext()).execute(new DecrementPlayerCartsCommand(1));
                    break;
                case R.id.claim_route:
                    if (ClientModelRoot.getInstance().cards.getDestinationCards().size() > 0) {
                        ArrayList<ResourceColor> claimWithColors = new ArrayList<>();
                        claimWithColors.add(ClientModelRoot.getInstance().cards.getResourceCards().get(0));
                        new CommandTask(this.getContext()).execute(new ClaimRouteCommand(CityName.Seattle, CityName.Vancouver, claimWithColors));
                    }
                    break;
                case R.id.change_turn:
                    if (ClientModelRoot.getInstance().cards.getDestinationCards().size() > 0) {
                        new CommandTask(this.getContext()).execute(new NextTurnCommand());
                    }
                    break;
                case R.id.reduce_carts_zero:
                    int numCarts = ClientModelRoot.getInstance().carts.getPlayerCarts(Login.getUserId());
                    if (numCarts > 0) {
                        new CommandTask(this.getContext()).execute(new DecrementPlayerCartsCommand(numCarts));
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
