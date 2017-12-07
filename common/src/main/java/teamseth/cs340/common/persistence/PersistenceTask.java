package teamseth.cs340.common.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import teamseth.cs340.common.util.Logger;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistenceTask {
    private static Map<UUID, Semaphore> currentLocks = new HashMap<UUID, Semaphore>();
    private static final Semaphore getMutex(UUID objectId) {
        Semaphore mutex;
        mutex = currentLocks.get(objectId);
        if (mutex == null) {
            mutex = new Semaphore(1);
            currentLocks.put(objectId, mutex);
        }
        return mutex;
    }
    private static ExecutorService executors = Executors.newCachedThreadPool();
    public static final Future<Void> save(IStorable storable, IDeltaCommand command) {
        return CompletableFuture.supplyAsync(() -> getMutex(storable.getId()), executors).thenCompose((Semaphore mutex) -> {
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