package teamseth.cs340.sql_plugin.DataAccess;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.models.server.ModelObjectType;
import teamseth.cs340.common.util.MaybeTuple;

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

    //delete then create Object table
    public Boolean clearObjects() throws DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = Connection.SINGLETON.conn.createStatement();

                stmt.executeUpdate(DELETE_OBJECT_TABLE_IF_EXISTS);
                stmt.executeUpdate(CREATE_OBJECT_TABLE);
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
            throw new DatabaseException("Delete Objects failed", e);
        }
    }

    public void createTables() throws DatabaseException {
        SQLDAO sqlDAO = new SQLDAO();
        try {
            Statement stmt = null;
            try {
                stmt = Connection.SINGLETON.conn.createStatement();
                stmt.executeUpdate(SELECT_ALL_DELTAS);
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
                throw new DatabaseException("createDeltaTable failed", e1);
            }
        }
        try {
            Statement stmt = null;
            try {
                stmt = Connection.SINGLETON.conn.createStatement();
                stmt.executeUpdate(SELECT_ALL_OBJECTS);
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

                    stmt.executeUpdate(DELETE_OBJECT_TABLE_IF_EXISTS);
                    stmt.executeUpdate(CREATE_OBJECT_TABLE);
                } finally {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                }
                Connection.SINGLETON.conn.commit();
            } catch (SQLException e1) {
                throw new DatabaseException("createObjectTable failed", e1);
            }
        }
    }

    /**
     * @param delta Adds a serializable delta to database
     * @return
     */
    public boolean addDelta(Serializable delta, UUID objectID, int order) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO DELTA (object_id," +
                        "order,delta_command) values ( " +
                        objectID + "\",\"" +
                        order + "\",\"" +
                        delta + "\")";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);

                if (stmt.executeUpdate() != 1) {
                    throw new DatabaseException("addDelta failed: Could not insert delta");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("addDelta failed", e);
        }
    }

    /**
     * @param object Adds a serializable object to database
     * @return
     */
    public boolean addObject(Serializable object, UUID objectID, ModelObjectType type) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO OBJECT (id," +
                        "object,type) values ( " +
                        objectID + "\",\"" +
                        object + "\",\"" +
                        type + "\")";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);

                if (stmt.executeUpdate() != 1) {
                    throw new DatabaseException("addObject failed: Could not insert object");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("addObject failed", e);
        }
    }

    public boolean removeDeltasBasedOnGame(UUID objectID) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "DELETE FROM Delta WHERE object_id=\'" + objectID + "\'";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                if (stmt.executeUpdate() != 1) {
                    throw new DatabaseException("delete deltas failed: Could not delete deltas based on object");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("delete deltas based on object failed", e);
        }
    }

    /**
     * gets all deltas from the database based on a type
     * @return
     * @throws DatabaseException
     */
    public List<MaybeTuple<Serializable, List<Serializable>>> getDeltas(ModelObjectType type) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Serializable object = null;
            List<MaybeTuple<Serializable, List<Serializable>>> deltas =
                    new LinkedList<>();
            try {
                String sql = SELECT_ALL_OBJECTS + " WHERE type=\'" + type + "\'";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                MaybeTuple objectCommands = null;
                while(rs.next())
                {
                    int objectID = rs.getInt(2);
                    object = rs.getString(3);
                    List<Serializable> deltaCommands =
                            new LinkedList<>();
                    try {
                        sql = SELECT_ALL_DELTAS + " WHERE object_id=\'" + objectID + "\'";
                        stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                        rs = stmt.executeQuery();

                        //adds the Serializable delta based on the object serializable
                        while(rs.next())
                        {
                            Serializable userID2 = rs.getString(4);
                            deltaCommands.add(userID2);
                        }
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (stmt != null) {
                            stmt.close();
                        }
                        objectCommands.set1(object);
                        objectCommands.set2(deltaCommands);
                    }

                    deltas.add(objectCommands);
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
            return deltas;
        } catch (SQLException e) {
            throw new DatabaseException("getDeltas failed", e);
        }
    }

    private static final String SELECT_ALL_DELTAS = "select * from DELTA";
    private static final String DELETE_DELTA_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS DELTA";
    private static final String CREATE_DELTA_TABLE =
            "CREATE TABLE DELTA " +
                    "(" +
                    "   hidden_id BIGINT AUTOINCREMENT NOT NULL," +
                    "   object_id BINARY(16) NOT NULL FOREIGN KEY REFERENCES Object(id)," +
                    "   order INTEGER NOT NULL," +
                    "   delta_command TEXT NOT NULL," +
                    "   PRIMARY KEY (hidden_id)" +
                    ")";

    private static final String SELECT_ALL_OBJECTS = "select * from OBJECT";
    private static final String DELETE_OBJECT_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS OBJECT";
    private static final String CREATE_OBJECT_TABLE =
            "CREATE TABLE OBJECT " +
                    "(" +
                    "   hidden_id BIGINT AUTOINCREMENT NOT NULL," +
                    "   id BINARY(16) UNIQUE NOT NULL," +
                    "   object TEXT NOT NULL," +
                    "   type TINYINT NOT NULL," +
                    "   PRIMARY KEY (hidden_id)" +
                    ")";
}
