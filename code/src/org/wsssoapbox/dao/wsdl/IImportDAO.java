package org.wsssoapbox.dao.wsdl;

import java.util.List;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.ImportBean;

public interface IImportDAO {
	List<ImportBean> getImportByDescription(Description desc) throws XmlException;
}
