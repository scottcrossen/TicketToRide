package teamseth.cs340.sql_plugin;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ObjectType;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.plugin.IPersistanceProvider;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;

public class PluginSQL implements IPersistanceProvider {
    @Override
    public void initialize() {
        Logger.info("SQL provider initialized");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.SQL;
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
