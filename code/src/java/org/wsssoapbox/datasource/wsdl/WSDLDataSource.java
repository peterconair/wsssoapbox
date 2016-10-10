package org.wsssoapbox.datasource.wsdl;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import org.slf4j.Logger;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "wsdlDataSource")
//@SessionScoped
public class WSDLDataSource implements Serializable {

   private static final long serialVersionUID = 1L;
   private static Description desc;
   private static WSDLReader reader;
   private static final Logger logger = LoggerFactory.getLogger(WSDLDataSource.class);

   public WSDLDataSource(String wsdlURL) throws WSDLException, MalformedURLException, URISyntaxException, IOException,
           URISyntaxException {
   }

   public static void initail(String wsdlURL) throws WSDLException, MalformedURLException, IOException,
           URISyntaxException {
      logger.debug(">>>>>>>>>>>>>>>> Start private void initail(String wsdlURL) ");

      reader = WSDLFactory.newInstance().newWSDLReader();
      desc = reader.read(new URL(wsdlURL));

      logger.debug(">>>>>>>>>>>>>>>>>> End private void initail(String wsdlURL) <<<<<<<<<<<<<<<<");
   }

   public static Description getDesc(String wsdlURL) throws WSDLException, MalformedURLException, IOException, URISyntaxException {
      initail(wsdlURL);
      return desc;
   }

   public WSDLReader getReader() {
      return reader;
   }
}
