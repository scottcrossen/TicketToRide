package teamseth.cs340.sql_plugin;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ObjectType;
import teamseth.cs340.common.plugin.IPersistenceProvider;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.MaybeTuple;

public class PluginSQL implements IPersistenceProvider {
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
    public void upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ObjectType type, int deltasBeforeUpdate) {

    }

    @Override
    public List<MaybeTuple<Serializable, List<Serializable>>> getAllOfType(ObjectType type) {
        return null;
    }
}
