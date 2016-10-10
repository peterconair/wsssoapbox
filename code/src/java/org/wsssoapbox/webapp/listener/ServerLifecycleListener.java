package org.wsssoapbox.webapp.listener;

import  java.sql.DriverManager;
import  java.sql.SQLException;

import  org.apache.catalina.Lifecycle;
import  org.apache.catalina.LifecycleEvent;
import  org.apache.catalina.LifecycleListener;

public class ServerLifecycleListener implements LifecycleListener {

    public static final String DRIVER_CLASSNAME =
        "org.apache.derby.jdbc.EmbeddedDriver";

    public static final String SHUTDOWN_URL =
        "jdbc:derby:;shutdown=true";

    public static final String SHUTDOWN_MESSAGE =
        "Derby database system shutdown.";

    public static final String MSG_INIT_SUCCESS =
        "Derby JDBC driver loaded successfully";

    public static final String MSG_INIT_CLASS_NOT_FOUND =
        "The Derby JDBC driver (" +
        DRIVER_CLASSNAME +
        ") could not be found.  Make sure the appropriate JAR" +
        " files are available.";

    public static final String MSG_TERM_SUCCESS =
        "Derby shutdown was successful.";

    public static final String MSG_TERM_FAILURE =
        "Unexpected Exception was caught from the Derby shutdown.";


   @Override
    public void lifecycleEvent(LifecycleEvent argEvent) {
        if (argEvent.getType().equals(Lifecycle.START_EVENT)) {
            this.initializeCloudscape();
        } else if (argEvent.getType().equals(Lifecycle.STOP_EVENT)) {
            this.shutdownCloudscape();
        }
    }

    protected void initializeCloudscape() {
        try {
            Class.forName(DRIVER_CLASSNAME);
            System.out.println(MSG_INIT_SUCCESS);
        } catch (ClassNotFoundException varException) {
            System.out.println(MSG_INIT_CLASS_NOT_FOUND);
            varException.printStackTrace();
        }
    }

    protected void shutdownCloudscape() {
        try {
            DriverManager.getConnection(SHUTDOWN_URL);
        } catch (SQLException varException) {
            if (varException.getMessage().equals(SHUTDOWN_MESSAGE)) {
                System.out.println(MSG_TERM_SUCCESS);
            } else {
                System.out.println(MSG_TERM_FAILURE);
                varException.printStackTrace();
            }
        }
    }
}
