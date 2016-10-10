/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.view.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import javax.servlet.ServletContext;

/**
 *
 * @author Peter
 */
public class ViewResourceLoader implements ResourceLoader{
  private final ServletContext context;
  
   public ViewResourceLoader(ServletContext context)
    {
        this.context = context;
    }

   @Override
    public URL getResource(String path)throws MalformedURLException
    {
        return context.getResource(path);
    }

    public URL getWSDLViewerXSLFile() throws MalformedURLException
    {
        return getResource("/resources/xslt/wsdl-viewer.xsl");
    }

   @Override
   public Set getResourcePaths(String path) {
      return context.getResourcePaths(path);
   }
   
}
