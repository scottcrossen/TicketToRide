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
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 *
 * The AuthToken class is the primary means of authentication for the application.
 * Authtokens are generated on the server and sealed using a randomly generated key
 * created by the server. Only token data is sealed: the client can access some fields
 * but most of them it cannot. Because of this construction, the server can test authetication
 * by simply unencrypting the token and reading sealed data. No server store access is needed to
 * do this. However, as it is just an initial draft, the ivSpec is stored in the object as well.
 *
 * {@invariant ivSpec.length == 16}
 */
public class AuthToken implements Serializable {
    /**
     * Included to solve a deserialization error involving different IDs between server and client.
     */
    private static final long serialVersionUID = 8368458447159720690L;

    /**
     * The sealed portion of the token
     */
    private static class TokenData implements Serializable {
        /**
         * Underlying data struct constructor. Seeds the struct data
         * @param privilege    The privilege this token has. Currently only User, Admin, System
         * @param instant      The instantiation time stamp of the token
         * @param userId       The user-id of the requesting user
         *
         * No pre/post conditions included because this isn't a constructor/method of the main class.
         */
        public TokenData(AuthType privilege, Instant instant, UUID userId){
            assert (userId != null);
            assert (privilege != null);
            assert (instant != null);
            // Initialize the struct
            this.privilege = privilege;
            this.instant = instant;
            this.userId = userId;
        }
        public AuthType privilege;
        public Instant instant;
        public UUID userId;
    }
    private SealedObject tokenData;

    /**
     * The ivspec paramter necessary for encryption. Randomly generated on creation.
     */
    private byte[] ivSpec = new byte[16];

    /**
     * AuthToken constructor. Generates sealed and unsealed data stored in fields.
     * @param user User     The user object stored in the user database.
     *
     * {@pre Config contains valid key && user exists with non-null feilds}
     * {@post constructs an AuthToken with valid feilds.}
     */
    public AuthToken(User user){
        // Randomize the ivSpec
        SecureRandom r = new SecureRandom();
        r.nextBytes(this.ivSpec);
        try {
            // Seed the tokenData field.
            this.tokenData = encryptData(new TokenData(user.getAuthType(), Instant.now(), user.getId()));
        } catch (Exception e) {
            this.tokenData = null;
        }
    }

    /**
     * Encrypts the underlying data object
     * @param data TokenData    The data to be sealed.
     * @return SealedObject     A SealedObject object containing the sealed data.
     * @throws UnauthorizedException    The private key or ivspec must not exist.
     *
     * {@pre data is non-null}
     * {@pre Config contains valid key}
     * {@post retval is non-null of type SealedObject}
     * {@post immutability in class feilds maintained}
     */
    private SealedObject encryptData(TokenData data) throws UnauthorizedException {
        // Get key from config
        SecretKey key = Config.getInstance().getSecretKey();
        // Make sure key is valid
        if (key == null) throw new UnauthorizedException();
        try {
            // Initialize cipher in encrypt mode
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(this.ivSpec));
            // Encrypt object and return
            return new SealedObject(data, cipher);
        } catch (Exception e) {
            // This normally won't be accessed if the code is implemented correctly (which it is)
            // Only thown if key is invalid in config.
            throw new UnauthorizedException();
        }
    }

    /**
     * Decrypts the underlying data object
     * @param sealed SealedObject   The sealed data object to be unsealed
     * @return TokenData    A casted TokenData object representing the underlying token data.
     * @throws UnauthorizedException    The token has been tampered with.
     *
     * {@pre sealed is non-null}
     * {@pre Config contains valid key}
     * {@pre sealed has not been tampered with and was encrypted by the same key/algorithm}
     * {@post retval is non-null of type TokenData}
     * {@post immutability in class feilds maintained}
     */
    private TokenData decryptData(SealedObject sealed) throws UnauthorizedException {
        // Get key from config
        SecretKey key = Config.getInstance().getSecretKey();
        // Make sure key is valid
        if (key == null) throw new UnauthorizedException();
        try {
            // Initialize cipher in decrypt mode
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(this.ivSpec));
            // decrypt object and return
            return (TokenData) sealed.getObject(cipher);
        } catch (Exception e) {
            // Cipher encountered error. The object must have been tampered with.
            throw new UnauthorizedException();
        }
    }

    /**
     * Returns the user stored in the token.
     * @return UUID  A UUID referring to the user id that this token is associated with.
     * @throws UnauthorizedException    The token was not able to be unsealed (has been tampered with).
     *
     * {@pre isValid()}
     * {@post retval == a valid UUID}
     */
    public UUID getUser() throws UnauthorizedException {
        // Attempt to decrypt data.
        TokenData data = this.decryptData(this.tokenData);
        // Return extracted user ID.
        return data.userId;
    }

    /**
     * Checks to see if the token is valid (untampered and not timed-out)
     * @param type AuthType    The type of user requried: user, admin, system.
     * @return boolean  true if token is valid. false otherwise.
     *
     * {@pre none}
     * {@post retval == true iff ivSpec != null &&
     *                              tokenData != null &&
     *                              tokenData hasn't been modified &&
     *                              tokenData encrypted with same key/algorithm &&
     *                              type == privaledge &&
     *                              timestamp is less than max age}
     */
    public boolean isValid(AuthType type) {
        // Check that feilds are set.
        if (this.ivSpec == null || this.tokenData == null) return false;
        try {
            // Attempt to decrypt data
            TokenData data = this.decryptData(this.tokenData);
            // Check all fields to make sure token is valid.
            if (!data.privilege.equals(type)) return false;
            Duration tokenAge = Duration.between(data.instant, Instant.now());
            if (tokenAge.compareTo(Config.getInstance().getMaxTokenAge()) > 0) return false;
            return true;
        } catch (Exception e) {
            // An error occured while decrypting. Token has been tampered with.
            return false;
        }
    }
}
