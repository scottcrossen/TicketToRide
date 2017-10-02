package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ModelActionException extends Exception {
    public ModelActionException() {
        super("The attempted action violated model restrictions");
    }
}
