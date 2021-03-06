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
            return false;
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
            return false;
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
                String sql = "INSERT INTO DELTA (object_id, order_num, delta_command) VALUES (?, ?, ?)";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                try {
                    stmt.setString(1, objectID.toString());
                    stmt.setInt(2, order_num);
                    write(delta,stmt,3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
            Serializable obj, PreparedStatement stmt, int i)
            throws SQLException, IOException {
        //Blob newData = new SerialBlob();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(baos);
        oout.writeObject(obj);
        oout.flush();
        oout.close();
        byte[] output = baos.toByteArray();
        try {
            stmt.setBytes(i, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Serializable read(ResultSet rs, int column)
            throws SQLException, IOException, ClassNotFoundException {
        byte[] buf = rs.getBytes(column);
        if (buf != null) {
            ObjectInputStream objectIn = new ObjectInputStream(
                    new ByteArrayInputStream(buf));
            return (Serializable) objectIn.readObject();
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
                String sql = "INSERT INTO OBJECT (id, object, type) VALUES (?, ?, ?)";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                try {
                    stmt.setString(1, objectID.toString());
                    write(object,stmt, 2);
                    stmt.setInt(3, typeIn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (stmt.executeUpdate() != 1) {
                        throw new DatabaseException("addObject failed: Could not insert object");
                    }
                } catch (Exception e) {
                    removeObject(objectID);
                    stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                    try {
                        stmt.setString(1, objectID.toString());
                        write(object,stmt, 2);
                        stmt.setInt(3, typeIn);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    if (stmt.executeUpdate() != 1) {
                        throw new DatabaseException("addObject failed: Could not insert object");
                    }
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
                    return true;
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

    public boolean removeObject(UUID objectID) throws DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "DELETE FROM OBJECT WHERE id=\'" + objectID.toString() + "\'";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                if (stmt.executeUpdate() != 1) {
                    return true;
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("delete object based on id failed", e);
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
            Serializable object = null;
            int typeOut = type.ordinal();
            ResultSet rs1 = null;
            List<MaybeTuple<Serializable, List<Serializable>>> deltas =
                    new LinkedList<>();
            try {
                String sql = SELECT_ALL_OBJECTS + " WHERE type=\'" + typeOut + "\'";
                stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                rs1 = stmt.executeQuery();
                while(rs1.next())
                {
                    String objectID = rs1.getString(2);
                    try {
                        object = read(rs1, 3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    List<Serializable> deltaCommands =
                            new LinkedList<>();
                    ResultSet rs2 = null;
                    try {
                        sql = SELECT_ALL_DELTAS + " WHERE object_id=\'" + objectID + "\'";
                        stmt = Connection.SINGLETON.conn.prepareStatement(sql);
                        rs2 = stmt.executeQuery();

                        //adds the Serializable delta based on the object serializable
                        while(rs2.next())
                        {
                            Serializable userID2 = null;
                            try {
                                userID2 = read(rs2,4);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            deltaCommands.add(userID2);
                        }
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (stmt != null) {
                            stmt.close();
                        }
                        MaybeTuple<Serializable,List<Serializable>> objectCommands= new MaybeTuple(object,deltaCommands);
                        deltas.add(objectCommands);
                    }
                }
            } finally {
                if (rs1 != null) {
                    rs1.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
            return deltas;
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }

    private static final String SELECT_ALL_DELTAS = "SELECT * from DELTA";
    private static final String DELETE_DELTA_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS DELTA";
    private static final String CREATE_DELTA_TABLE =
    "CREATE TABLE DELTA (" +
            "                       hidden_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "                       object_id STRING NOT NULL,\n" +
            "                       order_num INTEGER NOT NULL,\n" +
            "                       delta_command BLOB NOT NULL,\n" +
            "\n" +
            "                       FOREIGN KEY (object_id) REFERENCES Object (id)\n" +
            "                    )";


    private static final String SELECT_ALL_OBJECTS = "select * from OBJECT";
    private static final String DELETE_OBJECT_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS OBJECT";
    private static final String CREATE_OBJECT_TABLE =
            "CREATE TABLE OBJECT " +
                    "(" +
                    "   hidden_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "   id STRING UNIQUE NOT NULL,\n" +
                    "   object BLOB NOT NULL,\n" +
                    "   type TINYINT NOT NULL\n" +
                    ")";
}
