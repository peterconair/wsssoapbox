package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Output;
import org.ow2.easywsdl.wsdl.api.Part;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.bean.model.wsdl.PartBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public class PartDAO implements IPartDAO {
	
 private static final Logger logger=LoggerFactory.getLogger(PartDAO.class);
  
 @Override
	public List<PartBean> getPartsByInput(Input input) throws XmlException {
		logger.debug("start public List<PartBean> getPartsByInput(Input input)");
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		List<Part> partList = input.getParts();

		for (Part p : partList) {
			PartBean partBean = new PartBean();
			partBean.setPartQName(p.getPartQName());
			//			partBean.setDocumentation(p.getDocumentation().getContent());									
			partBean.setOtherAttributes(p.getOtherAttributes());
			partBean.setOtherElements(p.getOtherElements());
			//	logger.debug("Part QName : " + p.getPartQName());

			//type( one Part have one Type)
			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByPart(p);
			partBean.setType(typeBean);

			//set Element ( one Part have one Element )
			IElementDAO elementDAO = new ElementDAO();
			ElementBean elementBean = new ElementBean();
			elementBean = elementDAO.getElementByPart(p);
			partBean.setElement(elementBean);
			partBeanList.add(partBean);
		}
		logger.debug("end public List<PartBean> getPartsByInput(Input input)");
		return partBeanList;
	}

   @Override
	public List<PartBean> getPartsByOuput(Output output) throws XmlException {
		logger.debug("start public List<PartBean> getPartsByOuput(Output output)");
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		List<Part> partList = output.getParts();

          if(!partList.isEmpty()){
		for (Part p : partList) {
			PartBean partBean = new PartBean();
			partBean.setPartQName(p.getPartQName());
			//			partBean.setDocumentation(p.getDocumentation().getContent());									
			partBean.setOtherAttributes(p.getOtherAttributes());
			partBean.setOtherElements(p.getOtherElements());
			logger.debug("Part QName : " + p.getPartQName());

			//type( one Part have one Type)
			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByPart(p);
			partBean.setType(typeBean);

			//set Element ( one Part have one Element )
			IElementDAO elementDAO = new ElementDAO();
			ElementBean elementBean = new ElementBean();
			elementBean = elementDAO.getElementByPart(p);
			partBean.setElement(elementBean);
			partBeanList.add(partBean);

		}
		logger.debug("end public List<PartBean> getPartsByOuput(Output output)");
		return partBeanList;
          }else{
             return null;
          }
	}

	public List<PartBean> getPartsByFault(Fault fault) throws XmlException {
		logger.debug("start public List<PartBean> getPartsByFault(Fault fault)");
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		List<Part> partList = fault.getParts();

		for (Part p : partList) {
			PartBean partBean = new PartBean();
			partBean.setPartQName(p.getPartQName());
			//			partBean.setDocumentation(p.getDocumentation().getContent());									
			partBean.setOtherAttributes(p.getOtherAttributes());
			partBean.setOtherElements(p.getOtherElements());
			logger.debug("Part QName : " + p.getPartQName());

			//type( one Part have one Type)
			ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			typeBean = typeDAO.getTypeByPart(p);
			partBean.setType(typeBean);

			//set Element ( one Part have one Element )
			IElementDAO elementDAO = new ElementDAO();
			ElementBean elementBean = new ElementBean();
			elementBean = elementDAO.getElementByPart(p);
			partBean.setElement(elementBean);
			partBeanList.add(partBean);
		}
		logger.debug("end public List<PartBean> getPartsByFault(Fault fault)");
		return partBeanList;
	}



}
