package teamseth.cs340.mongo_plugin;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

import teamseth.cs340.common.plugin.IPersistanceProvider;
import teamseth.cs340.common.util.Logger;

public class PluginMongo implements IPersistanceProvider {
    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    DB db = mongoClient.getDB("TicketToRide");

    public PluginMongo() throws UnknownHostException {
    }

    @Override
    public void initialize() {
        Logger.info("Mongo Plugin initialized");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.MONGO;
    }
}
