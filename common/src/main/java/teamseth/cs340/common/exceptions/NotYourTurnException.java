package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class NotYourTurnException extends Exception {

    public NotYourTurnException() {
        super("Error: It is not your turn.");
    }

    @Override
    public String getMessage(){
        return "Error: It is not your turn.";
    }
}
