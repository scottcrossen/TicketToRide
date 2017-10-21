package teamseth.cs340.common.commands.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.IHistoricalCommand;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AddMessagesCommand implements IClientCommand, IHistoricalCommand {

    private ArrayList<Message> messages;
    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;

    public AddMessagesCommand(ArrayList<Message> messages, Set<UUID> allPlayers, UUID owner) {
        this.messages = messages;
        this.players = allPlayers;
        this.owner = owner;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addMessages(messages); return null;});
    }

    public UUID getId() {
        return id;
    }

    public Set<UUID> playersVisibleTo() {
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }

    public String getDescription() {
        return "sent a message";
    }
}
