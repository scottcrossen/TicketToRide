package teamseth.cs340.tickettoride.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.commands.server.JoinGameCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by mike on 10/4/17.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    public void update() {
        ArrayList<Game> oldList = gameList;
        ArrayList<Game> newGameList = (ArrayList<Game>) ClientModelRoot.games.getAll().stream().filter((game) -> game.getState().equals(GameState.PREGAME)).collect(Collectors.toList());
        gameList = newGameList;
        if (oldList != gameList) activity.runOnUiThread(() -> notifyDataSetChanged());
    }

    private ArrayList<Game> gameList = new ArrayList<>();
    Context context;
    Activity activity;

    public GameListAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView gameName;
        //private RelativeLayout gameNameLayout;


        public ViewHolder(View view) {
            super(view);
            gameName = (TextView) view.findViewById(R.id.game_name);
            //gameNameLayout = (RelativeLayout) view.findViewById(R.id.game_name_layout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String gameNameText = (String) gameName.getText();
            Game clickedGame = ClientModelRoot.games.getGame(gameNameText);
            UUID gameId = clickedGame.getId();
            Activity activity = (Activity)view.getContext();
            new CommandTask(context).execute(new JoinGameCommand(gameId));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game gameName = gameList.get(position);
        holder.gameName.setText(gameName.name());

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
