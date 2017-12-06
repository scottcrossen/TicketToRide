package teamseth.cs340.sql_plugin;

public class PluginSQL implements IPersistanceProvider {
    @Override
    public void initialize() {
        System.out.println("SQL Plugin initialized");
    }
}
