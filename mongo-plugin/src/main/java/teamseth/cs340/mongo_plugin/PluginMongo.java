package teamseth.cs340.mongo_plugin;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;

public class PluginMongo implements IPersistenceProvider {
    MongoClient mongoClient;
    DB db;

    @Override
    public void initialize() {
        try {
            mongoClient = new MongoClient( "localhost" , 27017 );
            db = mongoClient.getDB("TicketToRide");
            Logger.info("Mongo provider initialized");
        } catch (Exception e) {
            Logger.error("Mongo provider could not be initialized: " + e.getMessage());
        }
        if(!db.collectionExists("stateObjects")) db.createCollection("stateObjects", null);
        if(!db.collectionExists("stateDeltas")) db.createCollection("stateDeltas", null);
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.MONGO;
    }

    @Override
    public void finalize() {

    }

    @Override
    public CompletableFuture<Boolean> upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ModelObjectType type, int deltasBeforeUpdate) {
        DBCollection deltas = db.getCollection("stateDeltas");
        DBCollection objects = db.getCollection("stateObjects");
        BasicDBObject deltaDoc = new BasicDBObject("UUID", ObjectId)
                .append("delta", delta);
        deltas.insert(deltaDoc);
        BasicDBObject deltaQuery = new BasicDBObject("UUID", ObjectId);
        DBCursor deltaCursor = deltas.find(deltaQuery);
        if(deltaCursor.count() >= deltasBeforeUpdate){
            deltas.remove(deltaQuery);
            objects.remove(deltaQuery);
            BasicDBObject objectDoc = new BasicDBObject("UUID", ObjectId)
                    .append("Object", newObjectState)
                    .append("Type", type);
            objects.insert(objectDoc);
        }
        return CompletableFuture.supplyAsync(() -> false);
    }

    @Override
    public CompletableFuture<List<MaybeTuple<Serializable, List<Serializable>>>> getAllOfType(ModelObjectType type) {

        return CompletableFuture.supplyAsync(() -> new LinkedList<MaybeTuple<Serializable, List<Serializable>>>());
    }
}
