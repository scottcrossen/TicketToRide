package teamseth.cs340.common.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.server.Config;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistenceTask {
    private static Map<UUID, OrderedSemaphore> currentLocks = new HashMap<UUID, OrderedSemaphore>();
    private static final OrderedSemaphore getMutex(UUID objectId) {
        OrderedSemaphore mutex;
        mutex = currentLocks.get(objectId);
        if (mutex == null) {
            mutex = new OrderedSemaphore(1);
            currentLocks.put(objectId, mutex);
        }
        return mutex;
    }
    private static ExecutorService executors = Config.getInstance().getExecutionContext(Config.ContextType.PERSISTENCE);
    public static final Future<Void> save(IStorable storable, IDeltaCommand command) {
        return CompletableFuture.supplyAsync(() -> getMutex(storable.getId()), executors).thenCompose((OrderedSemaphore mutex) -> {
            try {
                mutex.acquire();
                return PersistenceAccess.save(storable, command).thenApply((Boolean result) -> {
                    mutex.release();
                    return result;
                });
            } catch (InterruptedException e) {
                mutex.release();
                return CompletableFuture.completedFuture(false);
            }
        }).thenApply((Boolean result) -> {
            if (!result) {
                Logger.error("Failed to store " + PersistenceAccess.getTypeFromStorable(storable) + " object in persistance");
            }
            return null;
        });
    }
}