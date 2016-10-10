package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;


import org.ow2.easywsdl.schema.api.Import;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Types;

import org.wsssoapbox.bean.model.wsdl.ImportBean;
import org.wsssoapbox.bean.model.wsdl.SchemaBean;

public class ImportSchemaDAO implements IImportSchemaDAO {

	public List<ImportBean> getImportSchemaByType(Types type) throws XmlException {
		List <Import> importList = new ArrayList<Import>();
		List <ImportBean> importSchemaBeanList = new ArrayList<ImportBean>();
		importList = type.getImportedSchemas();
			for(Import im : importList){
				ImportBean importSchemaBean = new ImportBean();
				importSchemaBean.setDocumentation(im.getDocumentation().getContent());
				importSchemaBean.setLocationURI(im.getLocationURI().toString());
				importSchemaBean.setNamespaceURI(im.getNamespaceURI());
				importSchemaBean.setOtherAttributes(im.getOtherAttributes());
				
				//importSchemaBean.setOtherElements(im.getOtherElements());
				//importSchemaBean.setDescriptionBean(XXXXXXX);
				
				
				// get Schema (One Import have one Schema)
				ISchemaDAO schemaDAO = new SchemaDAO();
				SchemaBean schemaBean = new SchemaBean();
				schemaBean = schemaDAO.getSchemasByImportSchema(im);			
				importSchemaBean.setSchema(schemaBean);
				
			}
		
		return importSchemaBeanList;
	}



}
