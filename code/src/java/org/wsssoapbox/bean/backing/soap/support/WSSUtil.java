/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.support;

/**
 *
 * @author Peter
 */
public class WSSUtil {

   public final static String WSSE_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
   public final static String WSU_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
   String content;
 
   
   /*  
   SoapVersion soapVersion;
   public static String removeWSSOutgoing( String content, WsaContainer wsaContainer )
   {
   try
   {
   SoapVersion soapVersion = wsaContainer.getOperation().getInterface().getSoapVersion();
   //			XmlObject xmlContentObject = XmlObject.Factory.parse( content );
   XmlObject xmlContentObject = XmlUtils.createXmlObject( content );
   Element header = ( Element )SoapUtils.getHeaderElement( xmlContentObject, soapVersion, true ).getDomNode();
   
   NodeList headerProps = XmlUtils.getChildElements( header );
   for( int i = 0; i < headerProps.getLength(); i++ )
   {
   Node headerChild = headerProps.item( i );
   if( headerChild.getNamespaceURI().equals( WSSE_NAMESPACE ) )
   {
   header.removeChild( headerChild );
   }
   }
   content = xmlContentObject.xmlText();
   }
   catch( XmlException e )
   {
   SoapUI.logError( e );
   }
   return content;
   }
   
    */
}
