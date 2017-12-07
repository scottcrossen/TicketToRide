package teamseth.cs340.mongo_plugin;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ObjectType;
import teamseth.cs340.common.plugin.IPersistenceProvider;
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
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.MONGO;
    }

    @Override
    public void finalize() {

    }

    @Override
    public void upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ObjectType type, int deltasBeforeUpdate) {

    }

    @Override
    public List<MaybeTuple<Serializable, List<Serializable>>> getAllOfType(ObjectType type) {
        return null;
    }
}
