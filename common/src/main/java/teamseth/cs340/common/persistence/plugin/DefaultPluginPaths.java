package teamseth.cs340.common.persistence.plugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class DefaultPluginPaths {
    private static List<File> paths = Arrays.asList(
            new File(String.join(File.separator, Arrays.asList("sql-plugin", "build", "libs", "sql-plugin.jar"))),
            new File(String.join(File.separator, Arrays.asList("mongo-plugin", "build", "libs", "mongo-plugin.jar"))),
            new File("sql-plugin.jar"),
            new File("mongo-plugin.jar")
    );
    public static List<File> getPaths() { return paths; }
}
