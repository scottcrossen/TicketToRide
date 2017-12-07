package teamseth.cs340.common.persistence;

import java.util.List;

import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
import teamseth.cs340.common.persistence.plugin.PluginLoader;
import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.server.Config;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistenceAccess {
    private static PersistenceAccess instance;
    private static List<IPersistenceProvider> providers;

    public static PersistenceAccess getInstance() {
        if(instance == null) {
            instance = new PersistenceAccess();
        }
        return instance;
    }

    public void initialize(String[] args) {
        Logger.info("Loading plugins from providers");
        providers = PluginLoader.loadPlugins(args, Config.getInstance().getPersistanceType());
        if (providers.size() <= 0) {
            Logger.warn("Supported providers not found in plugins");
        } else {
            Logger.info("Plugins loaded successfully");
            providers.forEach((IPersistenceProvider provider) -> provider.initialize());
            Logger.info("Persistance providers initialized");
        }
    }

}
