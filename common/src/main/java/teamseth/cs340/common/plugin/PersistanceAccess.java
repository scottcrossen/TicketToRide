package teamseth.cs340.common.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import teamseth.cs340.common.util.Logger;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistanceAccess {
    private static PersistanceAccess instance;
    private static IPersistanceProvider provider;

    public static PersistanceAccess getInstance() {
        if(instance == null) {
            instance = new PersistanceAccess();
        }
        return instance;
    }

    public void initialize() {
        loadPluginFromPath("sql_plugin/build/libs/sql_plugin.jar");
    }

    private void loadPluginFromDir(String dir, String key) {

    }

    private void loadPluginFromPath(String path) {
        try {
            Logger.info("Loading plugin from path");
            if (path.length() > 0) {
                String fixedPath = "file://" + ((path.charAt(0) != '/') ? System.getProperty("user.dir") + "/" + path : path);
                URL pathUrl = new URL(fixedPath);
                File jarFile = new File(pathUrl.toURI());
                if ((jarFile).exists()) {
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
                    boolean returnStatus = ClassFinder.findClasses(jarFile, new ClassFinder.Visitor<String>() {
                        @Override
                        public boolean visit(String clazz) {
                            try {
                                if (!classLoader.loadClass(clazz).isInterface() && classLoader.loadClass(clazz).newInstance() instanceof IPersistanceProvider) {
                                    if (provider == null) {
                                        provider = (IPersistanceProvider) classLoader.loadClass(clazz).newInstance();
                                    } else {
                                        provider = null;
                                        Logger.error("Multiple instances of persistance provider found.");
                                        return false;
                                    }
                                }
                            } catch (Exception e) {
                                Logger.error("Could not read all classes in jar");
                            }
                            return true;
                        }
                    });
                    if (provider != null) {
                        Logger.info("Plugin loaded successfully");
                    } else if (!returnStatus) {
                        Logger.error("Supported class not found in plugin");
                    }
                } else {
                    Logger.error("Plugin path does not exist");
                }
            } else {
                Logger.error("Malformed plugin path");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
