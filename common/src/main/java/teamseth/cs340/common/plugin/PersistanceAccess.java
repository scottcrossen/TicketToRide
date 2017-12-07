package teamseth.cs340.common.plugin;

import java.util.List;

import teamseth.cs340.common.util.Logger;

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

    public void initialize() {
        Logger.info("Loading plugins from providers");
        providers = PersistanceLoader.loadPlugins();
        if (providers.size() > 0) {
            Logger.info("Plugins loaded successfully");
        } else {
            Logger.warn("Supported providers not found in plugins");
        }
    }

}
