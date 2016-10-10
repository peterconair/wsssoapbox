/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.service.installer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.web.installer.Database;
import org.wsssoapbox.web.installer.util.ScriptRunner;

/**
 *
 * @author Peter
 */
public class InstallerServiceImpl implements InstallerService {

    private static final Logger logger = LoggerFactory.getLogger(InstallerServiceImpl.class);

    public void install(Database database, String dbFile) throws Exception {

        //  String url="jdbc:mysql://localhost/mybase?user=myuser&password=mypassword";
        // String driver = "com.mysql.jdbc.Driver";
        String driver = "org.gjt.mm.mysql.Driver";
        Class.forName(driver);
        //  String url = "jdbc:mysql://" + database.getDbServer() + ":" + database.getPort() + "/" + database.getDbName();
        String url = "jdbc:mysql://" + database.getDbServer() + ":" + database.getPort() + "/";

        logger.info("Driver : " + driver);
        logger.info("Server : " + database.getDbServer());
        logger.info("Port : " + database.getPort());
        logger.info("Database Name : " + database.getDbName());
        logger.info("User : " + database.getUsername());
        logger.info("Password : " + database.getPassword());
        logger.info("url : " + url);
        Connection con = DriverManager.getConnection(url, database.getUsername(), database.getPassword());

        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String webInfoPath = ctx.getRealPath("/WEB-INF/");
        String file = webInfoPath + "/" + dbFile;

        ScriptRunner runner = new ScriptRunner(con, false, true);

        runner.runScript(new BufferedReader(new FileReader(file)));
        logger.info("Database created.");

        con.close();

    }
}
