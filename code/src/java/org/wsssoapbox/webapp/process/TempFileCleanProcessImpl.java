/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.webapp.process;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter
 */
public class TempFileCleanProcessImpl implements CleanProcess {

   private static final Logger logger = LoggerFactory.getLogger(TempFileCleanProcessImpl.class);
   private String id;
   String userDir;

   public TempFileCleanProcessImpl(String id, String userDir) {
      this.id = id;
      this.userDir = userDir;
   }

   @Override
   public void doClean() {

      try {
         CleanTempFileProcess cleanner = new CleanTempFileProcessImpl();
         logger.debug("Cleaning " + userDir + " are processing.........");
         File dir = new File(userDir);
         cleanner.deleteDirectory(dir);
         logger.debug("Clean are finished.");
      } catch (Exception ex) {
         ex.printStackTrace();
      }

   }
}