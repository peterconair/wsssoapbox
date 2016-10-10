/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.web.installer;

import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.service.installer.InstallerService;
import org.wsssoapbox.service.installer.InstallerServiceImpl;

@ManagedBean(name = "installer")
@ApplicationScoped
public class Installer {
    
    private static final Logger logger = LoggerFactory.getLogger(Installer.class);
    private Database database;
    private boolean isDBCreated;
    private static String dbFile = "wsssoapbox.sql";
    
    public Installer() {
        database = new Database();
    }
    
    public void installDB() {
        try {
            InstallerService installer = new InstallerServiceImpl();
            installer.install(database, dbFile);
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Database created.", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            isDBCreated = true;
            
        } catch (ClassNotFoundException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        } catch (SQLException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    /**
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * @return the isDBCreated
     */
    public boolean isIsDBCreated() {
        return isDBCreated;
    }

    /**
     * @param isDBCreated the isDBCreated to set
     */
    public void setIsDBCreated(boolean isDBCreated) {
        this.isDBCreated = isDBCreated;
    }
}
