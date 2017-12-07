package teamseth.cs340.sql_plugin;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
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
    public CompletableFuture<Boolean> upsertObject(Serializable newObjectState, Serializable delta, UUID ObjectId, ModelObjectType type, int deltasBeforeUpdate) {
        return CompletableFuture.supplyAsync(() -> false);
    }

    @Override
    public CompletableFuture<List<MaybeTuple<Serializable, List<Serializable>>>> getAllOfType(ModelObjectType type) {
        return CompletableFuture.supplyAsync(() -> new LinkedList<MaybeTuple<Serializable, List<Serializable>>>());
    }
}
