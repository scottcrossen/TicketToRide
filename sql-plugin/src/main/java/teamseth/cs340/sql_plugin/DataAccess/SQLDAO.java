package teamseth.cs340.sql_plugin.DataAccess;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Seth on 12/8/2017.
 */

public class SQLDAO {

    public static final SQLDAO SINGLETON = new SQLDAO();

    public SQLDAO() {

    }
    
    //delete then create Delta table
    public Boolean clearDeltas() throws DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = Connection.SINGLETON.conn.createStatement();

                stmt.executeUpdate(DELETE_DELTA_TABLE_IF_EXISTS);
                stmt.executeUpdate(CREATE_DELTA_TABLE);
            }
            finally {
                if (stmt != null) {

                    stmt.close();
                    stmt = null;
                }
            }
            return true;
        }
        catch (SQLException e) {
            throw new DatabaseException("Delete Deltas failed", e);
        }
    }

    public void createTables() throws DatabaseException {
        //TODO do this block for every table
        {
            SQLDAO sqlDAO = new SQLDAO();
            try {
                Statement stmt = null;
                try {
                    stmt = Connection.SINGLETON.conn.createStatement();
                    stmt.executeUpdate("SELECT * FROM DELTA");
                } finally {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                }
            } catch (SQLException e) {
                try {
                    Statement stmt = null;
                    try {
                        stmt = Connection.SINGLETON.conn.createStatement();

                        stmt.executeUpdate(DELETE_DELTA_TABLE_IF_EXISTS);
                        stmt.executeUpdate(CREATE_DELTA_TABLE);
                    } finally {
                        if (stmt != null) {
                            stmt.close();
                            stmt = null;
                        }
                    }
                    Connection.SINGLETON.conn.commit();
                } catch (SQLException e1) {
                    throw new DatabaseException("createTables failed", e1);
                }
            }
        }
    }

    /**
     * @param delta Adds a serializable to database
     * @return
     */
    public boolean addDelta(Serializable delta) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO DELTA (hidden_id,game_id," +
                        "order,command) values ( \"";// +
                        /*delta.getEventID() + "\",\"" +
                        delta.getDescendant() + "\",\"" +
                        delta.getPerson() + "\",\"" +
                        delta.getLatitude() + "\",\"" +
                        delta.getLongitude() + "\",\"" +
                        delta.getCountry() + "\",\"" +
                        delta.getCity() + "\",\"" +
                        delta.getEventType() + "\",\"" +
                        delta.getYear() + "\")";*/
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);

                if (stmt.executeUpdate() != 1) {
                    throw new DatabaseException("addEvent failed: Could not insert delta");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("addEvent failed", e);
        }
    }

    /**
     * gets a single delta from the database based on a string personID and password
     * @return
     * @throws DatabaseException
     */
    /*public Delta getSingleDelta(String personID, String password) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Delta user = null;

            try {
                //this makes sure the password and username match and are correct
                String sql = SELECT_ALL_DELTAS + " WHERE username=\'" + username + "\' AND password=\'" + password + "\'";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while(rs.next())
                {
                    String userID2 = rs.getString(2);
                    String userName = rs.getString(1);
                    String password2 = rs.getString(3);
                    String email = rs.getString(4);
                    String firstName = rs.getString(5);
                    String lastName = rs.getString(6);
                    String gender = rs.getString(7);

                    user = new Delta(userName, userID2, password2,
                            email, firstName, lastName, gender);
                }

            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
            return user;

        } catch (SQLException e) {
            throw new DatabaseException("loadDelta failed", e);
        }
    }*/

    private static final String SELECT_ALL_DELTAS = "select * from DELTA";
    private static final String DELETE_DELTA_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS DELTA";
    private static final String CREATE_DELTA_TABLE =
            "CREATE TABLE DELTA " +
                    "(" +
                    "   hidden_id BIGINT AUTOINCREMENT NOT NULL," +
                    "   game_id BINARY(16) NOT NULL FOREIGN KEY REFERENCES Game(id)," +
                    "   order INTEGER NOT NULL," +
                    "   command TEXT NOT NULL," +
                    "   PRIMARY KEY (hidden_id)" +
                    ")";
}
