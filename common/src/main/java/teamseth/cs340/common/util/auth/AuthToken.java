package teamseth.cs340.common.util.auth;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.util.server.Config;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AuthToken implements Serializable {
    private static final long serialVersionUID = 8368458447159720690L;
    private static class TokenData implements Serializable {
        public TokenData(AuthType privilege, Instant instant, UUID userId){
            this.privilege = privilege;
            this.instant = instant;
            this.userId = userId;
        }
        public AuthType privilege;
        public Instant instant;
        public UUID userId;
    }

    private SealedObject tokenData;
    private byte[] ivSpec;

    public AuthToken(User user){
        SecureRandom r = new SecureRandom();
        this.ivSpec = new byte[16];
        r.nextBytes(this.ivSpec);
        try {
            tokenData = encryptData(new TokenData(user.getAuthType(), Instant.now(), user.getId()));
        } catch (Exception e) {
            this.tokenData = null;
        }
    }

    public SealedObject encryptData(TokenData data) throws UnauthorizedException {
        SecretKey key = Config.getInstance().getSecretKey();
        if (key == null) throw new UnauthorizedException();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(this.ivSpec));
            return new SealedObject(data, cipher);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }

    private TokenData decryptData(SealedObject sealed) throws UnauthorizedException {
        SecretKey key = Config.getInstance().getSecretKey();
        if (key == null) throw new UnauthorizedException();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(this.ivSpec));
            return (TokenData) sealed.getObject(cipher);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }

    public UUID getUser() throws UnauthorizedException {
        TokenData data = this.decryptData(this.tokenData);
        return data.userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        if (this.tokenData != null || !this.tokenData.equals(authToken.tokenData)) return false;
        if (this.ivSpec != null || !this.ivSpec.equals(authToken.ivSpec)) return false;
        return true;
    }

    public boolean isValid(AuthType type) {
        if (this.ivSpec == null || this.tokenData == null) return false;
        try {
            TokenData data = this.decryptData(this.tokenData);
            if (data.privilege != type) return false;
            Duration tokenAge = Duration.between(data.instant, Instant.now());
            if (tokenAge.compareTo(Config.getInstance().getMaxTokenAge()) > 0) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
