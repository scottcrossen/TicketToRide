package teamseth.cs340.common.root.server.models.authentication;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AuthToken {
    public UUID id = UUID.randomUUID();
    public AuthType privilege;
    public String token;
    public Instant instant;
    public UUID userId;

    public AuthToken(User user){
        byte bytes[] = new byte[20];
        (new SecureRandom()).nextBytes(bytes);
        this.token = Arrays.toString(bytes);
        this.instant = Instant.now();
        this.privilege = user.privilege;
        this.userId = user.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        if (this.token != null || !this.token.equals(authToken.token)) return false;
        if (this.privilege != null || !this.privilege.equals(authToken.privilege)) return false;
        if (this.instant != null || !this.instant.equals(authToken.instant)) return false;
        return true;
    }

    public boolean isExpired() {
        // Allow authtokens to exist indefinitely.
        return false;
    }
}
