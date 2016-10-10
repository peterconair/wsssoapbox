package org.wsssoapbox.dao.wsdl;

import java.util.List;

import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Output;
import org.ow2.easywsdl.wsdl.api.Part;
import org.wsssoapbox.bean.model.wsdl.ElementBean;

public interface IElementDAO {
	public ElementBean getElementByInput(Input input ) throws XmlException;

	public ElementBean getElementByPart(Part part) throws XmlException;

	public ElementBean getElementByOutput(Output output) throws XmlException;

	public ElementBean getElementByFault(Fault fault) throws XmlException;

	public List<ElementBean> getElementsBySchema(Schema s) throws XmlException;
}
