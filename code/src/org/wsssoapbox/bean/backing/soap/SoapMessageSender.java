package org.wsssoapbox.bean.backing.soap;

import java.net.MalformedURLException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SoapMessageSender {

	private static final Log logger = LogFactory.getLog(SoapMessageSender.class);

	public static SOAPMessage sent(SOAPConnection connection, SOAPMessage message, String endpoint)
			throws MalformedURLException, SOAPException {
		SOAPMessage soapResponse = null;
		try {
			logger.info("Sending resuest to : " + endpoint);

			soapResponse = connection.call(message, endpoint);

		} catch (SOAPException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
			e.printStackTrace();

		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return soapResponse;
	}
}
