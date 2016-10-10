package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Include;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.bean.model.wsdl.IncludeBean;

public class IncludeDAO implements IIncludeDAO{

	public List<IncludeBean> getIncludeByDescription(Description desc) throws XmlException {
		List <IncludeBean> includeBeanList = new ArrayList<IncludeBean>();
		List <Include> includeList = new ArrayList<Include>();	
		includeList = desc.getIncludes();
		
		for(Include in : includeList){
			IncludeBean includeBean = new IncludeBean();
			includeBean.setDocumentation(in.getDocumentation().getContent());
			includeBean.setLocationURI(in.getLocationURI().toString());
			includeBean.setOtherAttributes(in.getOtherAttributes());
			includeBean.setOtherElements(in.getOtherElements());
			
			// set WSDL document (On Include have one Document)
			IWSDLDAO wsdlDAO = new WSDLDAO();
			DescriptionBean wSDLBean= new DescriptionBean();			
			wSDLBean = wsdlDAO.getWSDLDocument(desc);
			includeBean.setDescription(wSDLBean);
			
			// add to list 
			includeBeanList.add(includeBean);
		}
		
		return includeBeanList;
	}



}
