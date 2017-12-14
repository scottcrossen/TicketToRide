package teamseth.cs340.common.util.server;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.persistence.plugin.IPersistenceProvider;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.util.auth.AuthType;

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

    private Optional<IPersistenceProvider.ProviderType> persistanceType = Optional.empty(); //Optional.of(IPersistenceProvider.ProviderType.SQL);

    public Optional<IPersistenceProvider.ProviderType> getPersistanceType() { return persistanceType; }

    private SecretKey secretKey;

    public SecretKey getSecretKey() { return secretKey; }

    private Duration maxTokenAge = Duration.ofMinutes(120);

    public Duration getMaxTokenAge() { return maxTokenAge; }

    public ExecutorService getExecutionContext(ContextType type) {
        switch(type) {
            case GLOBAL:
                return null;
            case PERSISTENCE:
                return Executors.newCachedThreadPool();
            default:
                return null;
        }
    }

    public enum ContextType {GLOBAL, PERSISTENCE}

    public AuthToken getSystemToken() {
        return new AuthToken(new User(AuthType.system));
    }

}
