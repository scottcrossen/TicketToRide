package teamseth.cs340.common.persistence.plugin;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ObjectType;
import teamseth.cs340.common.util.MaybeTuple;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public interface IPersistenceProvider {

    /**
     * The type of persistence provider
     */
    public static enum ProviderType {MONGO, SQL, OTHER}

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
     * Updates the state of an object in memory. The mutating delta is included in case the delta
     * pattern is being used.
     * @param newObjectState        The object to replace
     * @param delta                 The command that made this change
     * @param ObjectId              The ID of the object
     * @param type                  The type of object
     * @param deltasBeforeUpdate    The suggested amount of deltas before issuing a state update in database. -1 means update always.
     */
    void upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ObjectType type, int deltasBeforeUpdate); // Clear associated deltas as well.

    /**
     * Loads all objects of type and their associated deltas.
     * @param type  The type of the object to load
     * @return      A list of objects and their paired deltas.
     */
    List<MaybeTuple<Serializable, List<Serializable>>> getAllOfType(ObjectType type); // No need to include object Id.

    // User-tables methods are redundant. Users passed in just like any other object.
}

