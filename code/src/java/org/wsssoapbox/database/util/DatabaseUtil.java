/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Peter
 */
public class DatabaseUtil {
   public static String hash(String alg , String string){
        try {
            //Create MessageDigest and encoding for input String
            MessageDigest digest = MessageDigest.getInstance(alg);
            byte[] hash = digest.digest(string.getBytes("UTF-8"));
 
            //Hash the Input String
            StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hash.length; i++) {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
                return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        return null;
    }
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) throws ClassCastException{
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
          r.add(clazz.cast(o));
        return r;
    }
}
