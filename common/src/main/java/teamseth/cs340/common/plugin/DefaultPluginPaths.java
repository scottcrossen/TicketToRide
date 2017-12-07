package teamseth.cs340.common.plugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class DefaultPluginPaths {
    private static List<String> paths = Arrays.asList(
            String.join(File.separator, Arrays.asList("sql-plugin", "build", "libs", "sql-plugin.jar")),
            String.join(File.separator, Arrays.asList("mongo-plugin", "build", "libs", "mongo-plugin.jar")),
            "sql-plugin.jar",
            "mongo-plugin.jar"
    );
    public static List<String> getPaths() { return paths; }
}
