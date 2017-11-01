package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import teamseth.cs340.common.commands.server.SendMessageCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.adapter.ChatAdapter;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by Seth on 10/14/2017.
 */

public class ChatFragment extends Fragment implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ChatAdapter mAdapter;
    private ArrayList<String> mChatList = new ArrayList<>();
    private Button sendBtn;
    private EditText makeChat;

    public static final String ARG_TAB_NUMBER = "tab_number";

    public ChatFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Chat", Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        sendBtn = v.findViewById(R.id.send_chat);
        sendBtn.setOnClickListener(this);
        makeChat = v.findViewById(R.id.chat_message);
        String planet = getResources().getStringArray(R.array.tabs_array)[i];


        getActivity().setTitle(planet);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_chat:
                onSendPressed();
                break;
        }
    }

    private void onSendPressed() {
        Message newMessage = (Message) makeChat.getText();
        try {
            new CommandTask(this.getContext()).execute(new SendMessageCommand(newMessage));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
    }
}
