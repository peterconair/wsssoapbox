/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.derby.test;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "derbyForm")
@SessionScoped
public class DerbyForm implements Serializable {

   private static final long serialVersionUID = -1092356995113836708L;
   private User user;
   private List<User> users;
   private String framework = "embedded";
   private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
   private String protocol = "jdbc:derby:";
   String dbName = "wsssoapboxDB";
   private DataSource ds;

   public DerbyForm() {
   }

   public void getAllTable(Connection conn) {
      try {
         // Create a result set
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM location");

         // Get result set meta data
         ResultSetMetaData rsmd = rs.getMetaData();
         int numColumns = rsmd.getColumnCount();

         // Get the column names; column indices start from 1
         for (int i = 1; i < numColumns + 1; i++) {
            String columnName = rsmd.getColumnName(i);
            // Get the name of the column's table name
            String tableName = rsmd.getTableName(i);

            System.out.println("Table name :" + tableName);
            System.out.println("Column name :" + columnName);
         }
      } catch (SQLException e) {
      }finally {
         if(conn!=null){
            try {
               conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         }
      }
   }

   public void connectDataSource() {

      /**/
      try {
      Context ctx = new InitialContext();
      ds = (DataSource) ctx.lookup("java:comp/env/jdbc/wsssoapboxDS");
      
      } catch (NamingException ex) {
      System.out.println("NamingException : " + ex.getMessage());
      ex.printStackTrace();
      } catch (Exception ex) {
      System.out.println("Exception : " + ex.getMessage());
      ex.printStackTrace();
      }
       

      try {

         Connection conn = ds.getConnection();

         getAllTable(conn);

         /*
         PreparedStatement psInsert = null;
         Statement stmt = conn.createStatement();
         
         stmt = conn.createStatement();
         
         
         psInsert = conn.prepareStatement("insert into location values (?, ?)");
         psInsert.setInt(3, 2011);
         psInsert.setString(4, "Sirnakarin Rd.");
         psInsert.execute();
         
         stmt.execute("select * from location");
         ResultSet rs = stmt.executeQuery("SELECT num, addr FROM location ORDER BY num");
         
         while (rs.next()) {
         System.out.println(" ID : " + rs.getInt(1));
         }
          * 
          */

      } catch (SQLException ex) {
         System.out.println("SQLException : " + ex.getMessage());
         ex.printStackTrace();
      } catch (Exception ex) {
         System.out.println("Exception : " + ex.getMessage());
         ex.printStackTrace();
      }
   }

   private void loadDriver() {
      try {
         Class.forName(driver).newInstance();
         System.out.println("Loaded the appropriate driver");
      } catch (ClassNotFoundException cnfe) {
         System.err.println("\nUnable to load the JDBC driver " + driver);
         System.err.println("Please check your CLASSPATH.");
         cnfe.printStackTrace(System.err);
      } catch (InstantiationException ie) {
         System.err.println(
                 "\nUnable to instantiate the JDBC driver " + driver);
         ie.printStackTrace(System.err);
      } catch (IllegalAccessException iae) {
         System.err.println(
                 "\nNot allowed to access the JDBC driver " + driver);
         iae.printStackTrace(System.err);
      }
   }

   public void connectDB() {


      Properties props = new Properties();
      String path = "F:\\Develope\\Server\\Apache Tomcat 7.0.25\\Databases";
      System.setProperty("derby.system.home", path);
      props.put("user", "root");
      props.put("password", "1234");


      System.out.println("user.dir :" + System.getProperties().getProperty("user.dir"));
      System.out.println("derby.system.home :" + System.getProperties().getProperty("derby.system.home"));

      System.out.println("SimpleApp starting in " + framework + " mode");

      /* load the desired JDBC driver */
      loadDriver();

      Connection conn = null;
      ArrayList statements = new ArrayList(); // list of Statements, PreparedStatements
      PreparedStatement psInsert = null;
      PreparedStatement psUpdate = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {

         System.out.println("Connection String :  ");

         conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);

         System.out.println("Connected to and created database " + dbName);

         conn.setAutoCommit(false);

         stmt = conn.createStatement();
         statements.add(stmt);

         // We create a table...
         stmt.execute("create table location(num int, addr varchar(40))");
         System.out.println("Created table location");

         psInsert = conn.prepareStatement("insert into location values (?, ?)");
         statements.add(psInsert);

         psInsert.setInt(1, 1956);
         psInsert.setString(2, "Webster St.");
         psInsert.executeUpdate();
         System.out.println("Inserted 1956 Webster");

         psInsert.setInt(1, 1910);
         psInsert.setString(2, "Union St.");
         psInsert.executeUpdate();
         System.out.println("Inserted 1910 Union");
         psUpdate = conn.prepareStatement("update location set num=?, addr=? where num=?");
         statements.add(psUpdate);

         psUpdate.setInt(1, 180);
         psUpdate.setString(2, "Grand Ave.");
         psUpdate.setInt(3, 1956);
         psUpdate.executeUpdate();
         System.out.println("Updated 1956 Webster to 180 Grand");

         psUpdate.setInt(1, 300);
         psUpdate.setString(2, "Lakeshore Ave.");
         psUpdate.setInt(3, 180);
         psUpdate.executeUpdate();
         System.out.println("Updated 180 Grand to 300 Lakeshore");

         rs = stmt.executeQuery("SELECT num, addr FROM location ORDER BY num");

         int number; // street number retrieved from the database
         boolean failure = false;
         if (!rs.next()) {
            failure = true;
            reportFailure("No rows in ResultSet");
         }

         if ((number = rs.getInt(1)) != 300) {
            failure = true;
            reportFailure(
                    "Wrong row returned, expected num=300, got " + number);
         }

         if (!rs.next()) {
            failure = true;
            reportFailure("Too few rows");
         }

         if ((number = rs.getInt(1)) != 1910) {
            failure = true;
            reportFailure(
                    "Wrong row returned, expected num=1910, got " + number);
         }

         if (rs.next()) {
            failure = true;
            reportFailure("Too many rows");
         }

         if (!failure) {
            System.out.println("Verified the rows");
         }

         // delete the table
         //   s.execute("drop table location");
         //   System.out.println("Dropped table location");


         conn.commit();
         System.out.println("Committed the transaction");

         if (framework.equals("embedded")) {
            try {
               // the shutdown=true attribute shuts down Derby
               DriverManager.getConnection("jdbc:derby:;shutdown=true");

            } catch (SQLException se) {
               if (((se.getErrorCode() == 50000)
                       && ("XJ015".equals(se.getSQLState())))) {
                  // we got the expected exception
                  System.out.println("Derby shut down normally");
                  // Note that for single database shutdown, the expected
                  // SQL state is "08006", and the error code is 45000.
               } else {
                  // if the error code or SQLState is different, we have
                  // an unexpected exception (shutdown failed)
                  System.err.println("Derby did not shut down normally");
                  printSQLException(se);
               }
            }
         }
      } catch (SQLException sqle) {
         printSQLException(sqle);
      } finally {
         // release all open resources to avoid unnecessary memory usage

         // ResultSet
         try {
            if (rs != null) {
               rs.close();
               rs = null;
            }
         } catch (SQLException sqle) {
            printSQLException(sqle);
         }

         // Statements and PreparedStatements
         int i = 0;
         while (!statements.isEmpty()) {
            // PreparedStatement extend Statement
            Statement st = (Statement) statements.remove(i);
            try {
               if (st != null) {
                  st.close();
                  st = null;
               }
            } catch (SQLException sqle) {
               printSQLException(sqle);
            }
         }

         //Connection
         try {
            if (conn != null) {
               conn.close();
               conn = null;
            }
         } catch (SQLException sqle) {
            printSQLException(sqle);
         }
      }
   }

   public static void printSQLException(SQLException e) {
      // Unwraps the entire exception chain to unveil the real cause of the
      // Exception.
      while (e != null) {
         System.err.println("\n----- SQLException -----");
         System.err.println("  SQL State:  " + e.getSQLState());
         System.err.println("  Error Code: " + e.getErrorCode());
         System.err.println("  Message:    " + e.getMessage());
         // for stack traces, refer to derby.log or uncomment this:
         //e.printStackTrace(System.err);
         e = e.getNextException();
      }
   }

   private void reportFailure(String message) {
      System.err.println("\nData verification failed:");
      System.err.println('\t' + message);
   }

   public class User {

      int id;
      String name;
   }

   public class Location {
   }
}
