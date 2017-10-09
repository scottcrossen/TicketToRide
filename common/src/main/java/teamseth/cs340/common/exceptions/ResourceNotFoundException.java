package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ResourceNotFoundException extends Exception {
    private static final long serialVersionUID = 6843920357828690275L;

    public ResourceNotFoundException() {
        super("Error: Resource not found.");
    }

    @Override
    public String getMessage(){
        return "Error: Resource not found.";
    }

}
