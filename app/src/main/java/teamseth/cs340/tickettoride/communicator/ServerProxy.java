package teamseth.cs340.tickettoride.communicator;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.commands.server.SendMessageCommand;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;

/**
 * Created by ajols on 11/17/2017.
 */

public class ServerProxy {

    private static ServerProxy instance;

    public static ServerProxy getInstance() {
        if(instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }

    // Claim a route
    public static void claimRoute(Context context, Route route, List<ResourceColor> colors) {
        try {
            PlayerTurnTracker.getInstance().claimRoute(context, route, colors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean canDoClaimRoute(Context context, Route route, List<ResourceColor> colors) {
        try {
            boolean clientInCorrectState = PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.Deciding);
            List<Route> matchingRoutes = ClientModelRoot.board.getMatchingRoutes(route.getCity1(), route.getCity2(), route.getColor());
            List<Route> neighborRoutes = ClientModelRoot.getInstance().board.getMatchingRoutes(route.getCity1(), route.getCity2());
            boolean nonClaimedRouteExists = matchingRoutes.stream().anyMatch((Route currentRoute) -> !currentRoute.getClaimedPlayer().getOption().isPresent());
            boolean validColors = route.equals(route.getCity1(), route.getCity2(), colors);
            boolean playerHasEnoughCards = colors.stream().distinct().allMatch((ResourceColor uniqueColor) -> {
                long amntColorToBeUsed = colors.stream().filter((ResourceColor currentColor) -> currentColor.equals(uniqueColor)).count();
                long amntColorOwned = ClientModelRoot.cards.getResourceCards().stream().filter((ResourceColor currentColor) -> currentColor.equals(uniqueColor)).count();
                boolean playerHasEnoughColor = amntColorOwned >= amntColorToBeUsed;
                return playerHasEnoughColor;
            });
            boolean doubleRouteRestrictionObserved = ClientModelRoot.getInstance().games.getActive().getPlayers().size() >= 4 ||
                    neighborRoutes.size() == 1 ||
                    neighborRoutes.stream().noneMatch((Route boardRoute) -> boardRoute.getClaimedPlayer().getOption().isPresent());
            boolean playerHasEnoughCarts = ClientModelRoot.carts.getPlayerCarts(Login.getInstance().getUserId()) >= route.getLength();
            boolean playerDoesntHaveBothRoutes = neighborRoutes.stream().noneMatch((Route boardRoute) -> boardRoute.getClaimedPlayer().getOption().map((UUID claimedPlayer) -> claimedPlayer.equals(Login.getUserId())).orElseGet(() -> false));
            return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && clientInCorrectState && nonClaimedRouteExists && validColors && playerHasEnoughCards && playerHasEnoughCarts && doubleRouteRestrictionObserved && playerDoesntHaveBothRoutes);
        } catch (Exception e) {
            return false;
        }
    }

    // Request destination cards
    public static void requestDestCard(Context context) {
        try {
            PlayerTurnTracker.getInstance().drawDestinationCard(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean canDoRequestDestCard(Context context) {
        try {
            boolean clientInCorrectState = PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.Deciding);
            boolean enoughCards = 30 - ClientModelRoot.cards.others.getDestinationAmountUsed() - ClientModelRoot.cards.getDestinationCards().size() > 0;
            return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && clientInCorrectState && enoughCards);
        } catch (Exception e) {
            return false;
        }
    }

    // Return destination card
    public static void returnDestCard(Context context, LinkedList<DestinationCard> cards) {
        try {
            PlayerTurnTracker.getInstance().returnDrawnDestinationCards(context, cards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean canDoReturnDestCard(Context context, List<DestinationCard> cards) {
        try {
            boolean clientInCorrectState = PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.ChooseDestination);
            boolean cardsAreValid = cards.stream().allMatch((DestinationCard card) -> {
                boolean playerOwnsCard = ClientModelRoot.cards.getDestinationCards().stream().anyMatch((DestinationCard ownedCard) -> ownedCard.compareCitiesAndValue(card));
                return playerOwnsCard;
            });
            return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && clientInCorrectState && cardsAreValid);
        } catch (Exception e) {
            return false;
        }
    }

    // Choose faceup train card
    public static void chooseFaceUpTrainCard(Context context, ResourceColor color) {
        try {
            PlayerTurnTracker.getInstance().drawFaceUpResourceCard(context, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean canDoChooseFaceUpTrainCard(Context context, ResourceColor color) {
        try {
            boolean clientInCorrectState = PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.Deciding) || PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.ChooseResource);
            boolean enoughCards = 110 - ClientModelRoot.cards.others.getResourceAmountUsed() - ClientModelRoot.cards.getResourceCards().size() > 5;
            boolean cardExists = ClientModelRoot.cards.faceUp.getFaceUpCards().stream().anyMatch((ResourceColor faceUp) -> faceUp.equals(color));
            return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && clientInCorrectState && enoughCards && cardExists);
        } catch (Exception e) {
            return false;
        }
    }

    // Choose train card from deck
    public static void chooseFaceUpDownCard(Context context) {
        PlayerTurnTracker.getInstance().drawFaceDownResourceCard(context);
    }
    public static boolean canDoChooseFaceDownTrainCard(Context context) {
        try {
            boolean clientInCorrectState = PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.Deciding) || PlayerTurnTracker.getInstance().getState().equals(PlayerTurnTracker.TurnStateEnum.ChooseResource);
            boolean enoughCards = 110 - ClientModelRoot.cards.others.getResourceAmountUsed() - ClientModelRoot.cards.getResourceCards().size() > 0;
            return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && clientInCorrectState && enoughCards);
        } catch (Exception e) {
            return false;
        }
    }

    // Chat
    public static void chat(Context context, String makeChat) {
        Message newMessage = new Message(Login.getUserId(), makeChat);
        try {
            new CommandTask(context).execute(new SendMessageCommand(newMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean canDoChat(Context context, String makeChat) {
        boolean chatNotEmpty = !makeChat.isEmpty();
        return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && chatNotEmpty);
    }
}

