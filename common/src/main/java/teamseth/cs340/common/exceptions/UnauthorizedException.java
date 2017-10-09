package teamseth.cs340.common.exceptions;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class UnauthorizedException extends Exception {
    private static final long serialVersionUID = -1372608453349490966L;
    public UnauthorizedException() {
        super("Error: Unauthorized.");
    }

    @Override
    public String getMessage(){
        return "Error: Unauthorized.";
    }


}
