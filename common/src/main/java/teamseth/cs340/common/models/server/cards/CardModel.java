package teamseth.cs340.common.models.server.cards;

import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CardModel extends AuthAction implements IModel<Deck> {
    private static CardModel instance;

    public static CardModel getInstance() {
        if(instance == null) {
            instance = new CardModel();
        }
        return instance;
    }

    private HashSet<DestinationDeck> destinationDecks = new HashSet<>();
    private HashSet<ResourceDeck> resourceDecks = new HashSet<>();

    public ResourceColor drawResourceCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        return getResourceDeck(deckId).draw();
    }

    public DestinationCard drawDestinationCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        return getDestinationDeck(deckId).draw();
    }

    public void returnResourceCard(UUID deckId, ResourceColor card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        getResourceDeck(deckId).returnCard(card);
    }

    public void returnDestinationCard(UUID deckId, DestinationCard card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        getDestinationDeck(deckId).returnCard(card);
    }

    public void upsert(DestinationDeck newDeck, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            getDestinationDeck(newDeck.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            destinationDecks.add(newDeck);
        }
    }

    public void upsert(ResourceDeck newDeck, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            getResourceDeck(newDeck.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            resourceDecks.add(newDeck);
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

    public ResourceColor drawFaceUpCard(UUID deckId, ResourceColor color, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        return getResourceDeck(deckId).drawFaceUpCard(color);
    }
}
