package teamseth.cs340.mongo_plugin;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;
import teamseth.cs340.mongo_plugin.DataAccess.DatabaseException;
import teamseth.cs340.mongo_plugin.DataAccess.MongoDAO;

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

    public static byte[] write(Serializable obj) throws IOException {
        //Blob newData = new SerialBlob();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(baos);
        oout.writeObject(obj);
        oout.flush();
        oout.close();
        byte[] output = baos.toByteArray();
        return output;
    }

    public static Serializable read(DBObject obj, String get)
            throws IOException, ClassNotFoundException {
        byte[] buf = (byte[]) obj.get(get);
        if (buf != null) {
            ObjectInputStream objectIn = new ObjectInputStream(
                    new ByteArrayInputStream(buf));
            return (Serializable) objectIn.readObject();
        }
        return null;
    }

    @Override
    public CompletableFuture<Boolean> upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ModelObjectType type, int deltasBeforeUpdate) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                mongoClient = new MongoClient( "localhost" , 27017 );
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return false;
            }
            db = mongoClient.getDB("TicketToRide");
            DBCollection deltas = db.getCollection("stateDeltas");
            DBCollection objects = db.getCollection("stateObjects");
            BasicDBObject deltaDoc = null;
            try {
                deltaDoc = new BasicDBObject("UUID", ObjectId.toString())
                        .append("delta", write(delta))
                        .append("type", type.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            deltas.insert(deltaDoc);
            BasicDBObject deltaQuery = new BasicDBObject("UUID", ObjectId.toString());
            DBCursor deltaCursor = deltas.find(deltaQuery);
            if(deltaCursor.count() >= deltasBeforeUpdate){
                deltas.remove(deltaQuery);
                objects.remove(deltaQuery);
                BasicDBObject objectDoc = null;
                try {
                    objectDoc = new BasicDBObject("UUID", ObjectId.toString())
                            .append("Object", write(newObjectState))
                            .append("type", type.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                objects.insert(objectDoc);
            }
            return true;
        });
    }

    @Override
    public CompletableFuture<List<MaybeTuple<Serializable, List<Serializable>>>> getAllOfType(ModelObjectType type) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                mongoClient = new MongoClient( "localhost" , 27017 );
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            db = mongoClient.getDB("TicketToRide");
            DBCollection deltas = db.getCollection("stateDeltas");
            DBCollection objects = db.getCollection("stateObjects");
            BasicDBObject query = new BasicDBObject("type", type.toString());
            DBCursor objectCursor = objects.find(query);
            LinkedList<MaybeTuple<Serializable, List<Serializable>>> output = new LinkedList<MaybeTuple<Serializable, List<Serializable>>>();
            try {
                while (objectCursor.hasNext()){
                    DBObject object = objectCursor.next();
                    BasicDBObject deltaQuery = new BasicDBObject("UUID", object.get("UUID"));
                    Serializable stateObject = read(object, "Object");
                    DBCursor deltaCursor = deltas.find(deltaQuery);
                    List<Serializable> deltaList = new LinkedList<>();
                    try {
                        while (deltaCursor.hasNext()) {
                            deltaList.add(read(deltaCursor.next(), "delta"));
                        }
                    } finally {
                        deltaCursor.close();
                    }

                    MaybeTuple<Serializable, List<Serializable>> tuple = new MaybeTuple<Serializable, List<Serializable>>(stateObject, deltaList);
                    output.add(tuple);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                objectCursor.close();
            }
            return output;
        });
    }

    @Override
    public CompletableFuture<Boolean> clearData() {
        return CompletableFuture.supplyAsync(() -> {
            boolean wasCleared = false;
            try {
                wasCleared = MongoDAO.SINGLETON.clearData();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            return wasCleared;
        });
    }
}
