package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SessionExpiredException extends Exception {
    public SessionExpiredException() {
        super("The token used in the request has expired");
    }
}
