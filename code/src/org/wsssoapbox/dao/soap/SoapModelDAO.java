package org.wsssoapbox.dao.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.All;
import org.ow2.easywsdl.schema.api.Choice;
import org.ow2.easywsdl.schema.api.ComplexType;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Restriction;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Sequence;
import org.ow2.easywsdl.schema.api.SimpleType;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Part;
import org.wsssoapbox.bean.backing.soap.SoapMessageSender;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;
import org.wsssoapbox.bean.model.soap.SoapBodyBean;
import org.wsssoapbox.bean.model.soap.SoapEnvelopeBean;
import org.wsssoapbox.bean.model.soap.SoapHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.soap.SoapConstants;
import org.wsssoapbox.bean.model.soap.SoapBindingBean;

public class SoapModelDAO implements ISoapModelDAO {
	private static final Log logger = LogFactory.getLog(SoapMessageSender.class);
	public SoapBindingBean getSoapRequestRPC(Description desc, QName interfaceQName, QName operationQName)
			throws XmlException {

		List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();
		logger.debug("wsld url  : " + desc.getDocumentBaseURI());
		logger.debug("Interface QName  : " + interfaceQName);
		logger.debug("Operation QName  : " + operationQName);

		//		logger.debug("Interfaces  : " + desc.getInterface(interfaceQName));
		//		logger.debug("Operations  : " + desc.getInterface(interfaceQName).getOperation(operationQName));

		Input input = desc.getInterface(interfaceQName).getOperation(operationQName).getInput();

		logger.debug("Operations input  : " + input);

		// get Element correspone that Operation and Interface
		Element inputElement = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getElement();

		logger.debug("getSchemas  : " + desc.getTypes().getSchemas());

		List<Part> partList = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getParts();
		String inputName = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getName();
		logger.debug("Operations input Name  : " + inputName);

		QName messageQName = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getMessageName();
		logger.debug("Operations input Message QName : " + messageQName);

		for (Part p : partList) {
			logger.debug("Part Element  :" + p.getElement());
		}

		logger.debug("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");

		logger.debug("Input Element  : " + inputElement);
		logger.debug("Input Element Type  : " + inputElement.getType());
		Type inputType = inputElement.getType();
		logger.debug("Input Element Type QName  : " + inputType.getQName());
		Element paramElement = null;

		QName elementQName = inputElement.getQName();

		// find element in Type >> schema by element
		List<Schema> schemas = desc.getTypes().getSchemas();
		for (Schema schema : schemas) {
			paramElement = schema.getElement(elementQName);
			logger.debug("Type Size  : " + schema.getTypes().size() + " Elements");
		}

		logger.debug("Input Element QName for Input : " + paramElement.getQName());
		logger.debug("Input Element Min  for Input : " + paramElement.getMinOccurs());
		logger.debug("Input Element Max  for Input : " + paramElement.getMaxOccurs());
		logger.debug("Input Element Form  for Input : " + paramElement.getForm());
		logger.debug("Input Element Ref  for Input : " + paramElement.getRef());
		logger.debug("Input Element getOtherAttributes  for Input : " + paramElement.getOtherAttributes());
		logger.debug("Input Element Type  for Input : " + paramElement.getType());

		Type paramType = paramElement.getType();

		if (paramType instanceof ComplexType) {
			final ComplexType elementComplexType = (ComplexType) paramType;
			final All elementALL = elementComplexType.getAll();
			final Choice elementChoice = elementComplexType.getChoice();
			final Sequence elementSeq = elementComplexType.getSequence();

			logger.debug("Complex Type :" + elementComplexType);
			logger.debug("All Type :" + elementALL);
			logger.debug("Choice Type :" + elementChoice);
			logger.debug("Sequence :" + elementSeq);

			if (elementChoice != null) {
				final List<Element> elementChoiceList = elementChoice.getElements();

				for (Element e : elementChoiceList) {
					logger.debug("Sequence Element :" + e.getQName());
					logger.debug("Sequence MinOccurs :" + e.getMinOccurs());
					logger.debug("Sequence MaxOccurs :" + e.getMaxOccurs());
					logger.debug("Sequence Type :" + e.getType());
					logger.debug(" Name :" + e.getType().getQName().getLocalPart());
					logger.debug(" Prefix :" + e.getType().getQName().getPrefix());
					logger.debug(" Namespace :" + e.getType().getQName().getNamespaceURI());

					Type eType = e.getType();
					String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
					int minOccure = e.getMinOccurs();
					String maxOccure = e.getMaxOccurs();
					String type = eType.getQName().getLocalPart();
					String name = e.getQName().getLocalPart();
					String paraNamespaceURI = e.getQName().getNamespaceURI();
					String paralocalPart = e.getQName().getLocalPart();
					String paraPrefix = e.getQName().getPrefix();
					QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);

					SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
							defaultValue);
					parameters.add(param);

				}
			}
			if (elementALL != null) {
				final List<Element> elementAllList = elementALL.getElements();
				for (Element e : elementAllList) {
					logger.debug("Sequence Element :" + e.getQName());
					logger.debug("Sequence MinOccurs :" + e.getMinOccurs());
					logger.debug("Sequence MaxOccurs :" + e.getMaxOccurs());
					logger.debug("Sequence Type :" + e.getType());
					logger.debug(" Name :" + e.getType().getQName().getLocalPart());
					logger.debug(" Prefix :" + e.getType().getQName().getPrefix());
					logger.debug(" Namespace :" + e.getType().getQName().getNamespaceURI());

					Type eType = e.getType();
					String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
					int minOccure = e.getMinOccurs();
					String maxOccure = e.getMaxOccurs();
					String type = eType.getQName().getLocalPart();
					String name = e.getQName().getLocalPart();
					String paraNamespaceURI = e.getQName().getNamespaceURI();
					String paralocalPart = e.getQName().getLocalPart();
					String paraPrefix = e.getQName().getPrefix();
					QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);

					SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
							defaultValue);
					parameters.add(param);

				}
			}

			if (elementSeq != null) {
				final List<Element> elementSeqList = elementSeq.getElements();

				for (Element e : elementSeqList) {
					logger.debug("Sequence Element :" + e.getQName());
					logger.debug("Sequence MinOccurs :" + e.getMinOccurs());
					logger.debug("Sequence MaxOccurs :" + e.getMaxOccurs());
					logger.debug("Sequence Type :" + e.getType());
					logger.debug(" Name :" + e.getType().getQName().getLocalPart());
					logger.debug(" Prefix :" + e.getType().getQName().getPrefix());
					logger.debug(" Namespace :" + e.getType().getQName().getNamespaceURI());

					Type eType = e.getType();
					String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
					int minOccure = e.getMinOccurs();
					String maxOccure = e.getMaxOccurs();
					String type = eType.getQName().getLocalPart();
					String name = e.getQName().getLocalPart();
					String paraNamespaceURI = e.getQName().getNamespaceURI();
					String paralocalPart = e.getQName().getLocalPart();
					String paraPrefix = e.getQName().getPrefix();
					QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);

					SoapParameterBean p1 = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
							defaultValue);
					parameters.add(p1);

				}
			}
		} else if (paramType instanceof SimpleType) {
			final SimpleType simpleType = (SimpleType) paramType;
			final Restriction res = simpleType.getRestriction();

			logger.debug("SimpleType Element :" + simpleType);
			logger.debug("Restriction Element :" + res.getBase());
			logger.debug("Restriction Element :" + res.getEnumerations());

		}

		String operationName = operationQName.getLocalPart();
		String operationPrefix = operationQName.getPrefix();
		if (operationPrefix.equals("") || operationPrefix == null) {
			operationPrefix = "tns";
		}

		SoapOperationBean operation = new SoapOperationBean(operationName, operationQName, parameters);

		SoapBindingBean soapRequest = new SoapBindingBean();

		//Choose necessary schema are well form Soap speck
		Map<String, String> namespaces = new HashMap<String, String>();
		Map<String, String> tmp = desc.getNamespaces().getNamespaces();
		List<String> knowSchemas = SoapConstants.WELL_FORM_SCHEMAS;

		logger.debug("List of Know Schemas : " + knowSchemas);
		if (tmp != null) {
			Iterator iteratorTemp = tmp.entrySet().iterator();

			for (String name : knowSchemas) {
				while (iteratorTemp.hasNext()) {
					Map.Entry<String, String> me = (Map.Entry) iteratorTemp.next();
					String key = me.getKey();
					String value = me.getValue();
					if (name.equals(value)) {
						namespaces.put(key, value);
					}
				}
			}
		}
		logger.debug("List of Schemas : " + namespaces);

		soapRequest.setHeader(false);
		soapRequest.setXmlStandalone(true);
		soapRequest.setNamespaces(namespaces);
		soapRequest.setOperation(operation);

		return soapRequest;

	}

	public SoapBindingBean createSoapBindingBeanDoc(Description desc, QName bindingQName, QName interfaceQName,
			QName operationQName) throws XmlException {

		List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();
		
		String operationName = operationQName.getLocalPart();
		String soapVersion = desc.getBinding(bindingQName).getVersion();
		String soapAction = desc.getBinding(bindingQName).getBindingOperation(operationName).getSoapAction();
		
		
		
		logger.debug("wsld url  : " + desc.getDocumentBaseURI());
		logger.debug("Interface QName  : " + interfaceQName);
		logger.debug("Operation QName  : " + operationQName);
		logger.debug("Operation Name  : " + operationName);
		logger.debug("Soap Vesion  : " + soapVersion);
		logger.debug("Soap Action  : " + soapAction);


		// get Element correspond that Operation and Interface
		Element inputElement = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getElement();

		logger.debug("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");

		logger.debug("Input Element  : " + inputElement);
		logger.debug("Input Element Type  : " + inputElement.getType());
		Type inputType = inputElement.getType();
		logger.debug("Input Element Type QName  : " + inputType.getQName());
		Element paramElement = null;

		QName elementQName = inputElement.getQName();

		// find element in Type >> schema by element
		List<Schema> schemas = desc.getTypes().getSchemas();
		for (Schema schema : schemas) {
			paramElement = schema.getElement(elementQName);
			logger.debug("Type Size  : " + schema.getTypes().size() + " Elements");
		}

		logger.debug("Input Element QName for Input : " + paramElement.getQName());
		logger.debug("Input Element Min  for Input : " + paramElement.getMinOccurs());
		logger.debug("Input Element Max  for Input : " + paramElement.getMaxOccurs());
		logger.debug("Input Element Form  for Input : " + paramElement.getForm());
		logger.debug("Input Element Ref  for Input : " + paramElement.getRef());
		logger.debug("Input Element getOtherAttributes  for Input : " + paramElement.getOtherAttributes());
		logger.debug("Input Element Type  for Input : " + paramElement.getType());

		Type paramType = paramElement.getType();

		if (paramType instanceof ComplexType) {
			final ComplexType elementComplexType = (ComplexType) paramType;
			final All elementALL = elementComplexType.getAll();
			final Choice elementChoice = elementComplexType.getChoice();
			final Sequence elementSeq = elementComplexType.getSequence();

			logger.debug("Complex Type :" + elementComplexType);
			logger.debug("All Type :" + elementALL);
			logger.debug("Choice Type :" + elementChoice);
			logger.debug("Sequence :" + elementSeq);

			if (elementChoice != null) {
				final List<Element> elementChoiceList = elementChoice.getElements();

				for (Element e : elementChoiceList) {
					logger.debug("Sequence Element :" + e.getQName());
					logger.debug("Sequence MinOccurs :" + e.getMinOccurs());
					logger.debug("Sequence MaxOccurs :" + e.getMaxOccurs());
					logger.debug("Sequence Type :" + e.getType());
					logger.debug(" Name :" + e.getType().getQName().getLocalPart());
					logger.debug(" Prefix :" + e.getType().getQName().getPrefix());
					logger.debug(" Namespace :" + e.getType().getQName().getNamespaceURI());

					Type eType = e.getType();
					String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
					int minOccure = e.getMinOccurs();
					String maxOccure = e.getMaxOccurs();
					String type = eType.getQName().getLocalPart();
					String name = e.getQName().getLocalPart();
					String paraNamespaceURI = e.getQName().getNamespaceURI();
					String paralocalPart = e.getQName().getLocalPart();
					String paraPrefix = e.getQName().getPrefix();
					QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);

					SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
							defaultValue);
					parameters.add(param);

				}
			}
			if (elementALL != null) {
				final List<Element> elementAllList = elementALL.getElements();
				for (Element e : elementAllList) {
					logger.debug("Sequence Element :" + e.getQName());
					logger.debug("Sequence MinOccurs :" + e.getMinOccurs());
					logger.debug("Sequence MaxOccurs :" + e.getMaxOccurs());
					logger.debug("Sequence Type :" + e.getType());
					logger.debug(" Name :" + e.getType().getQName().getLocalPart());
					logger.debug(" Prefix :" + e.getType().getQName().getPrefix());
					logger.debug(" Namespace :" + e.getType().getQName().getNamespaceURI());

					Type eType = e.getType();
					String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
					int minOccure = e.getMinOccurs();
					String maxOccure = e.getMaxOccurs();
					String type = eType.getQName().getLocalPart();
					String name = e.getQName().getLocalPart();
					String paraNamespaceURI = e.getQName().getNamespaceURI();
					String paralocalPart = e.getQName().getLocalPart();
					String paraPrefix = e.getQName().getPrefix();
					QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);

					SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
							defaultValue);
					parameters.add(param);

				}
			}

			if (elementSeq != null) {
				final List<Element> elementSeqList = elementSeq.getElements();

				for (Element e : elementSeqList) {
					logger.debug("Sequence Element :" + e.getQName());
					logger.debug("Sequence MinOccurs :" + e.getMinOccurs());
					logger.debug("Sequence MaxOccurs :" + e.getMaxOccurs());
					logger.debug("Sequence Type :" + e.getType());
					logger.debug(" Name :" + e.getType().getQName().getLocalPart());
					logger.debug(" Prefix :" + e.getType().getQName().getPrefix());
					logger.debug(" Namespace :" + e.getType().getQName().getNamespaceURI());

					Type eType = e.getType();
					String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
					int minOccure = e.getMinOccurs();
					String maxOccure = e.getMaxOccurs();
					String type = eType.getQName().getLocalPart();
					String name = e.getQName().getLocalPart();
					String paraNamespaceURI = e.getQName().getNamespaceURI();
					String paralocalPart = e.getQName().getLocalPart();
					String paraPrefix = e.getQName().getPrefix();
					QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);

					SoapParameterBean p1 = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
							defaultValue);
					parameters.add(p1);

				}
			}
		} else if (paramType instanceof SimpleType) {
			final SimpleType simpleType = (SimpleType) paramType;
			final Restriction res = simpleType.getRestriction();

			logger.debug("SimpleType Element :" + simpleType);
			logger.debug("Restriction Element :" + res.getBase());
			logger.debug("Restriction Element :" + res.getEnumerations());

		}

		String operationPrefix = operationQName.getPrefix();
		String operationNamesapceURI = operationQName.getNamespaceURI();

		if (operationPrefix.equals("") || operationPrefix == null) {
			operationPrefix = elementQName.getPrefix();
		} else if (operationNamesapceURI.equals("") || operationNamesapceURI == null) {
			operationNamesapceURI = elementQName.getNamespaceURI();
		}

		operationQName = new QName(operationNamesapceURI, operationName, operationPrefix);
		SoapOperationBean operation = new SoapOperationBean(operationName, operationQName, parameters);

		SoapBindingBean soapBindingBean = new SoapBindingBean();

		//Choose necessary schema are well form Soap speck
		Map<String, String> namespaces = new HashMap<String, String>();
		Map<String, String> tmp = desc.getNamespaces().getNamespaces();
		List<String> knowSchemas = SoapConstants.WELL_FORM_SCHEMAS;

		//	logger.debug("List of Know Schemas : " + knowSchemas);
		//	logger.debug("List of Know Schemas size : " + knowSchemas.size());
		if (tmp != null) {
			Iterator iteratorKnowSchemas = knowSchemas.iterator();

			for (String name : knowSchemas) {
				//	logger.debug(" Name : " + name);
				Iterator iteratorTemp = tmp.entrySet().iterator();
				while (iteratorTemp.hasNext()) {
					Map.Entry<String, String> me = (Map.Entry) iteratorTemp.next();
					String key = me.getKey();
					String value = me.getValue();
					//		logger.debug(" Key : " + key);
					//		logger.debug(" Value : " + value);
					if (name.equals(value)) {
						namespaces.put(key, value);
					}
				}
			}
		}
		logger.debug("List of Schemas : " + namespaces);

		soapBindingBean.setHeader(false);
		soapBindingBean.setXmlStandalone(true);
		soapBindingBean.setSoapVersion(soapVersion);
		soapBindingBean.setSoapAction(soapAction);
		soapBindingBean.setNamespaces(namespaces);
		soapBindingBean.setOperation(operation);

		return soapBindingBean;
	}


}
