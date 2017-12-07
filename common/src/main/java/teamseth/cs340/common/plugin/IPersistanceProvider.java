package teamseth.cs340.common.plugin;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ObjectType;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.util.MaybeTuple;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public interface IPersistanceProvider {

    /**
     * The type of persistance provider
     */
    public enum ProviderType {MONGO, SQL, OTHER}

    /**
     * Included as part of the plugin lifecycle.
     */
    void initialize();

    /**
     * Included as part of the plugin lifecycle. May not always be called on terminate.
     */
    void finalize();

    /**
     * Query for the plugin to return what type it is.
     * @return
     */
    ProviderType getProviderType(); // Should be hard coded

    /**
     * Replaces the current state of an object in memory whether it exists or not and clears all associated deltas.
     * @param Object    The object to replace
     * @param type      The type of object
     * @param ObjectId  The ID of the object
     */
    void upsertObjectState(Serializable Object, ObjectType type, UUID ObjectId); // Clear associated deltas as well.

    /**
     * Inserts an iterative state-command of the object in memory
     * @param Object    The command delta to store
     * @param ObjectId  The ID of the object that it's associated with (see last method).
     */
    void insertObjectDelta(Serializable Object, UUID ObjectId); // Make sure that upsert order is stored.

    /**
     * Loads all objects of type and their associated deltas.
     * @param type  The type of the object to load
     * @return      A list of objects and their paired deltas.
     */
    List<MaybeTuple<Serializable, List<Serializable>>> getAllOfType(ObjectType type); // No need to include object Id.

    /**
     * Inserts/updates a user into memory
     * @param user  The user to upsert
     */
    void upsertUser(User user); // User is serializable fyi.

    /**
     * Returns all users to be loaded in ram
     * @return      A list of all users in the database
     */
    List<User> getAllUsers();
}

