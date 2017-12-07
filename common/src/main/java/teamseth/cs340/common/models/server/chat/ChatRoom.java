package teamseth.cs340.common.models.server.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import teamseth.cs340.common.persistence.IStorable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ChatRoom implements Serializable, IStorable {
    private UUID id = UUID.randomUUID();
    public UUID getId() {
        return id;
    }

    private ArrayList<Message> messages = new ArrayList<>();

    public void append(Message message) {
        messages.add(message);
    }
    public ArrayList<Message> getAll() {
        return messages;
    }
    public ArrayList<Message> getAfter(int size) { return (ArrayList<Message>) messages.subList(size, messages.size() - 1); }
}
