package teamseth.cs340.mongo_plugin;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ObjectType;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.plugin.IPersistanceProvider;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;

public class PluginMongo implements IPersistanceProvider {
    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    DB db = mongoClient.getDB("TicketToRide");

    public PluginMongo() throws UnknownHostException {
    }

    @Override
    public void initialize() {
        Logger.info("Mongo provider initialized");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.MONGO;
    }

    @Override
    public void finalize() {

    }

    @Override
    public void upsertObjectState(Serializable Object, ObjectType type, UUID ObjectId) {

    }

    @Override
    public void insertObjectDelta(Serializable Object, UUID ObjectId) {

    }

    @Override
    public List<MaybeTuple<Serializable, List<Serializable>>> getAllOfType(ObjectType type) {
        return null;
    }

    @Override
    public void upsertUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
