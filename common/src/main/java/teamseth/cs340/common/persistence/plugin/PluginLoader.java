package teamseth.cs340.common.persistence.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import teamseth.cs340.common.persistence.PersistenceAccess;
import teamseth.cs340.common.util.Logger;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class PluginLoader {

    private static PersistenceAccess instance;

    public static PersistenceAccess getInstance() {
        if(instance == null) {
            instance = new PersistenceAccess();
        }
        return instance;
    }

    public static List<IPersistenceProvider> loadPlugins(String[] args, Optional<IPersistenceProvider.ProviderType> providerType) {
        Optional<IPersistenceProvider.ProviderType> localProviderType = providerType;
        List<File> customPluginPaths = new LinkedList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--plugin-path") && i < args.length - 1) {
                customPluginPaths.add(new File(args[i+1]));
            } else if (args[i].equals("--plugin-type") && i < args.length - 1) {
                switch (args[i+1]) {
                    case "mongo":
                        localProviderType = Optional.of(IPersistenceProvider.ProviderType.MONGO);
                        break;
                    case "sql":
                        localProviderType = Optional.of(IPersistenceProvider.ProviderType.SQL);
                        break;
                    case "other":
                        localProviderType = Optional.of(IPersistenceProvider.ProviderType.OTHER);
                        break;
                    default:
                        Logger.warn("Command line argument for plugin-type malformed");
                        break;
                }
            }
        }
        final Optional<IPersistenceProvider.ProviderType> useOnlyType = localProviderType;
        return Stream.concat(
            customPluginPaths.stream().flatMap((File defaultPath) -> {
                return loadPluginFromDir(defaultPath, false, false).stream();
            }),
            DefaultPluginPaths.getPaths().stream().flatMap((File defaultPath) -> {
                return loadPluginFromDir(defaultPath, true, true).stream();
            })
        ).filter((IPersistenceProvider provider) -> {
            return useOnlyType.map((IPersistenceProvider.ProviderType type) -> type.equals(provider.getProviderType())).orElseGet(() -> true);
        }).collect(Collectors.toList());
    }

    private static List<IPersistenceProvider>  loadPluginFromDir(File dir, boolean quiet, boolean shallow) {
        if (dir.isFile()) {
            return loadPluginFromFile(dir);
        } else if (dir.isDirectory() ) {
            return Arrays.stream(dir.listFiles()).flatMap((File newDir) -> {
                if (shallow) {
                    if (newDir.isFile()) {
                        return loadPluginFromFile(newDir).stream();
                    } else {
                        return Stream.empty();
                    }
                } else {
                    return loadPluginFromDir(newDir, quiet, shallow).stream();
                }
            }).collect(Collectors.toList());
        } else {
            if (!quiet) {
                Logger.warn("Plugin path does not exist");
            }
            return new LinkedList<>();
        }
    }
    private static List<IPersistenceProvider> loadPluginFromFile(File jarFile) {
        List providers = new LinkedList();
        try {
            URL pathUrl = jarFile.toURI().toURL();
            if ((jarFile).exists()) {
                URLClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
                boolean returnStatus = ClassFinder.findClasses(jarFile, new ClassFinder.Visitor<String>() {
                    @Override
                    public boolean visit(String clazz) {
                        try {
                            if (!classLoader.loadClass(clazz).isInterface() && classLoader.loadClass(clazz).newInstance() instanceof IPersistenceProvider) {
                                providers.add((IPersistenceProvider) classLoader.loadClass(clazz).newInstance());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return providers;
    }
}
