package org.wsssoapbox.datasource.wsdl;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;

@ManagedBean(name = "wsdlDataSource")
@SessionScoped
public class WSDLDocumentDataSource implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Description desc;
	private static WSDLReader reader;
	private static final Log logger = LogFactory.getLog(WSDLDocumentDataSource.class);

	public WSDLDocumentDataSource(String wsdlURL) throws WSDLException, MalformedURLException, URISyntaxException ,IOException, 
			URISyntaxException {
		initail(wsdlURL);
	}

	public static void initail(String wsdlURL) throws WSDLException, MalformedURLException, IOException,
			URISyntaxException {
		logger.debug(">>>>>>>>>>>>>>>> Start private void initail(String wsdlURL) ");

		reader = WSDLFactory.newInstance().newWSDLReader();
		desc = reader.read(new URL(wsdlURL));

		logger.debug(">>>>>>>>>>>>>>>>>> End private void initail(String wsdlURL) <<<<<<<<<<<<<<<<");
	}

	public Description getDesc() {
		return desc;
	}

	public WSDLReader getReader() {
		return reader;
	}

}
