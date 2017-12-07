package teamseth.cs340.common.plugin;

import java.util.List;

import teamseth.cs340.common.util.Logger;
import teamseth.cs340.common.util.server.Config;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistanceAccess {
    private static PersistanceAccess instance;
    private static List<IPersistanceProvider> providers;

    public static PersistanceAccess getInstance() {
        if(instance == null) {
            instance = new PersistanceAccess();
        }
        return instance;
    }

    public void initialize(String[] args) {
        Logger.info("Loading plugins from providers");
        providers = PersistanceLoader.loadPlugins(args, Config.getInstance().getPersistanceType());
        if (providers.size() <= 0) {
            Logger.warn("Supported providers not found in plugins");
        } else {
            Logger.info("Plugins loaded successfully");
            providers.forEach((IPersistanceProvider provider) -> provider.initialize());
            Logger.info("Persistance providers initialized");
        }
    }

}
