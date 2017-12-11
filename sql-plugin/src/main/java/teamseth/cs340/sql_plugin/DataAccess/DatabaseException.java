package teamseth.cs340.sql_plugin.DataAccess;

/**
 * Created by Seth on 3/1/2017.
 */
public class DatabaseException extends Exception {

    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
