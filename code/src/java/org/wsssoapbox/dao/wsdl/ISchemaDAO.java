package org.wsssoapbox.dao.wsdl;

import java.util.List;

import org.ow2.easywsdl.schema.api.Import;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Types;
import org.wsssoapbox.bean.model.wsdl.SchemaBean;

public interface ISchemaDAO {
	public List <SchemaBean> getSchemas(Description desc ) throws XmlException;
	public List<SchemaBean> getSchemasByType(Types type) throws XmlException;
	public SchemaBean getSchemasByImportSchema(Import im) throws XmlException;
}
