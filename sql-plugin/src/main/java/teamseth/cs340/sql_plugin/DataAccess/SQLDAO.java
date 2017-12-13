package teamseth.cs340.sql_plugin.DataAccess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            }
        }
    }

    /**
     * @param delta Adds a serializable delta to database
     * @return
     */
    public boolean addDelta(Serializable delta, UUID objectID, int order_num) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO DELTA (object_id," +
                        "order_num,delta_command) values ( " +
                        "\"" + objectID.toString() + "\",\"" +
                        order_num + "\",\"" +
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

    public static void write(
            Object obj, PreparedStatement stmt, int i)
            throws SQLException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(baos);
        oout.writeObject(obj);
        oout.close();
        stmt.setBytes(i, baos.toByteArray());
    }
    public static Object read(ResultSet rs, String column)
            throws SQLException, IOException, ClassNotFoundException {
        byte[] buf = rs.getBytes(column);
        if (buf != null) {
            ObjectInputStream objectIn = new ObjectInputStream(
                    new ByteArrayInputStream(buf));
            return objectIn.readObject();
        }
        return null;
    }

    /**
     * @param object Adds a serializable object to database
     * @return
     */
    public boolean addObject(Serializable object, UUID objectID, ModelObjectType type) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {

                int typeIn = type.ordinal() ;
                String sql = "INSERT INTO OBJECT (id," +
                        "object,type) values ( " +
                        "\"" + objectID.toString() + "\",\"" +
                        "?" + "\",\"" +
                        typeIn + "\")";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                try {
                    write(object,stmt,2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
                String sql = "DELETE FROM Delta WHERE object_id=\'" + objectID.toString() + "\'";
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
            int typeOut = type.ordinal();
            List<MaybeTuple<Serializable, List<Serializable>>> deltas =
                    new LinkedList<>();
            try {
                String sql = SELECT_ALL_OBJECTS + " WHERE type=\'" + typeOut + "\'";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                rs = stmt.executeQuery();
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
                        MaybeTuple<Serializable,List<Serializable>> objectCommands= new MaybeTuple(object,deltaCommands);
                        deltas.add(objectCommands);
                    }
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
            return null;
        }
    }

    private static final String SELECT_ALL_DELTAS = "select * from DELTA";
    private static final String DELETE_DELTA_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS DELTA";
    private static final String CREATE_DELTA_TABLE =
    "CREATE TABLE DELTA (" +
            "                       hidden_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "                       object_id STRING NOT NULL,\n" +
            "                       order_num INTEGER NOT NULL,\n" +
            "                       delta_command TEXT NOT NULL,\n" +
            "\n" +
            "                       FOREIGN KEY (object_id) REFERENCES Object (id)\n" +
            "                    )";


    private static final String SELECT_ALL_OBJECTS = "select * from OBJECT";
    private static final String DELETE_OBJECT_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS OBJECT";
    private static final String CREATE_OBJECT_TABLE =
            "CREATE TABLE OBJECT " +
                    "(" +
                    "   hidden_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   id STRING UNIQUE NOT NULL," +
                    "   object TEXT NOT NULL," +
                    "   type TINYINT NOT NULL" +
                    ")";
}
