package teamseth.cs340.common.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.models.server.boards.Routes;
import teamseth.cs340.common.models.server.cards.DestinationDeck;
import teamseth.cs340.common.models.server.cards.ResourceDeck;
import teamseth.cs340.common.models.server.carts.CartSet;
import teamseth.cs340.common.models.server.chat.ChatRoom;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.history.CommandHistory;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
import teamseth.cs340.common.persistence.plugin.PluginLoader;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;
import teamseth.cs340.common.util.server.Config;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistenceAccess {
    private static PersistenceAccess instance;
    private static List<IPersistenceProvider> providers;
    private static Map<ModelObjectType, Integer> deltasBeforeUpdate = new HashMap<>();
    private static final int DEFAULT_DELTA_COUNT = -1;

    public PersistenceAccess() {
        deltasBeforeUpdate.put(ModelObjectType.USER,-1);
        deltasBeforeUpdate.put(ModelObjectType.HISTORY,25);
        deltasBeforeUpdate.put(ModelObjectType.GAME,25);
        deltasBeforeUpdate.put(ModelObjectType.CHAT,15);
        deltasBeforeUpdate.put(ModelObjectType.CARTS,10);
        deltasBeforeUpdate.put(ModelObjectType.DESTINATIONDECK,10);
        deltasBeforeUpdate.put(ModelObjectType.RESOURCEDECK,10);
        deltasBeforeUpdate.put(ModelObjectType.ROUTES,10);
    }

    public static PersistenceAccess getInstance() {
        if(instance == null) {
            instance = new PersistenceAccess();
        }
        return instance;
    }

    public static void initialize(String[] args) {
        Logger.info("Loading plugins from providers");
        providers = PluginLoader.loadPlugins(args, Config.getInstance().getPersistanceType());
        if (providers.size() <= 0) {
            Logger.warn("Supported providers not found in plugins");
        } else {
            Logger.info("Plugins loaded successfully");
            providers.forEach((IPersistenceProvider provider) -> provider.initialize());
            Logger.info("Persistence providers initialized");
        }
    }

    public static int getAmountOfProviders() {
        return providers.size();
    }

    public static CompletableFuture<Boolean> save(IStorable storable, IDeltaCommand command) {
        Logger.debug("Saving object of type " + getTypeFromStorable(storable) + " to persistence");
        ModelObjectType objectType = getTypeFromStorable(storable);
        int deltasBeforeUpdate = getDeltasBeforeUpdate(objectType);
        List<CompletableFuture<Boolean>> futures = providers.stream().map((IPersistenceProvider provider) -> provider.upsertObject(storable, command, storable.getId(), objectType, deltasBeforeUpdate)).collect(Collectors.toList());
        CompletableFuture<List<Boolean>> output = sequence(futures);
        return output.thenApply((List<Boolean> results) -> results.stream().reduce(true, (Boolean val1, Boolean val2) -> val1 != null & val2 != null && val1 && val2));
    }

    public static <A> CompletableFuture<List<A>> getObjects(ModelObjectType type) {
        if (providers.size() < 1) {
            return CompletableFuture.completedFuture(new LinkedList<A>());
        } else if (providers.size() > 1) {
            Logger.error("Cannot recover state from persistence: Too many providers loaded");
            return CompletableFuture.completedFuture(new LinkedList<A>());
        } else {
            return providers.get(0).getAllOfType(type).thenApply((List<MaybeTuple<Serializable, List<Serializable>>> queryResult) -> {
                return queryResult.stream().map((MaybeTuple<Serializable, List<Serializable>> singleObject) -> {
                    return singleObject.get2().map((List<Serializable> deltas) -> {
                        try {
                            return (A) DeltaCompose.compose(singleObject.get1(), deltas);
                        } catch (Exception e) {
                            Logger.error("Object found in state does not match requested type");
                            return null;
                        }
                    }).orElseGet(() -> (A) singleObject);
                }).filter(object -> object != null).collect(Collectors.toList());
            }).thenApply((List<A> data) -> {
                Logger.debug("Loading " + Integer.toString(data.size()) + " objects of type " + type + " from persistence");
                return data;
            });
        }
    }

    public static <A> List<A> getObjectsWait(ModelObjectType type) {
        return (List<A>) getObjects(type).join();
    }

    public static final ModelObjectType getTypeFromStorable(IStorable storable) {
        if (storable instanceof User) {
            return ModelObjectType.USER;
        } else if (storable instanceof CommandHistory) {
            return ModelObjectType.HISTORY;
        } else if (storable instanceof Game) {
            return ModelObjectType.GAME;
        } else if (storable instanceof ChatRoom) {
            return ModelObjectType.CHAT;
        } else if (storable instanceof CartSet) {
            return ModelObjectType.CARTS;
        } else if (storable instanceof DestinationDeck) {
            return ModelObjectType.DESTINATIONDECK;
        } else if (storable instanceof ResourceDeck) {
            return ModelObjectType.RESOURCEDECK;
        } else if (storable instanceof Routes) {
            return ModelObjectType.ROUTES;
        } else {
            return ModelObjectType.OTHER;
        }
    }

    private static final int getDeltasBeforeUpdate(ModelObjectType type) {
        try {
            return deltasBeforeUpdate.get(type);
        } catch (Exception e) {
            return DEFAULT_DELTA_COUNT;
        }
    }

    static<T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
                );
    }
}
