/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.view.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 *
 * @author Peter
 */
public interface ResourceLoader {

   public abstract URL getResource(String s)
           throws MalformedURLException;
   
   public abstract Set getResourcePaths(String s);
}
