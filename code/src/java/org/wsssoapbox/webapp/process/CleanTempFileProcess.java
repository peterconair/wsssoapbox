/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.webapp.process;

import java.io.File;

/**
 *
 * @author Peter
 */
public interface CleanTempFileProcess {

   public boolean deleteDirectory(File dir);

   public boolean deleteFile(String fileName);
}
