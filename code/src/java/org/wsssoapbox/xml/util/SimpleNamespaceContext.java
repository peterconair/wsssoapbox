/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.xml.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

/**
 *
 * @author Peter
 */
public class SimpleNamespaceContext implements NamespaceContext {

   private Map<String, String> prefixMap = new HashMap<String, String>();

   public SimpleNamespaceContext() {
      prefixMap.put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
      prefixMap.put("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
      prefixMap.put("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
      prefixMap.put("xenc", "http://www.w3.org/2001/04/xmlenc#");
      prefixMap.put("ds", "http://www.w3.org/2000/09/xmldsig#");
   }

   public SimpleNamespaceContext(Map<String, String> prefMap) {
      prefixMap.putAll(prefMap);
   }

   @Override
   public String getNamespaceURI(String prefix) {
      return prefixMap.get(prefix);
   }

   @Override
   public String getPrefix(String uri) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Iterator getPrefixes(String uri) {
      throw new UnsupportedOperationException();
   }
}
