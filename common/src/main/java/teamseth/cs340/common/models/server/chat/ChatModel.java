package teamseth.cs340.common.models.server.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.IServerModel;
import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.IDeltaCommand;
import teamseth.cs340.common.persistence.PersistenceAccess;
import teamseth.cs340.common.persistence.PersistenceTask;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ChatModel extends AuthAction implements IServerModel<ChatRoom>, Serializable {
    private static ChatModel instance;

    public static ChatModel getInstance() {
        if(instance == null) {
            instance = new ChatModel();
        }
        return instance;
    }

    private HashSet<ChatRoom> rooms = new HashSet<ChatRoom>();

    public CompletableFuture<Boolean> loadAllFromPersistence() {
        CompletableFuture<List<ChatRoom>> persistentData = PersistenceAccess.getObjects(ModelObjectType.CHAT);
        return persistentData.thenApply((List<ChatRoom> newData) -> {
            rooms.addAll(newData);
            return true;
        });
    }

    public void upsert(ChatRoom newRoom, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            getRoom(newRoom.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            rooms.add(newRoom);
            PersistenceTask.save(newRoom, new IDeltaCommand<ChatRoom>() {
                @Override
                public ChatRoom call(ChatRoom oldState) {
                    return newRoom;
                }
            });
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

    public void addMessage(UUID roomId, Message message, AuthToken token) throws UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        ChatRoom room = getRoom(roomId);
        room.append(message);
        PersistenceTask.save(room, new IDeltaCommand<ChatRoom>() {
            @Override
            public ChatRoom call(ChatRoom oldState) {
                oldState.append(message);
                return oldState;
            }
        });
    }

    public ArrayList<Message> getMessages(UUID room) throws ResourceNotFoundException {
        return getRoom(room).getAll();
    }

    public ArrayList<Message> getMessagesAfter(UUID room, int size) throws ResourceNotFoundException {
        return getRoom(room).getAfter(size);
    }
}