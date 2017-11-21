package teamseth.cs340.tickettoride.communicator;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.client.AddTrainCartsCommand;
import teamseth.cs340.common.commands.server.ClaimRouteCommand;
import teamseth.cs340.common.commands.server.DrawDestinationCardCommand;
import teamseth.cs340.common.commands.server.DrawFaceUpCardCommand;
import teamseth.cs340.common.commands.server.ReturnDestinationCardCommand;
import teamseth.cs340.common.commands.server.SendMessageCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.CityName;
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

    public static void claimRoute(Context context, CityName city1, CityName city2, ArrayList<ResourceColor> colors) {
        try {
            new CommandTask(context).execute(new ClaimRouteCommand(city1, city2, colors));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean canDoClaimRoute(Context context, CityName city1, CityName city2, ResourceColor color) {
        boolean routeNotClaimed = ClientModelRoot.getInstance().board.getMatchingRoutes(city1, city2, color).stream().anyMatch((Route route) -> !route.getClaimedPlayer().isPresent());
        return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && routeNotClaimed);
    }


    // Request destination cards

    public static void requestDestCard(Context context) {
        try {
            new CommandTask(context).execute(new DrawDestinationCardCommand());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean canDoRequestDestCard(Context context) {
        boolean availableDestCards = ClientModelRoot.getInstance().cards.others.getDestinationAmountUsed() <= 30;
        return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && availableDestCards);
    }


    // Return destination card

    public static void returnDestCard(Context context, DestinationCard card) {
        try {
            new CommandTask(context).execute(new ReturnDestinationCardCommand(card));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean canDoReturnDestCard(Context context, List<DestinationCard> cards) {
        boolean test = PlayerTurnTracker.getInstance().returnDrawnDestinationCards(context, cards);
        return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && test);
    }


    // Choose face-up train card

    /*public boolean drawFaceUpResourceCard(Context context, ResourceColor color) throws Exception {
            if (actionAllowed() && (!color.equals(ResourceColor.RAINBOW) || cardsDrawn == 0)) {
                if (++cardsDrawn == 2 || color.equals(ResourceColor.RAINBOW)) {
                    destroyContext = context;
                    awaitingDestruction = true;
                    registerObserver(ClientModelRoot.cards);
                }
                (new CommandTask(context)).execute(new DrawFaceUpCardCommand(color));
                return true;
            } else {
                return false;
            }
        }*/

    public static void chooseFaceUpTrainCard(Context context, ResourceColor color) {
        try {
            new CommandTask(context).execute(new DrawFaceUpCardCommand(color));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean canDoChooseFaceUpTrainCard(Context context) {
        //PlayerTurnTracker.getInstance().dr
    }

    // Choose train card from deck

    public static void chooseTrainCardFromDeck(Context context, int points, Set<UUID> allPlayers, UUID owner) {
        new CommandTask(context).execute(new AddTrainCartsCommand(points, allPlayers, owner));
    }


    // Chat

    public static void chat(Context context, String makeChat) {
        Message newMessage = new Message(Login.getUserId(), makeChat);
        try {
            new CommandTask(context).execute(new SendMessageCommand(newMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //new CommandTask(context).execute(new Chat)
    }

    public static boolean canDoChat(Context context, String makeChat) {
        boolean chatNotEmpty = !makeChat.isEmpty();
        return (context != null && Login.getInstance().getToken() != null && Login.getInstance().getUserId() != null && chatNotEmpty);
    }
}

