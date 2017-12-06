package teamseth.cs340.mongo_plugin;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class PluginMongo implements IPersistanceProvider{
    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    DB db = mongoClient.getDB("TicketToRide");

    public PluginMongo() throws UnknownHostException {
    }

    @Override
    public void initialize() {
        System.out.println("Mongo Plugin initialized");
    }
}
