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

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.client.chat.CurrentChat;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by mike on 10/14/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public void update() {
        ArrayList<Message> oldList = chatList;
        ArrayList<Message> newGameList = (ArrayList<Message>) ClientModelRoot.chat.getMessages();
        if (oldList != chatList) activity.runOnUiThread(() -> notifyDataSetChanged());
    }

    private ArrayList<Message> chatList = new ArrayList<>();
    Context context;
    Activity activity;

    public ChatAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView message;
        //private RelativeLayout gameNameLayout;


        public ViewHolder(View view) {
            super(view);
            message = (TextView) view.findViewById(R.id.chat_message);
            //gameNameLayout = (RelativeLayout) view.findViewById(R.id.game_name_layout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String messageText = (String) message.getText();
//            Game clickedGame = ClientModelRoot.chat.getMessages();
//            UUID gameId = clickedGame.getId();
//            Activity activity = (Activity)view.getContext();
            //new CommandTask(context).execute(new JoinGameCommand(gameId));
        }
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        return new ChatAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        Message message = chatList.get(position);
        holder.message.setText(message.message);

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
