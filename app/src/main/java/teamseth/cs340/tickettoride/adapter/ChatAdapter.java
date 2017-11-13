package teamseth.cs340.tickettoride.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.tickettoride.R;

/**
 * Created by mike on 10/14/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    Activity activity;

    public ChatAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView playerName;
        private TextView message;


        public ViewHolder(View view) {
            super(view);
            playerName = (TextView) view.findViewById(R.id.player_name);
            message = (TextView) view.findViewById(R.id.chat_message);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
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
        Message message = messageList.get(position);
        try {
            holder.playerName.setText(ClientModelRoot.getInstance().games.getActive().getPlayerNames().get(message.getUser()));
            holder.message.setText(message.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private ArrayList<Message> messageList = new ArrayList<>();

    public void update() {
        ArrayList<Message> oldList = messageList;
        System.out.println(messageList.size());
        ArrayList<Message> newMessageList = (ArrayList<Message>) ClientModelRoot.chat.getMessages().clone();
        System.out.println(newMessageList.size());
        messageList = newMessageList;
        System.out.println(messageList.size());
        if (oldList.size() != messageList.size()) activity.runOnUiThread(() -> notifyDataSetChanged());
    }

}
