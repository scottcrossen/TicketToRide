package teamseth.cs340.common.models.server.authentication;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import teamseth.cs340.common.models.IModel;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AuthTokenModel implements IModel<AuthToken> {
    private static AuthTokenModel instance;

    public static AuthTokenModel getInstance() {
        if(instance == null) {
            instance = new AuthTokenModel();
        }
        return instance;
    }

    private Set<AuthToken> tokens = new TreeSet<AuthToken>();

    // This should only be accessible through package members so people can't hack it as easily.
    protected void upsert(AuthToken token) {
        tokens.add(token);
    }

    public boolean authorized(AuthToken token, AuthType type) {
        Iterator<AuthToken> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            AuthToken currentToken = iterator.next();
            if (token == currentToken) {
                if (currentToken.isExpired()) {
                    iterator.remove();
                    return false;
                } else if (currentToken.privilege == type){
                    return true;
                } else {
                    return false;
                }
            }
            if(currentToken.isExpired()) iterator.remove();
        }
        return false;
    }

}
