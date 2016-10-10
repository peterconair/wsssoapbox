/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Peter
 */
public class DriverManager {
   String userName;
   String password;
   String dbms;
   String serverName;
   String portNumber;
   String dbName;
   
public Connection getConnection()
    throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);

    if (this.dbms.equals("mysql")) {
        conn = java.sql.DriverManager.getConnection("jdbc:" + this.dbms + "://" +this.serverName +":" + this.portNumber + "/",connectionProps);
    } else if (this.dbms.equals("derby")) {
        conn = java.sql.DriverManager.getConnection("jdbc:" + this.dbms + ":" +this.dbName +";create=true",connectionProps);
    }
    System.out.println("Connected to database");
    return conn;
}
}
