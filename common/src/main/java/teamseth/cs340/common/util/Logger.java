package teamseth.cs340.common.util;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class Logger {
    private static final Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    private Logger() {
    }

    private static int verbosity = 3;

    public static void setVerbosity(int newVerbsosity) {
        verbosity = newVerbsosity;
    }

    public static final void error(Object object) {
        if (verbosity >= 1) {
            System.err.println("[Error] " + object.toString());
        }
    }

    public static final void warning(Object object) {
        if (verbosity >= 2) {
            System.out.println("[Warning] " + object.toString());
        }
    }

    public static final void warn(Object object) {
        warning(object);
    }

    public static final void info(Object object) {
        if (verbosity >= 3) {
            System.out.println("[Info] " + object.toString());
        }
    }
    public static final void print(Object object) {
        if (verbosity >= 4) {
            System.out.println("[Out] " + object.toString());
        }
    }

    public static final void debug(Object object) {
        if (verbosity >= 5) {
            System.out.println("[Debug] " + object.toString());
        }
    }


    public static final void print(int priority, Object object) {
        if (verbosity >= priority) {
            System.out.println("[Out] " + object.toString());
        }
    }
}
