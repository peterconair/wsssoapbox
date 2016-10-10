package org.wsssoapbox.bean.backing;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wsssoapbox.util.xml.XMLUtil;
import org.wsssoapbox.view.bean.TreeBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.wsssoapbox.bean.backing.soap.SoapRequestForm;
import org.wsssoapbox.bean.backing.soap.SoapRequestOutline;
import org.wsssoapbox.bean.backing.soap.SoapRequestRaw;
import org.wsssoapbox.bean.model.soap.MimeHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapBodyBean;
import org.wsssoapbox.bean.model.soap.SoapEnvelopeBean;
import org.wsssoapbox.bean.model.soap.SoapFaultBean;
import org.wsssoapbox.bean.model.soap.SoapHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.bean.model.soap.SoapBindingBean;
import org.wsssoapbox.bean.model.soap.SoapMessageBean;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.bean.util.ScopeController;
import org.wsssoapbox.dao.soap.ISoapModelDAO;
import org.wsssoapbox.dao.soap.SoapModelDAO;
import org.wsssoapbox.soap.SoapCreator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ManagedBean(name = "operationDeailsForm")
@SessionScoped
public class OpeationDeatilsForm implements Serializable {
	private static final Log logger = LogFactory.getLog(OpeationDeatilsForm.class);
	private static final long serialVersionUID = 1L;
	private OperationBean operationBean;
	private InterfaceBean interfaceBean;
	private BindingBean bindingBean;
	private String operationName;
	private SoapEnvelopeBean soapEnvelopeBean;
	private SoapHeaderBean soapHeaderBean;
	private SoapBodyBean soapBodyBean;
	private SoapOperationBean soapOperationBean;
	private List<SoapBodyBean> soapBodyBeanEntryList;
	private SoapFaultBean soapFautlBean;
	private List<SoapFaultBean> soapFaultBeanList;
	private List<SoapParameterBean> soapParameterBeanList;
	private SoapBindingBean soapBindingBean;
	//	private SoapRequestBean soapRequestBean;
	private SoapRequestBean soapRequestBean;
	Map<String, String> mimeHeaders;

	public OpeationDeatilsForm() {
		logger.debug(" >>>>>>>>>>>>> StartOpeationDeatilsForm()");
		updateContent();
		logger.debug(" >>>>>>>>>>>>> End OpeationDeatilsForm() <<<<<<<<<<<<<<<< ");
	}

	public void updateContent() {

		soapBindingBean = new SoapBindingBean();
		operationName = (String) ScopeController.getSessionMap("operationName");
		operationBean = new OperationBean();
		operationBean = (OperationBean) ScopeController.getSessionMap("operationBean");

		interfaceBean = new InterfaceBean();
		interfaceBean = (InterfaceBean) ScopeController.getSessionMap("interfaceBean");

		bindingBean = new BindingBean();
		bindingBean = (BindingBean) ScopeController.getSessionMap("bindingBean");

		soapRequestBean = new SoapRequestBean();
		soapEnvelopeBean = new SoapEnvelopeBean();
		soapHeaderBean = new SoapHeaderBean();
		soapBodyBean = new SoapBodyBean();
		soapBodyBeanEntryList = new ArrayList<SoapBodyBean>();
		soapFautlBean = new SoapFaultBean();
		soapFaultBeanList = new ArrayList<SoapFaultBean>();
		soapParameterBeanList = new ArrayList<SoapParameterBean>();

		logger.debug("Operation Name     : " + operationBean.getName());
		logger.debug("Operation QName    : " + operationBean.getQname());
		logger.debug("Operation Signature: " + operationBean.getSignature());
		logger.debug("OperationInput     : " + operationBean.getInput());

	}

	public String submit() throws SOAPException, IOException, ParserConfigurationException, SAXException,
			TransformerFactoryConfigurationError, TransformerException {
		logger.debug(" >>>>>>>>>>>>> Start public String submit()");

		SOAPMessage requestMessage = null;

		// get new data from session and initiate page
		updateContent();

		requestMessage = MessageFactory.newInstance().createMessage();

		Description desc = (Description) ScopeController.getSessionMap("desc");
		ISoapModelDAO soapModelDAO = new SoapModelDAO();

		QName interfaceQName = interfaceBean.getQname();
		QName operationQName = operationBean.getQname();
		QName bindingQName = bindingBean.getQname();

		logger.debug(" Operation QName : " + operationQName);
		logger.debug(" Interface QName : " + interfaceQName);
		logger.debug(" Binding QName : " + bindingQName);

		try {
			soapBindingBean = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);
			// hide navigator tree layout in ui.xhtml page
			PageViewController pageViewController = (PageViewController) ScopeController
					.getSessionMap("pageViewController");
			pageViewController.setNavigatorCollapse(true);
		} catch (XmlException e) {
			e.printStackTrace();
			logger.error(" XmlException : " + e.getMessage());
		}

		// create soap message using saaj 
		requestMessage = SoapCreator.createSOAPMessage(requestMessage, soapBindingBean);

		ScopeController.setSessionMap("requestMessage", requestMessage);

		Iterator<?> names = requestMessage.getMimeHeaders().getAllHeaders();

		mimeHeaders = new HashMap<String, String>();
		logger.debug(" *****************Original from Operation Deatails Form ***************");
		while (names.hasNext()) {
			MimeHeader h = (MimeHeader) names.next();
			mimeHeaders.put(h.getName(), h.getValue());
			logger.debug(" Mime Header before : " + h.getName() + " : " + h.getValue());
		}
		logger.debug(" ************************************");

		logger.debug(" Request Mesaage Form Binding : ");
		XMLUtil.print(requestMessage);

		// convert message to String
		String soapRequtestTextFormat = XMLUtil.getSoapMessageString(requestMessage);

		// convert to xml format
		String pretyFormat = XMLUtil.prettyPrint(soapRequtestTextFormat);

		logger.debug(" Message : \n" + pretyFormat);

		soapRequestBean = SoapCreator.createSOAPRequestBean(requestMessage, operationQName);
		soapRequestBean.setSoapXMLFormat(pretyFormat);
		soapRequestBean.setSoapTextFormat(soapRequtestTextFormat);
		soapRequestBean.setMimeHeaders(mimeHeaders);
		soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getOperation()
				.setParameters(soapBindingBean.getOperation().getParameters());

		ScopeController.setSessionMap("soapRequestBean", soapRequestBean);

		logger.debug(" >>>>>>>>>>>>> End public String submit() <<<<<<<<<<<<<<<< ");
		return "createMessage";
	}
}
