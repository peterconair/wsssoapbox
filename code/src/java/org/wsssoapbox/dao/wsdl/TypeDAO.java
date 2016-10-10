package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.wsssoapbox.bean.model.wsdl.ImportBean;
import org.wsssoapbox.bean.model.wsdl.PartBean;
import org.wsssoapbox.bean.model.wsdl.SchemaBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public class TypeDAO implements ITypeDAO {
	private TypeBean typeBean;

	public TypeBean getTypes(Description desc) throws WSDLException, XmlException {
		Types type = desc.getTypes();

		typeBean = new TypeBean();

		IImportSchemaDAO importSchemaDAO = new ImportSchemaDAO();
		List<ImportBean> importSchemaBeanList = new ArrayList<ImportBean>();
		importSchemaBeanList = importSchemaDAO.getImportSchemaByType(type);

		typeBean.setOtherAttributes(type.getOtherAttributes());
		typeBean.setOtherElements(type.getOtherElements());

		typeBean.setImportSchemas(type.getImportedSchemas());

		// Set Schema (One Type have many Schema)
		ISchemaDAO schemaDAO = new SchemaDAO();
		List<SchemaBean> schemaBeanList = new ArrayList<SchemaBean>();
		schemaBeanList = schemaDAO.getSchemas(desc);
		typeBean.setSchemas(schemaBeanList);

		if (type.getDocumentation() != null)
			typeBean.setDocumentation(type.getDocumentation().getContent());

		return typeBean;
	}

	public TypeBean getTypeByElement(Element elememt) throws XmlException {
		typeBean = new TypeBean();

		Type types = elememt.getType();
		//	Type type = elememt.getType();

		//	typeBean.setImportSchemas(types.getImportedSchemas());
		//	typeBean.setOtherAttributes(type.getOtherAttributes());
		//	typeBean.setOtherElements(type.getOtherElements());

		ISchemaDAO schemaDAO = new SchemaDAO();
		List<SchemaBean> schemaBeanList = new ArrayList<SchemaBean>();

		//	schemaBeanList = schemaDAO.getSchemasByType(type);	
		//	typeBean.setSchemas(schemaBeanList);

		//		typeBean.setDocumentation(type.getDocumentation().getContent());

		return typeBean;
	}

	public TypeBean getTypeByPart(Part part) throws XmlException {
		typeBean = new TypeBean();

		Type type = part.getType();
		//	typeBean.setName(type.getClass().toString());
		//	typeBean.setImportSchemas(type.getImportedSchemas());
		//	typeBean.setOtherAttributes(type.getOtherAttributes());
		//	typeBean.setOtherElements(type.getOtherElements());

		ISchemaDAO schemaDAO = new SchemaDAO();
		List<SchemaBean> schemaBeanList = new ArrayList<SchemaBean>();

		//	schemaBeanList = schemaDAO.getSchemasByType(type);	
		typeBean.setSchemas(schemaBeanList);

		//	typeBean.setDocumentation(type.getDocumentation().getContent());

		return typeBean;
	}

	public List<TypeBean> getTypeBySchema(Schema s) throws XmlException {
		List<Type> typeList = new ArrayList<Type>();
		List<TypeBean> typeBeanList = new ArrayList<TypeBean>();
		typeList = s.getTypes();

		for (Type type : typeList) {
			typeBean = new TypeBean();
			typeBean.setOtherAttributes(type.getOtherAttributes());

			// Set Schema (One Type have many Schema)

			/*
			 * ISchemaDAO schemaDAO = new SchemaDAO(); List<SchemaBean>
			 * schemaBeanList = new ArrayList<SchemaBean>(); schemaBeanList =
			 * schemaDAO.getSchemas(desc); typeBean.setSchemas(schemaBeanList);
			 */
			if (type.getDocumentation() != null)
				typeBean.setDocumentation(type.getDocumentation().getContent());
			typeBeanList.add(typeBean);
		}
		return typeBeanList;
	}

	
}
