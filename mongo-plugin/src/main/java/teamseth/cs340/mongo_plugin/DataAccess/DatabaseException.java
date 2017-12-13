package teamseth.cs340.mongo_plugin.DataAccess;

/**
 * Created by mike on 12/13/17.
 */

public class DatabaseException extends Exception {
    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
