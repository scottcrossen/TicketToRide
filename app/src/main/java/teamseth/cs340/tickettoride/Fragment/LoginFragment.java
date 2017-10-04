package teamseth.cs340.tickettoride.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import teamseth.cs340.tickettoride.Activity.GameListActivity;
import teamseth.cs340.tickettoride.R;

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
        /*if (login != null) {
            args.putString(userName, login.getUserName());
            args.putString(password, login.getPassword());
            args.putString(serverHost, login.getHost());
            args.putString(serverPort, login.getPort());
        }*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            login = new Login();
            login.setUserName(getArguments().getString(String.valueOf(userName)));
            login.setPassword(getArguments().getString(String.valueOf(password)));
            login.setHost(getArguments().getString(String.valueOf(serverHost)));
            login.setPort(getArguments().getString(String.valueOf(serverPort)));
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        signInBtn = (Button) v.findViewById(R.id.signInButton);
        registerBtn = (Button) v.findViewById(R.id.registerButton);


        signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onSignInPressed();
                    }
                }
        );

        registerBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRegisterPressed();
                    }
                }
        );

        final boolean[] textFields = {false, false, false, false, false, false, false};

        userNameTextIn = (EditText) v.findViewById(R.id.userNameEditText);
        passwordTextIn = (EditText) v.findViewById(R.id.passwordEditText);
        serverHostTextIn = (EditText) v.findViewById(R.id.hostEditText);
        serverPortTextIn = (EditText) v.findViewById(R.id.portEditText);

        userNameTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    textFields[0] = true;
                } else {
                    textFields[0] = false;
                }
                if (textFields[0] && textFields[1] && textFields[2] && textFields[3]) {
                    signInBtn.setEnabled(true);
                } else {
                    signInBtn.setEnabled(false);
                }

                if (textFields[0] && textFields[1] && textFields[2] && textFields[3] && textFields[4]
                        && textFields[5] && textFields[6]) {
                    registerBtn.setEnabled(true);
                } else {
                    registerBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    textFields[1] = true;
                } else {
                    textFields[1] = false;
                }
                if (textFields[0] && textFields[1] && textFields[2] && textFields[3]) {
                    signInBtn.setEnabled(true);
                } else {
                    signInBtn.setEnabled(false);
                }

                if (textFields[0] && textFields[1] && textFields[2] && textFields[3] && textFields[4]
                        && textFields[5] && textFields[6]) {
                    registerBtn.setEnabled(true);
                } else {
                    registerBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        serverHostTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    textFields[2] = true;
                } else {
                    textFields[2] = false;
                }
                if (textFields[0] && textFields[1] && textFields[2] && textFields[3]) {
                    signInBtn.setEnabled(true);
                } else {
                    signInBtn.setEnabled(false);
                }

                if (textFields[0] && textFields[1] && textFields[2] && textFields[3] && textFields[4]
                        && textFields[5] && textFields[6]) {
                    registerBtn.setEnabled(true);
                } else {
                    registerBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        serverPortTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    textFields[3] = true;
                } else {
                    textFields[3] = false;
                }
                if (textFields[0] && textFields[1] && textFields[2] && textFields[3]) {
                    signInBtn.setEnabled(true);
                } else {
                    signInBtn.setEnabled(false);
                }

                if (textFields[0] && textFields[1] && textFields[2] && textFields[3] && textFields[4]
                        && textFields[5] && textFields[6]) {
                    registerBtn.setEnabled(true);
                } else {
                    registerBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

    public void onRegisterPressed() {
        String userN = userNameTextIn.getText().toString();
        String passW = passwordTextIn.getText().toString();
        String serverH = serverHostTextIn.getText().toString();
        String serverP = serverPortTextIn.getText().toString();
        //new registerUserTask().execute(userN, passW, serverH, serverP, firstN, lastN, emaiL);
        startActivity(new Intent(getActivity(), GameListActivity.class));

    }

    public void onSignInPressed() {
        String userN = userNameTextIn.getText().toString();
        String passW = passwordTextIn.getText().toString();
        String serverH = serverHostTextIn.getText().toString();
        String serverP = serverPortTextIn.getText().toString();
        //new validateUserTask().execute(userN, passW, serverH, serverP);
        startActivity(new Intent(getActivity(), GameListActivity.class));

    }

    @Override
    public void onClick(View view) {

    }
/*
    private JSONObject sendRequestToUrl(String url, String requestParams, String authToken) throws IOException {
        try {
            URL urlObj = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.addRequestProperty("Accept", "application/json");
            conn.addRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Connection", "close");

            if (authToken != null) {
                conn.addRequestProperty("Authorization", authToken);
            }

            if (requestParams != null) {
                byte[] outputInBytes = requestParams.getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputInBytes);
                os.close();
            }

            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = conn.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                try {
                    JSONObject loginResponse = new JSONObject(responseBodyData);
                    try {
                        loginResponse.getString("message");
                    } catch (JSONException e) {
                        loginResponse.put("success", "True");
                        return loginResponse;
                    }
                    loginResponse.put("success", "False");
                    return loginResponse;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    private class registerUserTask extends AsyncTask<String, Void, JSONObject> {
        String host;
        String port;

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            this.host = params[2];
            this.port = params[3];
            String firstN = params[4];
            String lastN = params[5];
            String emaiL = params[6];
            String url = "http://" + host + ":" + port + "/user/register";
            String requestParams = "{userName: \"" + username + "\", password: \"" + password + "\"," +
                    "email: \"" + emaiL + "\", firstName: \"" + firstN + "\", lastName: \"" +
                    lastN + "\", gender: \"" + gender.toLowerCase() + "\"}";
            try {
                JSONObject registerResponse = sendRequestToUrl(url, requestParams, null);
                if (registerResponse == null) {
                    return null;
                } else {
                    return registerResponse;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject registerResponse) {
            if (registerResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error processing request.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    if (registerResponse.getString("success").equalsIgnoreCase("True")) {
                        String authTok = registerResponse.getString("authToken");
                        userId = registerResponse.getString("personID");
                        ModelApp.SINGLETON.setUserNameCurr(registerResponse.getString("personID"));
                        ModelApp.SINGLETON.setCurrId(userId);
                        new getUserPeopleTask().execute(this.host, this.port, authTok);
                    } else {
                        Toast toast = Toast.makeText(getContext(), registerResponse.getString("message"), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class validateUserTask extends AsyncTask<String, Void, JSONObject> {
        String host;
        String port;

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            this.host = params[2];
            this.port = params[3];
            String url = "http://" + host + ":" + port + "/user/login";
            String requestParams = "{userName: \"" + username + "\", password: \"" + password + "\"}";
            try {
                JSONObject loginResponse = sendRequestToUrl(url, requestParams, null);
                if (loginResponse == null) {
                    return null;
                } else {
                    return loginResponse;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject loginResponse) {
            if (loginResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error processing request.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    if (loginResponse.getString("success").equalsIgnoreCase("True")) {
                        String authTok = loginResponse.getString("authToken");
                        userId = loginResponse.getString("personID");
                        ModelApp.SINGLETON.setUserNameCurr(loginResponse.getString("personID"));
                        ModelApp.SINGLETON.setCurrId(userId);
                        new getUserPeopleTask().execute(this.host, this.port, authTok);
                    } else {
                        Toast toast = Toast.makeText(getContext(), loginResponse.getString("message"), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class getUserEventsTask extends AsyncTask<String, Void, JSONObject> {
        String host;
        String port;
        String authorization;

        @Override
        protected JSONObject doInBackground(String... params) {
            host = params[0];
            port = params[1];
            authorization = params[2];
            String url = "http://" + host + ":" + port + "/event/";

            try {
                JSONObject getEventsResponse = sendRequestToUrl(url, null, authorization);
                if (getEventsResponse == null) {
                    return null;
                } else {
                    return getEventsResponse;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject getEventsResponse) {
            if (getEventsResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error getting user info.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    JSONArray data = getEventsResponse.getJSONArray("data");
                    int dataCount = data.length();
                    for (int i = 0; i < dataCount; i++) {
                        JSONObject eventObj = data.getJSONObject(i);
                        Event event = new Event();
                        event.setEventID(eventObj.getString("eventID"));
                        event.setPerson(eventObj.getString("personID"));
                        event.setLatitude((float) eventObj.getDouble("latitude"));
                        event.setLongitude((float) eventObj.getDouble("longitude"));
                        event.setCountry(eventObj.getString("country"));
                        event.setCity(eventObj.getString("city"));
                        event.setEventType(eventObj.getString("eventType"));
                        event.setYear(Integer.parseInt(eventObj.getString("year")));
                        event.setDescendant(eventObj.getString("descendant"));
                        ModelApp.SINGLETON.addEvent(event);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ModelApp.SINGLETON.setLoggedIn(true);
                ModelApp.SINGLETON.setMotherFatherSides();
                MainActivity mainActivity = ((MainActivity) getActivity());
                if (mainActivity != null) {
                    Toast loginSuccess = Toast.makeText(getContext(), "Welcome " +
                            ModelApp.SINGLETON.getCurrentUser().getFirstName() +
                            " " + ModelApp.SINGLETON.getCurrentUser().getLastName() +
                            "!", Toast.LENGTH_LONG);
                    loginSuccess.show();
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.loginFragment, new MapFragment())
                            .commit();
                }
            }
        }
    }

    private class getUserPeopleTask extends AsyncTask<String, Void, JSONObject> {
        String host;
        String port;
        String authorization;

        @Override
        protected JSONObject doInBackground(String... params) {
            host = params[0];
            port = params[1];
            authorization = params[2];
            String url = "http://" + host + ":" + port + "/person/";

            try {
                JSONObject getEventsResponse = sendRequestToUrl(url, null, authorization);
                if (getEventsResponse == null) {
                    return null;
                } else {
                    return getEventsResponse;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject getPeopleResponse) {
            if (getPeopleResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error getting user info.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    JSONArray data = getPeopleResponse.getJSONArray("data");
                    int dataCount = data.length();
                    for (int i = 0; i < dataCount; i++) {
                        JSONObject personObj = data.getJSONObject(i);
                        Person person = new Person();
                        person.setPersonID(personObj.getString("personID"));
                        person.setFirstName(personObj.getString("firstName"));
                        person.setLastName(personObj.getString("lastName"));
                        person.setGender(String.valueOf(personObj.getString("gender").charAt(0)));
                        person.setDescendant(personObj.getString("descendant"));
                        try {
                            person.setSpouse(personObj.getString("spouse"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person.setFather(personObj.getString("father"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person.setMother(personObj.getString("mother"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ModelApp.SINGLETON.addPerson(person.getPersonID(), person);
                        String whatIwant = person.getPersonID();
                        String what2 = person.getPersonID();
                        String whatIveGot = ModelApp.SINGLETON.getUserNameCurr();
                        String userName2 = person.getFirstName();
                        if (whatIveGot.contentEquals(whatIwant)) {
                            ModelApp.SINGLETON.setCurrentUser(person);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ModelApp.SINGLETON.createFamilyTree();
                new getUserEventsTask().execute(this.host, this.port, this.authorization);
            }
        }
    }*/
}
