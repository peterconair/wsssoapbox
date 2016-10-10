package wsdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.junit.BeforeClass;
import org.junit.Test;

import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.ExplicitGroup;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.LocalComplexType;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.TopLevelElement;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.SchemaBean;
import org.wsssoapbox.dao.wsdl.ISchemaDAO;
import org.wsssoapbox.dao.wsdl.SchemaDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class SchemaDAOTestCase {

   private static Description desc;
   private static String url;
   private static WSDLDataSource wsdlDocumentDataSource;
   TopLevelElement topLevel;
   LocalComplexType localComplexType;
   ExplicitGroup explicitGroup;
   JAXBElement jaxbElement;
   Schema schema;

   @BeforeClass
   public static void setUpBeforeClass() throws Exception {
      wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
      SchemaDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);

   }

 //  @Test
   public void testGetSchemasDefault() throws XmlException {
      List<Schema> schemasList = desc.getTypes().getSchemas();

      List<Schema> schemaList = desc.getTypes().getSchemas();
      System.out.println("***************** Schemas  testGetSchemasDefault()**********************");
      for (Schema s : schemasList) {

         System.out.println("   Namespaces   : " + s.getAllNamespaces());
         System.out.println("   Attribute Form Default  : " + s.getAttributeFormDefault());
         System.out.println("   Attributes   : " + s.getAttributes());
         System.out.println("   BlockDefault   : " + s.getBlockDefault());
         System.out.println("   Documentation   : " + s.getDocumentation());
         System.out.println("   Element Form Default   : " + s.getElementFormDefault());
         System.out.println("   Final Default   : " + s.getFinalDefault());
         System.out.println("   Imports   : " + s.getImports());
         System.out.println("   Includes   : " + s.getIncludes());
         System.out.println("   Namespaces   : " + s.getLang());
         System.out.println("   Lang   : " + s.getOtherAttributes());
         System.out.println("   Target Namespace   : " + s.getTargetNamespace());
         System.out.println("   Types   : " + s.getTypes());
         System.out.println("   Version   : " + s.getVersion());
         System.out.println("   Elements   : " + s.getElements());
         System.out.println("   Elements Attribute  : " + s.getElements().get(0).getType().getOtherAttributes());

      }
   }

   //@Test
   public void testGetSchemas() throws XmlException {

      ISchemaDAO schemaDAO = new SchemaDAO();
      List<SchemaBean> scheamaBeanList = new ArrayList<SchemaBean>();
      scheamaBeanList = schemaDAO.getSchemas(desc);

      System.out.println("***************** Schemas  testGetSchemas()**********************");
      for (SchemaBean s : scheamaBeanList) {
         System.out.println("   Namespaces   : " + s.getAllNamespaces());
         System.out.println("   Attribute Form Default  : " + s.getAttributeFormDefault());
         System.out.println("   Attributes   : " + s.getAttributes());
         System.out.println("   BlockDefault   : " + s.getBlockDefault());
         System.out.println("   Documentation   : " + s.getDocumentation());
         System.out.println("   Element Form Default   : " + s.getElementFormDefault());
         System.out.println("   Elements   : " + s.getElements());
         System.out.println("   Final Default   : " + s.getFinalDefault());
         System.out.println("   Imports   : " + s.getImports());
         System.out.println("   Includes   : " + s.getIncludes());
         System.out.println("   Namespaces   : " + s.getLang());
         System.out.println("   Lang   : " + s.getOtherAttributes());
         System.out.println("   Target Namespace   : " + s.getTargetNamespace());
         System.out.println("   Types   : " + s.getTypes());
         System.out.println("   Version   : " + s.getVersion());

      }
   }

   @Test
   public void testGetSchemasTypes() throws XmlException {
      Iterator<Schema> itSchema = desc.getTypes().getSchemas().iterator();
      while (itSchema.hasNext()) {
         Schema schema = itSchema.next();
         Iterator<Type> itType = schema.getTypes().iterator();
         while (itType.hasNext()) {
            Type type = itType.next();
            System.out.println("Types :" + type.getQName());
     
         }
      }

   }

   //@Test
   public void testGetSchemasByType() throws XmlException {
      Types types = desc.getTypes();
      ISchemaDAO schemaDAO = new SchemaDAO();
      List<SchemaBean> scheamaBeanList = new ArrayList<SchemaBean>();
      scheamaBeanList = schemaDAO.getSchemasByType(types);

      System.out.println("***************** Schemas  testGetSchemasByType()**********************");
      for (SchemaBean s : scheamaBeanList) {
         System.out.println("   Namespaces   : " + s.getAllNamespaces());
         System.out.println("   Attribute Form Default  : " + s.getAttributeFormDefault());
         System.out.println("   Attributes   : " + s.getAttributes());
         System.out.println("   BlockDefault   : " + s.getBlockDefault());
         System.out.println("   Documentation   : " + s.getDocumentation());
         System.out.println("   Element Form Default   : " + s.getElementFormDefault());
         System.out.println("   Elements   : " + s.getElements());
         System.out.println("   Final Default   : " + s.getFinalDefault());
         System.out.println("   Imports   : " + s.getImports());
         System.out.println("   Includes   : " + s.getIncludes());
         System.out.println("   Namespaces   : " + s.getLang());
         System.out.println("   Lang   : " + s.getOtherAttributes());
         System.out.println("   Target Namespace   : " + s.getTargetNamespace());
         System.out.println("   Types   : " + s.getTypes());
         System.out.println("   Version   : " + s.getVersion());

      }
   }

   //@Test
   public void testGetSchemasByImportSchema() throws XmlException {
      org.ow2.easywsdl.schema.api.Import im = desc.getTypes().getImportedSchemas().get(0);
      ISchemaDAO schemaDAO = new SchemaDAO();
      SchemaBean s = new SchemaBean();
      s = schemaDAO.getSchemasByImportSchema(im);

      System.out.println("***************** Schemas  testGetSchemasByImportSchema()**********************");
      System.out.println("   Namespaces   : " + s.getAllNamespaces());
      System.out.println("   Attribute Form Default  : " + s.getAttributeFormDefault());
      System.out.println("   Attributes   : " + s.getAttributes());
      System.out.println("   BlockDefault   : " + s.getBlockDefault());
      System.out.println("   Documentation   : " + s.getDocumentation());
      System.out.println("   Element Form Default   : " + s.getElementFormDefault());
      System.out.println("   Elements   : " + s.getElements());
      System.out.println("   Final Default   : " + s.getFinalDefault());
      System.out.println("   Imports   : " + s.getImports());
      System.out.println("   Includes   : " + s.getIncludes());
      System.out.println("   Namespaces   : " + s.getLang());
      System.out.println("   Lang   : " + s.getOtherAttributes());
      System.out.println("   Target Namespace   : " + s.getTargetNamespace());
      System.out.println("   Types   : " + s.getTypes());
      System.out.println("   Version   : " + s.getVersion());
   }
}
