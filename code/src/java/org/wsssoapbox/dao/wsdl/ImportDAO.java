package org.wsssoapbox.dao.wsdl;
import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Import;
import org.wsssoapbox.bean.model.wsdl.ImportBean;
import org.wsssoapbox.bean.model.wsdl.SchemaBean;
public class ImportDAO implements IImportDAO{

	@Override
	public List<ImportBean> getImportByDescription(Description desc) throws XmlException {
		List <Import> importList = new ArrayList<Import>();
		List <ImportBean> importSchemaBeanList = new ArrayList<ImportBean>();
		importList = desc.getImports();
			for(Import im : importList){
				ImportBean importSchemaBean = new ImportBean();
				
				importSchemaBean.setDocumentation(im.getDocumentation().getContent());
				importSchemaBean.setLocationURI(im.getLocationURI().toString());
				importSchemaBean.setNamespaceURI(im.getNamespaceURI());
				importSchemaBean.setOtherAttributes(im.getOtherAttributes());				
				importSchemaBean.setOtherElements(im.getOtherElements());
				
			}
		
		return importSchemaBeanList;
	}

}
