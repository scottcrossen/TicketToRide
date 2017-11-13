package teamseth.cs340.common.models.client.chat;

import java.util.ArrayList;
import java.util.Observable;

import teamseth.cs340.common.models.server.chat.Message;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CurrentChat extends Observable {
    private static CurrentChat instance;

    public static CurrentChat getInstance() {
        if(instance == null) {
            instance = new CurrentChat();
        }
        return instance;
    }

    public void resetModel() {
        messages = new ArrayList<>();
    }

    private ArrayList<Message> messages = new ArrayList<>();

    public ArrayList<Message> getMessages() { return messages; }

    public void addMessages(ArrayList<Message> newMessages) {
        this.messages.addAll(newMessages);
        setChanged();
        notifyObservers();
    }
}
