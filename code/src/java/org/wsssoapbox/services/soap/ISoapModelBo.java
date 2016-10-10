/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.soap;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;

/**
 *
 * @author Peter
 */
public interface ISoapModelBo {
  
   public SoapBindingBean createSoapBindingBean(Description desc, QName bindingQName, QName interfaceQName,
           QName operationQName) throws XmlException;
}
