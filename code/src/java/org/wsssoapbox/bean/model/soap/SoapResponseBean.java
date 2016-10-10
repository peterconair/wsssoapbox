package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.soap.SOAPMessage;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 * @author  Peter
 */
@ManagedBean(name = "soapResponseBean")
@SessionScoped
public class SoapResponseBean implements Serializable {

   private static final long serialVersionUID = 1580546377095328466L;
   private List<SoapAttachmentBean> soapAttachments;
   private SoapPartBean soapPart;
   private SOAPMessage soapResponse;
   private String soapTextFormat;
   private String soapXMLFormat;
   private String soapDecryptedMsg;
   private String soapDecryptedXML;
   private String responseHeader;
   private String responseTime;
   private Map<String, String> mimeHeaders;
   private List<MimeHeaderBean> mimeHeaderLists;

   public List<SoapAttachmentBean> getSoapAttachments() {
      return soapAttachments;
   }

   public void setSoapAttachments(List<SoapAttachmentBean> soapAttachments) {
      this.soapAttachments = soapAttachments;
   }

   public SoapPartBean getSoapPart() {
      return soapPart;
   }

   public void setSoapPart(SoapPartBean soapPart) {
      this.soapPart = soapPart;
   }

   public String getSoapTextFormat() {
      return soapTextFormat;
   }

   public void setSoapTextFormat(String soapTextFormat) {
      this.soapTextFormat = soapTextFormat;
   }

   public String getSoapXMLFormat() {
      return soapXMLFormat;
   }

   public void setSoapXMLFormat(String soapXMLFormat) {
      this.soapXMLFormat = soapXMLFormat;
   }

   public Map<String, String> getMimeHeaders() {
      return mimeHeaders;
   }

   public void setMimeHeaders(Map<String, String> mimeHeaders) {
      this.mimeHeaders = mimeHeaders;

      List<MimeHeaderBean> mimHeaderList = new ArrayList<MimeHeaderBean>();
      Iterator iterator = this.mimeHeaders.entrySet().iterator();
      MimeHeaderBean mimeHeaderBean = null;
      while (iterator.hasNext()) {
         mimeHeaderBean = new MimeHeaderBean();
         Map.Entry m = (Map.Entry) iterator.next();
         String key = (String) m.getKey();
         String value = (String) m.getValue();
         mimeHeaderBean.setHeader(key);
         mimeHeaderBean.setValue(value);
         mimHeaderList.add(mimeHeaderBean);
      }
      this.setMimeHeaderLists(mimHeaderList);

   }

   /**
    * @return the mimeHeaderLists
    */
   public List<MimeHeaderBean> getMimeHeaderLists() {
      return mimeHeaderLists;
   }

   /**
    * @param mimeHeaderLists the mimeHeaderLists to set
    */
   public void setMimeHeaderLists(List<MimeHeaderBean> mimeHeaderLists) {
      this.mimeHeaderLists = mimeHeaderLists;
   }

   /**
    * @return the soapResponse
    */
   public SOAPMessage getSoapResponse() {
      return soapResponse;
   }

   /**
    * @param soapResponse the soapResponse to set
    */
   public void setSoapResponse(SOAPMessage soapResponse) {
      this.soapResponse = soapResponse;
   }

   /**
    * @return the soapDecryptedMsg
    */
   public String getSoapDecryptedMsg() {
      return soapDecryptedMsg;
   }

   /**
    * @param soapDecryptedMsg the soapDecryptedMsg to set
    */
   public void setSoapDecryptedMsg(String soapDecryptedMsg) {
      this.soapDecryptedMsg = soapDecryptedMsg;
   }

   /**
    * @return the soapDecryptedXML
    */
   public String getSoapDecryptedXML() {
      return XMLUtils.prettyPrint(soapDecryptedMsg);
   }

   /**
    * @param soapDecryptedXML the soapDecryptedXML to set
    */
   public void setSoapDecryptedXML(String soapDecryptedXML) {
      this.soapDecryptedXML = soapDecryptedXML;
   }

   /**
    * @return the responseHeader
    */
   public String getResponseHeader() {
      return responseHeader;
   }

   /**
    * @param responseHeader the responseHeader to set
    */
   public void setResponseHeader(String responseHeader) {
      this.responseHeader = responseHeader;
   }

   /**
    * @return the responseTime
    */
   public String getResponseTime() {
      return responseTime;
   }

   /**
    * @param responseTime the responseTime to set
    */
   public void setResponseTime(String responseTime) {
      this.responseTime = responseTime;
   }


}
