/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 *
 * @author Peter
 */
public class TestLog2DB {

   private static final Logger logger = LoggerFactory.getLogger(TestLog2DB.class);

   public static void main(String[] args) {
      MDC.put("user", "1");
      logger.info("test message {}","peter");
      
      try{
          int a = 1/0 ;
      }catch(Exception ex){
         logger.error("Cannot divide by zero", ex);
      }
     
      
      MDC.remove("user");
   }
}