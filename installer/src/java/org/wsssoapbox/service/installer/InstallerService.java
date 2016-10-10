/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.service.installer;

import org.wsssoapbox.web.installer.Database;

/**
 *
 * @author Peter
 */
public interface InstallerService {
    public void install(Database database,String dbFile) throws Exception ;
}
