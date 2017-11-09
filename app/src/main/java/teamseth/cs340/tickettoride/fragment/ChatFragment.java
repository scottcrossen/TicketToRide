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
import android.widget.Toast;

import java.util.ArrayList;

import teamseth.cs340.common.commands.server.SendMessageCommand;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.adapter.ChatAdapter;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by Seth on 10/14/2017.
 */

public class ChatFragment extends Fragment implements View.OnClickListener{

    private LinearLayoutManager mLinearLayoutManager;
    private ChatAdapter mAdapter;
    private RecyclerView mRecyclerView;
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
        makeChat = v.findViewById(R.id.make_chat);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.chat_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ChatAdapter(getContext(), getActivity());
        mRecyclerView.setAdapter(mAdapter);


        String planet = getResources().getStringArray(R.array.tabs_array)[i];

        getActivity().setTitle(planet);
        update();
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
        Message newMessage = new Message(Login.getUserId(), makeChat.getText().toString());
        try {
            new CommandTask(this.getContext()).execute(new SendMessageCommand(newMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        makeChat.setText("");
    }

    public void update() {
        mAdapter.update();
    }
}
