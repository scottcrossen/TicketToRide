package teamseth.cs340.tickettoride.communicator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import teamseth.cs340.common.util.Serializer;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClientCommunicator {

    private static String urlStem = "/command";

    public static <A> A post(Serializable object) throws IOException, ClassNotFoundException {
        URL url = new URL("http://" + Login.getInstance().getServerHost() + ":" + Login.getInstance().getServerPort() + ClientCommunicator.urlStem);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.addRequestProperty("Accept", "application/json");
        http.connect();

        OutputStream reqBody = http.getOutputStream();
        Serializer.write(reqBody, object);
        reqBody.close();

        InputStream respBody = http.getInputStream();

        return (A) Serializer.read(respBody);
    }

}