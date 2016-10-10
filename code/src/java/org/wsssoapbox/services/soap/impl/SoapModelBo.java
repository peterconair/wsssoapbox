/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.soap.impl;

import javax.xml.namespace.QName;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;
import org.wsssoapbox.services.soap.ISoapModelBo;
import org.wsssoapbox.dao.soap.ISoapModelDAO;
import org.wsssoapbox.dao.soap.SoapModelDAO;

/**
 *
 * @author Peter
 */
public class SoapModelBo implements ISoapModelBo {

   @Override
   public SoapBindingBean createSoapBindingBean(Description desc, QName bindingQName, QName interfaceQName, QName operationQName) throws XmlException {

      ISoapModelDAO soapModelDAO = new SoapModelDAO();
      String soapVesion = desc.getBinding(bindingQName).getVersion();
      String optName = operationQName.getLocalPart();
      String docStyle = desc.getBinding(bindingQName).getBindingOperation(optName).getStyle().toString();
      if (docStyle.equalsIgnoreCase("DOCUMENT")) {
         return soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);
      } else if (docStyle.equalsIgnoreCase("RPC")) {
         if (soapVesion.equals("1.2")) {
            return soapModelDAO.createSoapBindingBeanRPC(desc, bindingQName, interfaceQName, operationQName);
         } else {
            return soapModelDAO.createSoapBindingBeanRPC(desc, bindingQName, interfaceQName, operationQName);
         }
      } else {
         return null;
      }
   }
}
