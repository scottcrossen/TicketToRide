package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import teamseth.cs340.common.commands.server.LoginCommand;
import teamseth.cs340.common.commands.server.RegisterCommand;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by Seth on 9/29/2017.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText userNameTextIn;
    private EditText passwordTextIn;
    private EditText serverHostTextIn;
    private EditText serverPortTextIn;

    private static String userName;
    private static String password;
    private static String serverHost;
    private static String serverPort;

    private Button signInBtn;
    private Button registerBtn;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        if (Login.getInstance().getServerHost() != null)
            args.putString(serverHost, Login.getInstance().getServerHost());
        if (Login.getInstance().getServerPort() != null)
            args.putString(serverPort, Login.getInstance().getServerPort());
        if (Login.getInstance().getUsername() != null)
            args.putString(userName, Login.getInstance().getUsername());
        if (Login.getInstance().getPassword() != null)
            args.putString(password, Login.getInstance().getPassword());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getString(String.valueOf(userName)) != null)
                Login.getInstance().setUsername(getArguments().getString(String.valueOf(userName)));
            if (getArguments().getString(String.valueOf(password)) != null)
                Login.getInstance().setPassword(getArguments().getString(String.valueOf(password)));
            if (getArguments().getString(String.valueOf(serverHost)) != null)
                Login.getInstance().setServerHost(getArguments().getString(String.valueOf(serverHost)));
            if (getArguments().getString(String.valueOf(serverPort)) != null)
                Login.getInstance().setServerPort(getArguments().getString(String.valueOf(serverPort)));
        }
    }

    private void enableButtons() {
        if (userNameTextIn.getText().toString().trim().length() != 0 &&
                passwordTextIn.getText().toString().trim().length() != 0 &&
                serverHostTextIn.getText().toString().trim().length() != 0 &&
                serverPortTextIn.getText().toString().trim().length() != 0) {
            signInBtn.setEnabled(true);
            registerBtn.setEnabled(true);
        } else {
            signInBtn.setEnabled(false);
            registerBtn.setEnabled(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        signInBtn = v.findViewById(R.id.login_button);
        registerBtn = v.findViewById(R.id.register_button);

        signInBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        userNameTextIn = v.findViewById(R.id.nameEditText);
        userNameTextIn.setText(Login.getInstance().getUsername());
        passwordTextIn = v.findViewById(R.id.passwordEditText);
        passwordTextIn.setText(Login.getInstance().getPassword());
        serverHostTextIn = v.findViewById(R.id.hostEditText);
        serverHostTextIn.setText(Login.getInstance().getServerHost());
        serverPortTextIn = v.findViewById(R.id.portEditText);
        serverPortTextIn.setText(Login.getInstance().getServerPort());


        userNameTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableButtons();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        passwordTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableButtons();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        serverHostTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableButtons();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        serverPortTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableButtons();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                onSignInPressed();
                break;
            case R.id.register_button:
                onRegisterPressed();
                break;
        }
    }

    public void onRegisterPressed() {
        String userN = userNameTextIn.getText().toString();
        String passW = passwordTextIn.getText().toString();
        String serverH = serverHostTextIn.getText().toString();
        String serverP = serverPortTextIn.getText().toString();
        if (serverH != null) Login.getInstance().setServerHost(serverH);
        if (serverP != null) Login.getInstance().setServerPort(serverP);
        new CommandTask(this.getContext()).execute(new RegisterCommand(new UserCreds(userN, passW)));
    }

    public void onSignInPressed() {
        String userN = userNameTextIn.getText().toString();
        String passW = passwordTextIn.getText().toString();
        String serverH = serverHostTextIn.getText().toString();
        String serverP = serverPortTextIn.getText().toString();
        if (serverH != null) Login.getInstance().setServerHost(serverH);
        if (serverP != null) Login.getInstance().setServerPort(serverP);
        new CommandTask(this.getContext()).execute(new LoginCommand(new UserCreds(userN, passW)));
    }
}
