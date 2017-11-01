package teamseth.cs340.tickettoride.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
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
            holder.message.setText(ClientModelRoot.getInstance().games.getActive().getPlayerNames().get(message.getUser()) + ": " + message.getMessage());
        } catch (ResourceNotFoundException e) {
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
        ArrayList<Message> newMessageList = (ArrayList<Message>) ClientModelRoot.chat.getMessages();
        messageList = newMessageList;
        if (oldList != messageList) activity.runOnUiThread(() -> notifyDataSetChanged());
    }

}
