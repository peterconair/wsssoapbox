/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.logger;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.wsssoapbox.database.logger.UserLog;

/**
 *
 * @author Peter
 */
public class UserLogDataModel extends ListDataModel<UserLog> implements Serializable, SelectableDataModel<UserLog> {

   private static final long serialVersionUID = -5936009505793762561L;
   //  private UserLogService userLogService;

   public UserLogDataModel() {
   }

   public UserLogDataModel(List<UserLog> userLogs) {
      super(userLogs);
   }

   @Override
   public UserLog getRowData(String rowKey) {
      //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data           

      List<UserLog> userLogs = (List<UserLog>) getWrappedData();
      for (UserLog log : userLogs) {
         if (log.getLogId().toString().equals(rowKey)) {
            return log;
         }
      }
      return null;
   }

   @Override
   public Object getRowKey(UserLog log) {
      return log.getLogId();
   }
}
