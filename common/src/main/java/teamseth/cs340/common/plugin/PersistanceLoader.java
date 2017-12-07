package teamseth.cs340.common.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<IPersistanceProvider> loadPlugins(String[] args, Optional<IPersistanceProvider.ProviderType> providerType) {
        Optional<IPersistanceProvider.ProviderType> localProviderType = providerType;
        List<File> customPluginPaths = new LinkedList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--plugin-path") && i < args.length - 1) {
                customPluginPaths.add(new File(args[i+1]));
            } else if (args[i].equals("--plugin-type") && i < args.length - 1) {
                switch (args[i+1]) {
                    case "mongo":
                        localProviderType = Optional.of(IPersistanceProvider.ProviderType.MONGO);
                        break;
                    case "sql":
                        localProviderType = Optional.of(IPersistanceProvider.ProviderType.SQL);
                        break;
                    case "other":
                        localProviderType = Optional.of(IPersistanceProvider.ProviderType.OTHER);
                        break;
                    default:
                        Logger.warn("Command line argument for plugin-type malformed");
                        break;
                }
            }
        }
        final Optional<IPersistanceProvider.ProviderType> useOnlyType = localProviderType;
        return Stream.concat(
            customPluginPaths.stream().flatMap((File defaultPath) -> {
                return loadPluginFromDir(defaultPath, false, false).stream();
            }),
            DefaultPluginPaths.getPaths().stream().flatMap((File defaultPath) -> {
                return loadPluginFromDir(defaultPath, true, true).stream();
            })
        ).filter((IPersistanceProvider provider) -> {
            return useOnlyType.map((IPersistanceProvider.ProviderType type) -> type.equals(provider.getProviderType())).orElseGet(() -> true);
        }).collect(Collectors.toList());
    }

    private static List<IPersistanceProvider>  loadPluginFromDir(File dir, boolean quiet, boolean shallow) {
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
    private static List<IPersistanceProvider> loadPluginFromFile(File jarFile) {
        List providers = new LinkedList();
        try {
            URL pathUrl = jarFile.toURI().toURL();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return providers;
    }
}
