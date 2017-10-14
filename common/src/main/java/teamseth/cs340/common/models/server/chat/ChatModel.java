package teamseth.cs340.common.models.server.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ChatModel extends AuthAction implements IModel<ChatRoom> {
    private static ChatModel instance;

    public static ChatModel getInstance() {
        if(instance == null) {
            instance = new ChatModel();
        }
        return instance;
    }

    private HashSet<ChatRoom> rooms = new HashSet<ChatRoom>();

    public void upsert(ChatRoom newRoom, AuthToken token) throws UnauthorizedException {
        AuthAction.user(token);
        try {
            getRoom(newRoom.getId());
        } catch (ResourceNotFoundException e) {
            rooms.add(newRoom);
        }
    }

    private ChatRoom getRoom(UUID id) throws ResourceNotFoundException {
        for (ChatRoom room : rooms) {
            if(room.getId().equals(id)) {
                return room;
            }
        }
        throw new ResourceNotFoundException();
    }

    public void addMessage(UUID room, Message message, AuthToken token) throws UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        getRoom(room).append(message);
    }

    public ArrayList<Message> getMessages(UUID room) throws ResourceNotFoundException {
        return getRoom(room).getAll();
    }

    public ArrayList<Message> getMessagesAfter(UUID room, int size) throws ResourceNotFoundException {
        return getRoom(room).getAfter(size);
    }
}