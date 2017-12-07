package teamseth.cs340.sql_plugin;

import teamseth.cs340.common.plugin.IPersistanceProvider;
import teamseth.cs340.common.util.Logger;

public class PluginSQL implements IPersistanceProvider {
    @Override
    public void initialize() {
        Logger.info("SQL Plugin initialized");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.SQL;
    }
}
