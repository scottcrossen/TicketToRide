package teamseth.cs340.common.plugin;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public interface IPersistanceProvider {
    public enum ProviderType {
        MONGO, SQL, OTHER
    }
    void initialize();
    ProviderType getProviderType();
}

