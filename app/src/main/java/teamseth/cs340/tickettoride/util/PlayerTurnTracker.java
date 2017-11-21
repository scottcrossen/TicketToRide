package teamseth.cs340.tickettoride.util;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.commands.server.ClaimRouteCommand;
import teamseth.cs340.common.commands.server.DrawDestinationCardCommand;
import teamseth.cs340.common.commands.server.DrawFaceUpCardCommand;
import teamseth.cs340.common.commands.server.DrawResourceCardCommand;
import teamseth.cs340.common.commands.server.NextTurnCommand;
import teamseth.cs340.common.commands.server.ReturnDestinationCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PlayerTurnTracker implements Observer {
    private static PlayerTurnTracker instance;
    public static PlayerTurnTracker getInstance() {
        if(instance == null) {
            instance = new PlayerTurnTracker();
        }
        return instance;
    }

    /**
     * The player decides to use his/her move on drawing a resource card.
     *
     * @param context   The activity context. Just pass in "getContext()" from an activity.
     *
     * @return boolean  whether or not the cards could be successfully drawn at this time.
     *                  Possible reasons for 'false' include player trying to do another type of
     *                  action or there isn't enough cards to draw.
     */
    public boolean drawFaceDownResourceCard(Context context) {
        try {
            if (isPlayerTurn() && ClientModelRoot.cards.getResourceCards().size() > 0) {
                return state.drawFaceDownResourceCard(context);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The player decides to use his/her move on drawing a resource card.
     *
     * @param context   The activity context. Just pass in "getContext()" from an activity.
     * @param color     The card in the face-up pile to draw.
     *
     * @return boolean  whether or not the cards could be successfully drawn at this time.
     *                  Possible reasons for 'false' include player trying to do another type of
     *                  action or there isn't enough cards to draw.
     */
    public boolean drawFaceUpResourceCard(Context context, ResourceColor color) {
        try {
            if (isPlayerTurn() &&
                    ClientModelRoot.cards.faceUp.getFaceUpCards().size() > 0 &&
                    ClientModelRoot.cards.faceUp.getFaceUpCards().stream().anyMatch((ResourceColor faceUp) -> faceUp.equals(color)) &&
                    cardsDrawn < 2
            ) {
                return state.drawFaceUpResourceCard(context, color);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The player decides to use his/her move on drawing destination cards.
     *
     * @param context   The activity context. Just pass in "getContext()" from an activity.
     *
     * @return boolean  whether or not the cards could be successfully drawn at this time.
     *                  Possible reasons for 'false' include player trying to do another type of
     *                  action or there isn't enough cards to draw.
     */
    public boolean drawDestinationCard(Context context) {
        try {
            if (isPlayerTurn() && ClientModelRoot.cards.others.getDestinationAmountUsed() != 30) {
                return state.drawDestinationCard(context);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The player needs to choose which cards to discard.
     *
     * IMPORTANT:       You really should be calling this function from inside the 'update' function
     *                  of a class implementing the 'Observer' Interface that is currently watching
     *                  'ClientModelRoot.cards'
     *
     * @return List     A list of destination cards that represent the cards the player just drew.
     */
    public List<DestinationCard> getDestinationCardsToDecideOn() {
        try {
            if (isPlayerTurn()) {
                return state.getDestinationCardsToDecideOn();
            } else {
                return new LinkedList<>();
            }
        } catch (Exception e) {
            return new LinkedList<>();
        }
    }

    /**
     * A player is given the option to return some of the cards he drew
     *
     * @param context   The activity context. Just pass in "getContext()" from an activity.
     * @param cards     The list of cards to return. Must be from new cards returned
     *                  the "drawDestinationCard" command.
     *
     * @return boolean  whether or not the cards could be successfully returned at this time.
     *                  Possible reasons for 'false' include player trying to do another type of
     *                  action or the client-side state hasn't "caught up" yet.
     */
    public boolean returnDrawnDestinationCards(Context context, List<DestinationCard> cards) {
        try {
            if (isPlayerTurn()) {
                return state.returnDestinationCards(context, cards);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The player decides to use his/her move on claiming a route
     *
     * @param context   The activity context. Just pass in "getContext()" from an activity.
     * @param route     The route to claim.
     * @oaram colors    The list of colors to claim the route with.
     *
     * @return boolean  whether or not the route could be successfully claimed at this time.
     *                  Possible reasons for 'false' include player trying to do another type of
     *                  action or the client-side state hasn't "caught up" yet.
     */
    public boolean claimRoute(Context context, Route route, List<ResourceColor> colors) {
        try {
            if (isPlayerTurn()) {
                return state.claimRoute(context, route, colors);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The player decides to not do his turn.
     *
     * @param context   The activity context. Just pass in "getContext()" from an activity.
     *
     * @return boolean  Almost always true.
     */
    public boolean passTurn(Context context) {
        try {
            if (isPlayerTurn()) {
                nextTurn(context);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            state.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Observable currentObserver = null;

    protected void registerObserver(Observable observable) {
        resetObserver();
        observable.addObserver(this);
    }

    protected void resetObserver() {
        if (currentObserver != null) {
            currentObserver.deleteObserver(this);
            currentObserver = null;
        }
    }

    public void clearObserver() {
        if (currentObserver != null) {
            currentObserver.deleteObserver(this);
        }
    }

    private TurnState state = new NotTurnState();

    private void setState(TurnState newState) {
        resetObserver();
        state = newState;
    }

    private void nextTurn(Context context) throws ResourceNotFoundException {
        (new CommandTask(context)).execute(new NextTurnCommand());
        setState(new NotTurnState());
    }

    public boolean isPlayerTurn() {
        boolean output;
        try {
            output = ClientModelRoot.getInstance().games.getActive().getWhosTurnItIs().map((UUID turnId) -> turnId.equals(Login.getUserId())).orElseGet(() -> false);
        } catch (Exception e) {
            output = false;
        }
        if (!(state instanceof NotTurnState) && !output) {
            this.setState(new NotTurnState());
        } else if ((state instanceof NotTurnState) && output) {
            this.setState(new DecideActionState());
        }
        return output;
    }

    private interface TurnState {
        boolean drawFaceDownResourceCard(Context context) throws Exception;
        boolean drawFaceUpResourceCard(Context context, ResourceColor color) throws Exception;
        boolean drawDestinationCard(Context context) throws Exception;
        boolean returnDestinationCards(Context context, List<DestinationCard> cards) throws Exception;
        List<DestinationCard> getDestinationCardsToDecideOn() throws Exception;
        boolean claimRoute(Context context, Route route, List<ResourceColor> colors) throws Exception;
        void update() throws Exception;
    }

    private class NotTurnState implements TurnState {
        public boolean drawFaceDownResourceCard(Context context) {
            return false;
        }
        public boolean drawFaceUpResourceCard(Context context, ResourceColor color) {
            return false;
        }
        public boolean drawDestinationCard(Context context) {
            return false;
        }
        public boolean returnDestinationCards(Context context, List<DestinationCard> cards) {
            return false;
        }
        public List<DestinationCard> getDestinationCardsToDecideOn() {
            return new LinkedList<>();
        }
        public boolean claimRoute(Context context, Route route, List<ResourceColor> colors) {
            return false;
        }
        public void update() {}
    }

    private class DecideActionState implements TurnState {
        public DecideActionState() {
            cardsDrawn = 0;
        }
        public boolean drawFaceDownResourceCard(Context context) throws Exception {
            setState(new DrawResourceState());
            return state.drawFaceDownResourceCard(context);
        }
        public boolean drawFaceUpResourceCard(Context context, ResourceColor color) throws Exception {
            setState(new DrawResourceState());
            return state.drawFaceUpResourceCard(context, color);
        }
        public boolean drawDestinationCard(Context context) throws Exception {
            setState(new DrawDestinationState());
            return state.drawDestinationCard(context);
        }
        public boolean returnDestinationCards(Context context, List<DestinationCard> cards) {
            return false;
        }
        public List<DestinationCard> getDestinationCardsToDecideOn() {
            return new LinkedList<>();
        }
        public boolean claimRoute(Context context, Route route, List<ResourceColor> colors) throws Exception {
            setState(new ClaimRouteState());
            return state.claimRoute(context, route, colors);
        }
        public void update() {}
    }

    private int cardsDrawn = 0;
    private class DrawResourceState implements TurnState {
        private Context destroyContext = null;
        private boolean awaitingDestruction = false;
        private int initialAmountOfCards = ClientModelRoot.cards.getResourceCards().size();
        private boolean actionAllowed() {
            return (!awaitingDestruction && (cardsDrawn == 0 ||  initialAmountOfCards != ClientModelRoot.cards.getResourceCards().size()));
        }
        public boolean drawFaceDownResourceCard(Context context) throws ResourceNotFoundException {
            if (actionAllowed()) {
                cardsDrawn++;
                if (cardsDrawn == 2) {
                    destroyContext = context;
                    awaitingDestruction = true;
                    registerObserver(ClientModelRoot.cards);
                }
                (new CommandTask(context)).execute(new DrawResourceCardCommand());
                return true;
            } else {
                return false;
            }
        }
        public boolean drawFaceUpResourceCard(Context context, ResourceColor color) throws Exception {
            if (actionAllowed() && (!color.equals(ResourceColor.RAINBOW) || cardsDrawn == 0)) {
                cardsDrawn++;
                if (cardsDrawn == 2 || color.equals(ResourceColor.RAINBOW)) {
                    destroyContext = context;
                    awaitingDestruction = true;
                    registerObserver(ClientModelRoot.cards);
                }
                (new CommandTask(context)).execute(new DrawFaceUpCardCommand(color));
                return true;
            } else {
                return false;
            }
        }
        public boolean drawDestinationCard(Context context) {
            return false;
        }
        public boolean returnDestinationCards(Context context, List<DestinationCard> cards) {
            return false;
        }
        public List<DestinationCard> getDestinationCardsToDecideOn() {
            return new LinkedList<>();
        }
        public boolean claimRoute(Context context, Route route, List<ResourceColor> colors) {
            return false;
        }
        public void update() throws Exception {
            if (awaitingDestruction && ClientModelRoot.cards.getResourceCards().size() - initialAmountOfCards == cardsDrawn) {
                nextTurn(destroyContext);
            }
        }
    }

    private class DrawDestinationState implements TurnState {
        private Context destroyContext = null;
        private List<DestinationCard> awaitingForCardsToBeRemoved = new LinkedList<>();
        List<DestinationCard> initialCards = null;
        public boolean drawFaceDownResourceCard(Context context) {
            return false;
        }
        public boolean drawFaceUpResourceCard(Context context, ResourceColor color) {
            return false;
        }
        public boolean drawDestinationCard(Context context) throws ResourceNotFoundException {
            if (destroyContext == null && initialCards == null) {
                LinkedList<DestinationCard> newInitialCards = new LinkedList<>();
                newInitialCards.addAll(ClientModelRoot.cards.getDestinationCards());
                initialCards = newInitialCards;
                int cardsLeft = 30 - ClientModelRoot.cards.getDestinationCards().size();
                for (int i = 1; i <= (cardsLeft > 3 ? 3 : cardsLeft); i++) {
                    (new CommandTask(context)).execute(new DrawDestinationCardCommand());
                }
                return true;
            } else {
                return false;
            }
        }
        public boolean returnDestinationCards(Context context, List<DestinationCard> cards) throws ResourceNotFoundException {
            if (destroyContext == null && initialCards != null) {
                int amountOfCardsDrawn = ClientModelRoot.cards.getDestinationCards().size() - initialCards.size();
                boolean cardsAreValid = cards.stream().allMatch((DestinationCard card) -> {
                    boolean playerOwnsCard = ClientModelRoot.cards.getDestinationCards().stream().anyMatch((DestinationCard ownedCard) -> ownedCard.compareCitiesAndValue(card));
                    boolean cardIsNew = initialCards.stream().noneMatch((DestinationCard ownedCard) -> ownedCard.compareCitiesAndValue(card));
                    return (cardIsNew && playerOwnsCard);
                });
                if (awaitingForCardsToBeRemoved.size() == 0 && amountOfCardsDrawn > 0 && cardsAreValid && amountOfCardsDrawn - cards.size() >= 1) {
                    for (DestinationCard card : cards) {
                        (new CommandTask(context)).execute(new ReturnDestinationCardCommand(card));
                    }
                    if (cards.size() == 0) {
                        nextTurn(context);
                    } else {
                        destroyContext = context;
                        awaitingForCardsToBeRemoved = cards;
                        registerObserver(ClientModelRoot.cards);
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        public List<DestinationCard> getDestinationCardsToDecideOn() {
            if (initialCards == null) return new LinkedList<>();
            return ClientModelRoot.getInstance().cards.getDestinationCards().stream().filter((DestinationCard card) -> {
                boolean cardInInitialSet = initialCards.stream().anyMatch((DestinationCard initialCard) -> card.compareCitiesAndValue(initialCard));
                return !cardInInitialSet;
            }).collect(Collectors.toList());
        }
        public boolean claimRoute(Context context, Route route, List<ResourceColor> colors) {
            return false;
        }
        public void update() throws ResourceNotFoundException {
            boolean cardsDontExist = awaitingForCardsToBeRemoved.stream().noneMatch((DestinationCard card) -> {
                boolean playerOwnsCard = ClientModelRoot.cards.getDestinationCards().stream().anyMatch((DestinationCard ownedCard) -> ownedCard.compareCitiesAndValue(card));
                return playerOwnsCard;
            });
            if (awaitingForCardsToBeRemoved.size() > 0 && cardsDontExist) {
                nextTurn(destroyContext);
            }
        }
    }

    private class ClaimRouteState implements TurnState {
        private Context destroyContext = null;
        private Route awaitingForRouteToBeClaimed = null;
        public boolean drawFaceDownResourceCard(Context context) {
            return false;
        }
        public boolean drawFaceUpResourceCard(Context context, ResourceColor color) {
            return false;
        }
        public boolean drawDestinationCard(Context context) {
            return false;
        }
        public boolean returnDestinationCards(Context context, List<DestinationCard> cards) {
            return false;
        }
        public List<DestinationCard> getDestinationCardsToDecideOn() {
            return new LinkedList<>();
        }
        public boolean claimRoute(Context context, Route route, List<ResourceColor> colors) throws ResourceNotFoundException {
            List<Route> matchingRoutes = ClientModelRoot.board.getMatchingRoutes(route.getCity1(), route.getCity2(), route.getColor());
            List<Route> neighborRoutes = ClientModelRoot.getInstance().board.getMatchingRoutes(route.getCity1(), route.getCity2());

            // Begin precondition check.
            boolean nonClaimedRouteExists = matchingRoutes.stream().anyMatch((Route currentRoute) -> !currentRoute.getClaimedPlayer().isPresent());
            boolean validColors = route.equals(route.getCity1(), route.getCity2(), colors);
            boolean playerHasEnoughCards = colors.stream().distinct().allMatch((ResourceColor uniqueColor) -> {
                long amntColorToBeUsed = colors.stream().filter((ResourceColor currentColor) -> currentColor.equals(uniqueColor)).count();
                long amntColorOwned = ClientModelRoot.cards.getResourceCards().stream().filter((ResourceColor currentColor) -> currentColor.equals(uniqueColor)).count();
                boolean playerHasEnoughColor = amntColorOwned >= amntColorToBeUsed;
                return playerHasEnoughColor;
            });
            boolean doubleRouteRestrictionObserved = ClientModelRoot.getInstance().games.getActive().getPlayers().size() >= 4 ||
                    neighborRoutes.size() == 1 ||
                    neighborRoutes.stream().noneMatch((Route boardRoute) -> boardRoute.getClaimedPlayer().isPresent());
            boolean playerHasEnoughCarts = ClientModelRoot.carts.getPlayerCarts(Login.getInstance().getUserId()) >= route.getLength();
            boolean playerDoesntHaveBothRoutes = neighborRoutes.stream().noneMatch((Route boardRoute) -> boardRoute.getClaimedPlayer().map((UUID claimedPlayer) -> claimedPlayer.equals(Login.getUserId())).orElseGet(() -> false));
            // End of precondition check

            if (awaitingForRouteToBeClaimed == null && nonClaimedRouteExists && validColors && playerHasEnoughCards && playerHasEnoughCarts && doubleRouteRestrictionObserved && playerDoesntHaveBothRoutes) {
                destroyContext = context;
                awaitingForRouteToBeClaimed = route;
                registerObserver(ClientModelRoot.board);
                (new CommandTask(context)).execute(new ClaimRouteCommand(route.getCity1(), route.getCity2(), colors));
                return true;
            } else {
                setState(new DecideActionState());
                return false;
            }
        }
        public void update() throws ResourceNotFoundException {
            if (awaitingForRouteToBeClaimed != null) {
                List<Route> routes = ClientModelRoot.board.getMatchingRoutes(awaitingForRouteToBeClaimed.getCity1(),awaitingForRouteToBeClaimed.getCity2(),awaitingForRouteToBeClaimed.getColor());
                boolean routeClaimed = routes.stream().anyMatch((Route route) -> route.getClaimedPlayer().map((UUID playerId) -> playerId.equals(Login.getInstance().getUserId())).orElseGet(() -> false) && route.getLength() == awaitingForRouteToBeClaimed.getLength());
                if (routeClaimed) {
                    nextTurn(destroyContext);
                }
            }
        }
    }
}
