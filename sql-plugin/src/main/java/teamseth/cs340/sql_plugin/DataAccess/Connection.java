package teamseth.cs340.sql_plugin.DataAccess;

/**
 * Created by Seth on 12/8/2017.
 */

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    public static java.sql.Connection conn;

    public final static Connection SINGLETON = new Connection();
    public Connection() {

    }

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:tickettoridedata.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }
}

