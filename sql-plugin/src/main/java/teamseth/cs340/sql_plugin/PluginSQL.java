package teamseth.cs340.sql_plugin;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;
import teamseth.cs340.sql_plugin.DataAccess.Connection;
import teamseth.cs340.sql_plugin.DataAccess.DatabaseException;
import teamseth.cs340.sql_plugin.DataAccess.SQLDAO;

public class PluginSQL implements IPersistenceProvider {

    SQLDAO sqlDAO;
    Map<UUID, Integer> orderMap = new HashMap<>();

    @Override
    public void initialize() {
        sqlDAO = new SQLDAO();
        try {
            Connection.SINGLETON.openConnection();
            sqlDAO.SINGLETON.createTables();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Logger.info("SQL provider initialized");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.SQL;
    }

    @Override
    public CompletableFuture<Boolean> upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ModelObjectType type, int deltasBeforeUpdate) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (orderMap.get(ObjectId) == null || orderMap.get(ObjectId) >= deltasBeforeUpdate) {
                    orderMap.put(ObjectId, 0);
                    //insert into object here based on uuid objecttype
                    sqlDAO.SINGLETON.addObject(newObjectState, ObjectId, type);
                    //remove deltas based on uuid
                    sqlDAO.SINGLETON.removeDeltasBasedOnGame(ObjectId);
                } else {
                    sqlDAO.SINGLETON.addDelta(delta, ObjectId, orderMap.get(ObjectId) + 1);
                    orderMap.put(ObjectId, orderMap.get(ObjectId) + 1);
                }
                return true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                return false;
            }
        }).thenApply((Boolean output) -> {
            try {
                Connection.SINGLETON.conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return output;
        });
    }

    @Override
    public CompletableFuture<List<MaybeTuple<Serializable, List<Serializable>>>> getAllOfType(ModelObjectType type) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return sqlDAO.SINGLETON.getDeltas(type);
            } catch (DatabaseException e) {
                e.printStackTrace();
                return new LinkedList<MaybeTuple<Serializable, List<Serializable>>>();
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> clearData() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                sqlDAO.SINGLETON.clearDeltas();
                sqlDAO.SINGLETON.clearObjects();
            } catch (DatabaseException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        });
    }
}
