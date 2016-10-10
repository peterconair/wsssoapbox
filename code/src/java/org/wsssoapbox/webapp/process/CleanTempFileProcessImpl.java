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
public class CleanTempFileProcessImpl implements CleanTempFileProcess {

   private static final Logger logger = LoggerFactory.getLogger(CleanTempFileProcessImpl.class);


   //  deleteDir(new File("/usr/tmp"));
   @Override
   public  boolean deleteDirectory(File dir) {
      if (dir.exists()) {
         File[] files = dir.listFiles();
         for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
               deleteDirectory(files[i]);
            } else {
               files[i].delete();
            }
         }
      }
      return (dir.delete());
   
   }

   @Override
   public boolean deleteFile(String fileName) {
      File file = new File(fileName);
      boolean success = file.delete();
      if (!success) {
         logger.info("Deletion of " + fileName + " failed.");
      } else {
         logger.info(fileName + " File deleted");
      }
      return success;
   }
}
