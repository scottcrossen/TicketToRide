package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("The attempted resource access did not work on account that it does not exist");
    }
}
