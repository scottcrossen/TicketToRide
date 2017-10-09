package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ModelActionException extends Exception {
    private static final long serialVersionUID = 5842707007555619939L;
    public ModelActionException() {
        super("Error: Action not allowed.");
    }

    @Override
    public String getMessage(){
        return "Error: Action not allowed.";
    }
}
