package org.wsssoapbox.dao.wsdl;

import java.util.List;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.PartBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public interface ITypeDAO {
	public TypeBean getTypes(Description desc) throws WSDLException ,XmlException;
	public TypeBean getTypeByElement(Element elememt) throws XmlException;
	public TypeBean getTypeByPart(Part part) throws XmlException;
	public List<TypeBean> getTypeBySchema(Schema s) throws XmlException;

}
