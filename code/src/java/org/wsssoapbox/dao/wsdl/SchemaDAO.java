package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.ow2.easywsdl.schema.api.Import;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Types;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.bean.model.wsdl.SchemaBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public class SchemaDAO implements ISchemaDAO {
	 private static final Logger logger=LoggerFactory.getLogger(SchemaDAO.class);

	public List<SchemaBean> getSchemas(Description desc) throws XmlException {
		logger.debug("start public List<SchemaBean> getSchemas(Description desc)");
		List<Schema> schemaList = desc.getTypes().getSchemas();
		List<SchemaBean> schemaBeanList = new ArrayList<SchemaBean>();
		SchemaBean schemaBean = new SchemaBean();
		for (Schema s : schemaList) {
			schemaBean.setAllNamespaces(s.getAllNamespaces());
			schemaBean.setAttributeFormDefault(s.getAttributeFormDefault());
			schemaBean.setAttributes(s.getAttributes());
			schemaBean.setBlockDefault(s.getBlockDefault());
			schemaBean.setDocumentURI(s.getDocumentURI());
			schemaBean.setElementFormDefault(s.getElementFormDefault());
			schemaBean.setFinalDefault(s.getFinalDefault());
			schemaBean.setImports(s.getImports());
			schemaBean.setIncludes(s.getIncludes());
			schemaBean.setLang(s.getLang());
			schemaBean.setOtherAttributes(s.getOtherAttributes());
			schemaBean.setTargetNamespace(s.getTargetNamespace());
			schemaBean.setVersion(s.getVersion());

			// Set All Type to Schema (One Schema have many Element)
			ITypeDAO typeDAO = new TypeDAO();
			List<TypeBean> typeBeanList = typeDAO.getTypeBySchema(s);
			schemaBean.setTypes(typeBeanList);

			// Set All Element to Schema (One Schema have many Element)
			IElementDAO elementDAO = new ElementDAO();
			List<ElementBean> elementBeanList = new ArrayList<ElementBean>();
			elementBeanList = elementDAO.getElementsBySchema(s);
			schemaBean.setElements(elementBeanList);

			if (s.getDocumentation() != null)
				schemaBean.setDocumentation(s.getDocumentation().getContent());
			schemaBeanList.add(schemaBean);
		}
		logger.debug("end public List<SchemaBean> getSchemas(Description desc)");
		return schemaBeanList;
	}

	public List<SchemaBean> getSchemasByType(Types type) throws XmlException {
		logger.debug("start public List<SchemaBean> getSchemasByType(Types type)");
		List<Schema> schemaList = type.getSchemas();
		List<SchemaBean> schemaBeanList = new ArrayList<SchemaBean>();
		SchemaBean schemaBean = new SchemaBean();
		for (Schema s : schemaList) {
			schemaBean.setAllNamespaces(s.getAllNamespaces());
			schemaBean.setAttributeFormDefault(s.getAttributeFormDefault());
			schemaBean.setAttributes(s.getAttributes());
			schemaBean.setBlockDefault(s.getBlockDefault());
			schemaBean.setDocumentURI(s.getDocumentURI());
			schemaBean.setElementFormDefault(s.getElementFormDefault());
			schemaBean.setFinalDefault(s.getFinalDefault());
			schemaBean.setImports(s.getImports());
			schemaBean.setIncludes(s.getIncludes());
			schemaBean.setLang(s.getLang());
			schemaBean.setOtherAttributes(s.getOtherAttributes());
			schemaBean.setTargetNamespace(s.getTargetNamespace());
			schemaBean.setVersion(s.getVersion());

			// Set All Type to Schema (One Schema have many Element)
			ITypeDAO typeDAO = new TypeDAO();
			List<TypeBean> typeBeanList =  typeDAO.getTypeBySchema(s);
			schemaBean.setTypes(typeBeanList);

			// Set All Element to Schema (One Schema have many Element)
			IElementDAO elementDAO = new ElementDAO();
			List<ElementBean> elementBeanList = new ArrayList<ElementBean>();
			elementBeanList = elementDAO.getElementsBySchema(s);
			schemaBean.setElements(elementBeanList);

			if (s.getDocumentation() != null)
				schemaBean.setDocumentation(s.getDocumentation().getContent());
			schemaBeanList.add(schemaBean);
		}
		logger.debug("end public List<SchemaBean> getSchemasByType(Types type)");
		return schemaBeanList;
	}

	public SchemaBean getSchemasByImportSchema(Import im) throws XmlException {
		Schema s = im.getSchema();
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setAllNamespaces(s.getAllNamespaces());
		schemaBean.setAttributeFormDefault(s.getAttributeFormDefault());
		schemaBean.setAttributes(s.getAttributes());
		schemaBean.setBlockDefault(s.getBlockDefault());
		schemaBean.setDocumentURI(s.getDocumentURI());
		schemaBean.setElementFormDefault(s.getElementFormDefault());
		schemaBean.setFinalDefault(s.getFinalDefault());
		schemaBean.setImports(s.getImports());
		schemaBean.setIncludes(s.getIncludes());
		schemaBean.setLang(s.getLang());
		schemaBean.setOtherAttributes(s.getOtherAttributes());
		schemaBean.setTargetNamespace(s.getTargetNamespace());
		schemaBean.setVersion(s.getVersion());

		// Set All Type to Schema (One Schema have many Element)
		ITypeDAO typeDAO = new TypeDAO();
		List<TypeBean> typeBeanList = typeDAO.getTypeBySchema(s);
		schemaBean.setTypes(typeBeanList);

		// Set All Element to Schema (One Schema have many Element)
		IElementDAO elementDAO = new ElementDAO();
		List<ElementBean> elementBeanList = new ArrayList<ElementBean>();
		elementBeanList = elementDAO.getElementsBySchema(s);
		schemaBean.setElements(elementBeanList);

		if (s.getDocumentation() != null)
			schemaBean.setDocumentation(s.getDocumentation().getContent());

		logger.debug("end public List<SchemaBean> getSchemasByType(Types type)");
		return schemaBean;
	}


}