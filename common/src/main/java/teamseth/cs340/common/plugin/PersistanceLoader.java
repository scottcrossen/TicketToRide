package teamseth.cs340.common.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import teamseth.cs340.common.util.Logger;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PersistanceLoader {

    private static PersistanceAccess instance;

    public static PersistanceAccess getInstance() {
        if(instance == null) {
            instance = new PersistanceAccess();
        }
        return instance;
    }

    public static List<IPersistanceProvider> loadPlugins(){
        return DefaultPluginPaths.getPaths().stream().flatMap((String defaultPath) -> loadPluginFromDir(defaultPath).stream()).collect(Collectors.toList());
    }

    private static List<IPersistanceProvider>  loadPluginFromDir(String dir) {
        return loadPluginFromFile(dir);
    }
    private static List<IPersistanceProvider> loadPluginFromFile(String path) {
        List providers = new LinkedList();
        try {
            if (path.length() > 0) {
                String fixedPath = "file://" + ((Character.toString(path.charAt(0)) != File.separator) ? System.getProperty("user.dir") + File.separator + path : path);
                URL pathUrl = new URL(fixedPath);
                File jarFile = new File(pathUrl.toURI());
                if ((jarFile).exists()) {
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
                    boolean returnStatus = ClassFinder.findClasses(jarFile, new ClassFinder.Visitor<String>() {
                        @Override
                        public boolean visit(String clazz) {
                            try {
                                if (!classLoader.loadClass(clazz).isInterface() && classLoader.loadClass(clazz).newInstance() instanceof IPersistanceProvider) {
                                    providers.add((IPersistanceProvider) classLoader.loadClass(clazz).newInstance());
                                }
                            } catch (Exception e) {
                                Logger.warn("Could not read all classes in jar");
                            }
                            return true;
                        }
                    });
                } else {
                    Logger.warn("Plugin path does not exist");
                }
            } else {
                Logger.warn("Malformed plugin path");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return providers;
    }
}
