package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.R;

/**
 * Created by ajols on 11/11/2017.
 */

public class GameFinishFragment extends Fragment {

    private TextView winner;

    private LinearLayout player3Info;
    private LinearLayout player4Info;
    private LinearLayout player5Info;

    private TextView player1Name;
    private TextView player2Name;
    private TextView player3Name;
    private TextView player4Name;
    private TextView player5Name;

    private TextView scorePlayer1;
    private TextView scorePlayer2;
    private TextView scorePlayer3;
    private TextView scorePlayer4;
    private TextView scorePlayer5;

    private TextView cartsPlayer1;
    private TextView cartsPlayer2;
    private TextView cartsPlayer3;
    private TextView cartsPlayer4;
    private TextView cartsPlayer5;

    private TextView longestRoutePlayer1;
    private TextView longestRoutePlayer2;
    private TextView longestRoutePlayer3;
    private TextView longestRoutePlayer4;
    private TextView longestRoutePlayer5;

    private TextView pointsFromClaimedRoutesPlayer1;
    private TextView pointsFromClaimedRoutesPlayer2;
    private TextView pointsFromClaimedRoutesPlayer3;
    private TextView pointsFromClaimedRoutesPlayer4;
    private TextView pointsFromClaimedRoutesPlayer5;

    private TextView pointsFromUnclaimedRoutesPlayer1;
    private TextView pointsFromUnclaimedRoutesPlayer2;
    private TextView pointsFromUnclaimedRoutesPlayer3;
    private TextView pointsFromUnclaimedRoutesPlayer4;
    private TextView pointsFromUnclaimedRoutesPlayer5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_finish, container, false);
        String title = "Game Summary Activity";

        winner = v.findViewById(R.id.winner);

        player3Info = v.findViewById(R.id.player3Info);
        player4Info = v.findViewById(R.id.player4Info);
        player5Info = v.findViewById(R.id.player5Info);

        player1Name = v.findViewById(R.id.player1Name);
        player2Name = v.findViewById(R.id.player2Name);
        player3Name = v.findViewById(R.id.player3Name);
        player4Name = v.findViewById(R.id.player4Name);
        player5Name = v.findViewById(R.id.player5Name);

        scorePlayer1 = v.findViewById(R.id.scorePlayer1);
        scorePlayer2 = v.findViewById(R.id.scorePlayer2);
        scorePlayer3 = v.findViewById(R.id.scorePlayer3);
        scorePlayer4 = v.findViewById(R.id.scorePlayer4);
        scorePlayer5 = v.findViewById(R.id.scorePlayer5);

        cartsPlayer1 = v.findViewById(R.id.cartsPlayer1);
        cartsPlayer2 = v.findViewById(R.id.cartsPlayer2);
        cartsPlayer3 = v.findViewById(R.id.cartsPlayer3);
        cartsPlayer4 = v.findViewById(R.id.cartsPlayer4);
        cartsPlayer5 = v.findViewById(R.id.cartsPlayer5);

        longestRoutePlayer1 = v.findViewById(R.id.longestRoutePlayer1);
        longestRoutePlayer2 = v.findViewById(R.id.longestRoutePlayer2);
        longestRoutePlayer3 = v.findViewById(R.id.longestRoutePlayer3);
        longestRoutePlayer4 = v.findViewById(R.id.longestRoutePlayer4);
        longestRoutePlayer5 = v.findViewById(R.id.longestRoutePlayer5);

        pointsFromClaimedRoutesPlayer1 = v.findViewById(R.id.pointsFromClaimedRoutesPlayer1);
        pointsFromClaimedRoutesPlayer2 = v.findViewById(R.id.pointsFromClaimedRoutesPlayer2);
        pointsFromClaimedRoutesPlayer3 = v.findViewById(R.id.pointsFromClaimedRoutesPlayer3);
        pointsFromClaimedRoutesPlayer4 = v.findViewById(R.id.pointsFromClaimedRoutesPlayer4);
        pointsFromClaimedRoutesPlayer5 = v.findViewById(R.id.pointsFromClaimedRoutesPlayer5);

        pointsFromUnclaimedRoutesPlayer1 = v.findViewById(R.id.pointsFromUnclaimedRoutesPlayer1);
        pointsFromUnclaimedRoutesPlayer2 = v.findViewById(R.id.pointsFromUnclaimedRoutesPlayer2);
        pointsFromUnclaimedRoutesPlayer3 = v.findViewById(R.id.pointsFromUnclaimedRoutesPlayer3);
        pointsFromUnclaimedRoutesPlayer4 = v.findViewById(R.id.pointsFromUnclaimedRoutesPlayer4);
        pointsFromUnclaimedRoutesPlayer5 = v.findViewById(R.id.pointsFromUnclaimedRoutesPlayer5);

        try {
            HashMap<UUID, String> playerNames = ClientModelRoot.getInstance().games.getActive().getPlayerNames();
            int totalPlayers = playerNames.size();
            Iterator<UUID> playerIterator = ClientModelRoot.getInstance().games.getActive().getPlayers().iterator();
            int count = 0;
            int highestScore = 0;
            String winningPlayer = "";

            if (totalPlayers != 5) {
                switch (totalPlayers) {
                    case 4 :
                        player5Info.setVisibility(View.GONE);
                        break;
                    case 3 :
                        player4Info.setVisibility(View.GONE);
                        player5Info.setVisibility(View.GONE);
                        break;
                    case 2 :
                        player3Info.setVisibility(View.GONE);
                        player4Info.setVisibility(View.GONE);
                        player5Info.setVisibility(View.GONE);
                        break;
                }
            }

            while (playerIterator.hasNext()) {
                UUID player = playerIterator.next();
                String playerName = playerNames.get(player);
                int score = ClientModelRoot.getInstance().points.getTotalPlayerPoints(player);

                if (score > highestScore) {
                    highestScore = score;
                    winningPlayer = playerName;
                }

                int carts = ClientModelRoot.getInstance().carts.getPlayerCarts(player);
                String longestRoute = "Loading...";
                Optional<UUID> tempPlayer = ClientModelRoot.getInstance().points.getPlayerWithLongestPath();

                if (tempPlayer.isPresent()) {
                    if (player.equals(tempPlayer.get())) {
                        longestRoute = "Yes";
                    } else {
                        longestRoute = "No";
                    }
                }

                int pointsFromClaimedRoutes = ClientModelRoot.getInstance().points.getPlayerMetDestinationCardPoints(player);
                int pointsFromUnclaimedRoutes = ClientModelRoot.getInstance().points.getPlayerUnmetDestinationCardPoints(player);

                switch (count) {
                    case 0 :
                        player1Name.setText(playerName);
                        scorePlayer1.setText(String.valueOf(score));
                        cartsPlayer1.setText(String.valueOf(carts));
                        longestRoutePlayer1.setText(longestRoute);
                        pointsFromClaimedRoutesPlayer1.setText(String.valueOf(pointsFromClaimedRoutes));
                        pointsFromUnclaimedRoutesPlayer1.setText(String.valueOf(pointsFromUnclaimedRoutes));
                        break;
                    case 1 :
                        player2Name.setText(playerName);
                        scorePlayer2.setText(String.valueOf(score));
                        cartsPlayer2.setText(String.valueOf(carts));
                        longestRoutePlayer2.setText(longestRoute);
                        pointsFromClaimedRoutesPlayer2.setText(String.valueOf(pointsFromClaimedRoutes));
                        pointsFromUnclaimedRoutesPlayer2.setText(String.valueOf(pointsFromUnclaimedRoutes));
                        break;
                    case 2 :
                        player3Name.setText(playerName);
                        scorePlayer3.setText(String.valueOf(score));
                        cartsPlayer3.setText(String.valueOf(carts));
                        longestRoutePlayer3.setText(longestRoute);
                        pointsFromClaimedRoutesPlayer3.setText(String.valueOf(pointsFromClaimedRoutes));
                        pointsFromUnclaimedRoutesPlayer3.setText(String.valueOf(pointsFromUnclaimedRoutes));
                        break;
                    case 3 :
                        player4Name.setText(playerName);
                        scorePlayer4.setText(String.valueOf(score));
                        cartsPlayer4.setText(String.valueOf(carts));
                        longestRoutePlayer4.setText(longestRoute);
                        pointsFromClaimedRoutesPlayer4.setText(String.valueOf(pointsFromClaimedRoutes));
                        pointsFromUnclaimedRoutesPlayer4.setText(String.valueOf(pointsFromUnclaimedRoutes));
                        break;
                    case 4 :
                        player5Name.setText(playerName);
                        scorePlayer5.setText(String.valueOf(score));
                        cartsPlayer5.setText(String.valueOf(carts));
                        longestRoutePlayer5.setText(longestRoute);
                        pointsFromClaimedRoutesPlayer5.setText(String.valueOf(pointsFromClaimedRoutes));
                        pointsFromUnclaimedRoutesPlayer5.setText(String.valueOf(pointsFromUnclaimedRoutes));
                        break;
                }

                count++;
            }

            winner.setText(winningPlayer);

        } catch (ResourceNotFoundException r) {
            r.printStackTrace();
        }

        getActivity().setTitle(title);

        return v;
    }
}