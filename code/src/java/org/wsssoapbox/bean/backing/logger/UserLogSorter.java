/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.logger;

import java.util.Comparator;

import org.primefaces.model.SortOrder;
import org.wsssoapbox.database.logger.UserLog;

public class UserLogSorter implements Comparator<UserLog> {

    private String sortField;    
    private SortOrder sortOrder;
    
    public UserLogSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

   @Override
    public int compare(UserLog log1, UserLog log2) {
        try {
            Object value1 = UserLog.class.getField(this.sortField).get(log1);
            Object value2 = UserLog.class.getField(this.sortField).get(log2);

            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
