package teamseth.cs340.common.models.server.chat;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Message implements Serializable {
    String message;
    UUID user;

    public Message(UUID user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public UUID getUser() {
        return user;
    }
}
