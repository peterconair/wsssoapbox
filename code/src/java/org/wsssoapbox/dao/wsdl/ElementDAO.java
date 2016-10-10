package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Output;
import org.ow2.easywsdl.wsdl.api.Part;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public class ElementDAO implements IElementDAO {
	 private static final Logger logger=LoggerFactory.getLogger(ElementDAO.class);

    @Override
	public ElementBean getElementByInput(Input input) throws XmlException {
		logger.debug("start public ElementBean getElementByInput(Input input )");

		ElementBean elementBean = new ElementBean();
		Element element = input.getElement();
		if (element != null) {
			elementBean.setQname(element.getQName());
			//	elementBean.setDocumentation(element.getDocumentation().getContent());
			elementBean.setForm(element.getForm());
			elementBean.setMaxOccurs(element.getMaxOccurs());
			elementBean.setMinOccurs(element.getMinOccurs());
			elementBean.setOtherAttributes(element.getOtherAttributes());
			elementBean.setRef(element.getRef());

			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByElement(element);
			elementBean.setType(typeBean);
		}
		logger.debug("end public ElementBean getElementByInput(Input input )");
		return elementBean;
	}

    @Override
	public ElementBean getElementByPart(Part part) throws XmlException {
		logger.debug("start public ElementBean getElementByPart(Part part)");

		ElementBean elementBean = new ElementBean();
		Element element = part.getElement();
		if (element != null) {
			elementBean.setQname(element.getQName());
			//	elementBean.setDocumentation(element.getDocumentation().getContent());
			elementBean.setForm(element.getForm());
			elementBean.setMaxOccurs(element.getMaxOccurs());
			elementBean.setMinOccurs(element.getMinOccurs());
			elementBean.setOtherAttributes(element.getOtherAttributes());
			elementBean.setRef(element.getRef());

			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByElement(element);
			elementBean.setType(typeBean);
		}
		logger.debug("end public ElementBean getElementByPart(Part part)");
		return elementBean;
	}

    @Override
	public ElementBean getElementByOutput(Output output) throws XmlException {
		logger.debug("start public ElementBean getElementByOutput(Output output)");

		ElementBean elementBean = new ElementBean();
		Element element = output.getElement();
		if (element != null) {
			elementBean.setQname(element.getQName());
			//	elementBean.setDocumentation(element.getDocumentation().getContent());
			elementBean.setForm(element.getForm());
			elementBean.setMaxOccurs(element.getMaxOccurs());
			elementBean.setMinOccurs(element.getMinOccurs());
			elementBean.setOtherAttributes(element.getOtherAttributes());
			elementBean.setRef(element.getRef());

			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByElement(element);
			elementBean.setType(typeBean);
		}
		logger.debug("end public ElementBean getElementByOutput(Output output)");
		return elementBean;
	}

	

    @Override
	public List<ElementBean> getElementsBySchema(Schema s) throws XmlException {
		logger.debug("start public List<ElementBean> getElementsBySchema(Schema s)");

		List<ElementBean> elementBeanList = new ArrayList<ElementBean>();
		List<Element> elementList = s.getElements();

		for (Element element : elementList) {
			ElementBean elementBean = new ElementBean();

			elementBean.setQname(element.getQName());
			//	elementBean.setDocumentation(element.getDocumentation().getContent());
			elementBean.setForm(element.getForm());
			elementBean.setMaxOccurs(element.getMaxOccurs());
			elementBean.setMinOccurs(element.getMinOccurs());
			elementBean.setOtherAttributes(element.getOtherAttributes());
			elementBean.setRef(element.getRef());

			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByElement(element);
			elementBean.setType(typeBean);
			elementBeanList.add(elementBean);
		}

		logger.debug("end public List<ElementBean> getElementsBySchema(Schema s)");
		return elementBeanList;
	}

    @Override
	public ElementBean getElementByFault(Fault fault) throws XmlException {
		ElementBean elementBean = new ElementBean();
		Element element = fault.getElement();
		if (element != null) {
			elementBean.setQname(element.getQName());
			//	elementBean.setDocumentation(element.getDocumentation().getContent());
			elementBean.setForm(element.getForm());
			elementBean.setMaxOccurs(element.getMaxOccurs());
			elementBean.setMinOccurs(element.getMinOccurs());
			elementBean.setOtherAttributes(element.getOtherAttributes());
			elementBean.setRef(element.getRef());

			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByElement(element);
			elementBean.setType(typeBean);
		}
		logger.debug("end public List<ElementBean> getElementByFault(Fault fault)");
		return elementBean;
	}

}
