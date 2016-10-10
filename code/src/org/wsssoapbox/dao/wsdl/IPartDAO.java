package org.wsssoapbox.dao.wsdl;

import java.util.List;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Output;
import org.wsssoapbox.bean.model.wsdl.PartBean;

public interface IPartDAO {
	public List<PartBean> getPartsByInput(Input input) throws XmlException;

	public List<PartBean>  getPartsByOuput(Output output) throws XmlException;

	public List<PartBean> getPartsByFault(Fault fault) throws XmlException;
}
