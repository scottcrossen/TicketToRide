package teamseth.cs340.common.util;

import java.io.*;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Serializer {
    private static Serializer instance = null;

    protected Serializer(){}

    public static Serializer getInstance() {
        if (instance == null) {
            instance = new Serializer();
        }
        return instance;
    }

    public static void write(OutputStream os, Serializable object) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(object);
        out.flush();
    }

    public static Object read(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(is);
        Object object = in.readObject();
        in.close();
        return object;
    }
}
