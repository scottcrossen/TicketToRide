package teamseth.cs340.tickettoride.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.tickettoride.R;

/**
 * Created by mike on 10/4/17.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> implements Observer {

    @Override
    public void update(Observable observable, Object o) {
        List<String> gameNames = ClientFacade.getInstance().getGames().stream().filter((game) -> game.getState().equals(GameState.PREGAME)).map((game) -> game.name()).collect(Collectors.toList());
        gameList = (ArrayList<String>) gameNames;
    }

    private List<String> gameList = new ArrayList<String>();
    Context context;

    public GameListAdapter(Context context) {
        this.gameList = gameList;
        this.context = context;
        ClientModelRoot.getInstance().games.addObserver(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView gameName;

        public ViewHolder(View view) {
            super(view);
            gameName = (TextView) view.findViewById(R.id.game_name);
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
        String gameName = gameList.get(position);
        holder.gameName.setText(gameName);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
