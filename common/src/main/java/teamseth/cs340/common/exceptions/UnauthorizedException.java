package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("The token used in the request does not have sufficient privileges");
    }
}
