package org.wsssoapbox.dao.wsdl;

import java.util.List;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public interface IWSDLDAO {

	public DescriptionBean getWSDLDocument(Description desc) throws XmlException;

}
