package teamseth.cs340.sql_plugin;

import teamseth.cs340.common.plugin.IPersistanceProvider;

public class PluginSQL implements IPersistanceProvider {
    @Override
    public void initialize() {
        System.out.println("SQL Plugin initialized");
    }
}
