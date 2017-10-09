package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SessionExpiredException extends Exception {
    private static final long serialVersionUID = 6430103867093097974L;
    public SessionExpiredException() {
        super("Error: Session expired.");
    }

    @Override
    public String getMessage(){
        return "Error: Session expired.";
    }
}
