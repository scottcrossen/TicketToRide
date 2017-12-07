package teamseth.cs340.common.util.server;

import java.time.Duration;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import teamseth.cs340.common.plugin.IPersistenceProvider;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Config {
    private static Config instance;
    public static  Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public Config() {
        try {
            // We don't require the secretKey to persist because right now it's just used for tokens.
            this.secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (Exception e) {
            this.secretKey = null;
        }
        //Logger.setVerbosity(6);
    }

    private Optional<IPersistenceProvider.ProviderType> persistanceType = Optional.empty();

    public Optional<IPersistenceProvider.ProviderType> getPersistanceType() { return persistanceType; }

    private SecretKey secretKey;

    public SecretKey getSecretKey() { return secretKey; }

    private Duration maxTokenAge = Duration.ofMinutes(120);

    public Duration getMaxTokenAge() { return maxTokenAge; }

}
