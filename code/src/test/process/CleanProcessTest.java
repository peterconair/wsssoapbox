/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.File;
import org.wsssoapbox.webapp.process.CleanTempFileProcess;
import org.wsssoapbox.webapp.process.CleanTempFileProcessImpl;

/**
 *
 * @author Peter
 */
public class CleanProcessTest {

   public static void main(String[] args) {
      String dir = "F:\\Develope\\sourecode\\netbeans\\J2EE\\WS-SSOAPBox\\web\\temp\\5F950B0FE9D2CBB6040FF51F6C67FAA8";

      //deleteDirectory(new File(dir));
      // System.out.println("Finish ! = " + deleteDirectory(new File(dir)));
      CleanProcessTest.doClean();
   }

   public static void doClean() {

      try {
         String sessionId = "F800885C935B2F74F76FBFB3966FF761";
         CleanTempFileProcess cleanner = new CleanTempFileProcessImpl();
         String path = "F:\\Develope\\sourecode\\netbeans\\J2EE\\WS-SSOAPBox\\web\\temp\\" + sessionId;
         System.out.println("Cleaning " + path + " are processing.........");
         String userDir = path;
         File dir = new File(userDir);
         cleanner.deleteDirectory(dir);
         System.out.println("Clean are finished.");
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private static boolean deleteDir(File dir) {
      boolean success = false;
      if (dir.isDirectory()) {
         String[] subDirs = dir.list();
         for (int i = 0; i < subDirs.length; i++) {
            //Call Recursive for delete sub dirs
            success = deleteDir(new File(dir, subDirs[i]));
         }
      }
      //Delete main dir
      dir.delete();
      return success;
   }

   static public boolean deleteDirectory(File path) {
      if (path.exists()) {
         File[] files = path.listFiles();
         for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
               deleteDirectory(files[i]);
            } else {
               files[i].delete();
            }
         }
      }
      return (path.delete());
   }
}
