package teamseth.cs340.common.models.server.cards;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
public class CardModel extends AuthAction implements IServerModel<Deck>, Serializable {
    private static CardModel instance;

    public static CardModel getInstance() {
        if(instance == null) {
            instance = new CardModel();
        }
        return instance;
    }

    private HashSet<DestinationDeck> destinationDecks = new HashSet<>();
    private HashSet<ResourceDeck> resourceDecks = new HashSet<>();

    public CompletableFuture<Boolean> loadAllFromPersistence() {
        CompletableFuture<List<DestinationDeck>> persistentDestinationData = PersistenceAccess.getObjects(ModelObjectType.DESTINATIONDECK);
        return persistentDestinationData.thenApply((List<DestinationDeck> newData) -> {
            destinationDecks.addAll(newData);
            return true;
        }).thenCompose((Boolean result1) -> {
            CompletableFuture<List<ResourceDeck>> persistentResourceData = PersistenceAccess.getObjects(ModelObjectType.RESOURCEDECK);
            return persistentResourceData.thenApply((List<ResourceDeck> newData) -> {
                resourceDecks.addAll(newData);
                Boolean result2 = true;
                return result1 && result2;
            });
        });
    }

    public ResourceColor drawResourceCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        ResourceDeck deck = getResourceDeck(deckId);
        ResourceColor output = deck.draw();
        PersistenceTask.save(deck, new IDeltaCommand<ResourceDeck>() {
            @Override
            public ResourceDeck call(ResourceDeck oldState) {
                oldState.removeCard(output);
                return oldState;
            }
        });
        return output;
    }

    public DestinationCard drawDestinationCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        DestinationDeck deck = getDestinationDeck(deckId);
        DestinationCard output = deck.draw();
        PersistenceTask.save(deck, new IDeltaCommand<DestinationDeck>() {
            @Override
            public DestinationDeck call(DestinationDeck oldState) {
                oldState.removeCard(output);
                return oldState;
            }
        });
        return output;
    }

    public void returnResourceCard(UUID deckId, ResourceColor card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        ResourceDeck deck = getResourceDeck(deckId);
        deck.returnCard(card);
        PersistenceTask.save(deck, new IDeltaCommand<ResourceDeck>() {
            @Override
            public ResourceDeck call(ResourceDeck oldState) {
                oldState.returnCard(card);
                return oldState;
            }
        });
    }

    public void returnDestinationCard(UUID deckId, DestinationCard card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        DestinationDeck deck = getDestinationDeck(deckId);
        deck.returnCard(card);
        PersistenceTask.save(deck, new IDeltaCommand<DestinationDeck>() {
            @Override
            public DestinationDeck call(DestinationDeck oldState) {
                oldState.returnCard(card);
                return oldState;
            }
        });
    }

    public void upsert(DestinationDeck newDeck, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            getDestinationDeck(newDeck.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            destinationDecks.add(newDeck);
            PersistenceTask.save(newDeck, new IDeltaCommand<DestinationDeck>() {
                @Override
                public DestinationDeck call(DestinationDeck oldState) {
                    return newDeck;
                }
            });
        }
    }

    public void upsert(ResourceDeck newDeck, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            getResourceDeck(newDeck.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            resourceDecks.add(newDeck);
            PersistenceTask.save(newDeck, new IDeltaCommand<ResourceDeck>() {
                @Override
                public ResourceDeck call(ResourceDeck oldState) {
                    return newDeck;
                }
            });
        }
    }

    private DestinationDeck getDestinationDeck(UUID id) throws ResourceNotFoundException {
        for (DestinationDeck deck : destinationDecks) {
            if(deck.getId().equals(id)) {
                return deck;
            }
        }
        throw new ResourceNotFoundException();
    }

    private ResourceDeck getResourceDeck(UUID id) throws ResourceNotFoundException {
        for (ResourceDeck deck : resourceDecks) {
            if(deck.getId().equals(id)) {
                return deck;
            }
        }
        throw new ResourceNotFoundException();
    }

    public Optional<ResourceColor> drawFaceUpCard(UUID deckId, ResourceColor color, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        return getResourceDeck(deckId).drawFaceUpCard(color);
    }

    public List<ResourceColor> checkAndResuffleFaceUpCards(UUID deckId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        return getResourceDeck(deckId).checkAndResuffleFaceUpCards();
    }
}
