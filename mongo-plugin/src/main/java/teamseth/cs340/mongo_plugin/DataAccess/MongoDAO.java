package teamseth.cs340.mongo_plugin.DataAccess;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mike on 12/13/17.
 */

public class MongoDAO {
    MongoClient mongoClient;
    DB db;
    public static final MongoDAO SINGLETON = new MongoDAO();
    public MongoDAO(){

    }

    public Boolean clearData() throws DatabaseException {
        try {
            mongoClient = new MongoClient( "localhost" , 27017 );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        db = mongoClient.getDB("TicketToRide");
        DBCollection states = db.getCollection("stateObjects");
        states.drop();
        DBCollection deltas = db.getCollection("stateDeltas");
        deltas.drop();
        db.createCollection("stateObjects", null);
        db.createCollection("stateDeltas", null);
        return false;
    }



}
