package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.soap.SOAPMessage;

/**
 * @author  Peter
 */
@ManagedBean(name = "soapRequestBean")
@SessionScoped
public class SoapRequestBean implements Serializable {

   private static final long serialVersionUID = 1580546377095328466L;
   private List<SoapAttachmentBean> soapAttachments;
   private SoapPartBean soapPart;
   private SOAPMessage SOAPRequest;
   private String soapTextFormat;
   private String elementFormDefault;
   private String soapOriginal;
   private String soapXMLFormat;
   private String soapAttachmentValue;
   private String requestHeader;
   //for download
   private String soapRequestDownload;
   private String secureSoapMessageValue;
   private boolean secureSoapMessage;
   private boolean soapAttachment;
   private Map<String, String> mimeHeaders;
   private Map<String, String> userMimeHeaders;
   private List<MimeHeaderBean> mimeHeaderLists;
   private List<MimeHeaderBean> userMimeHeaderLists;

   public SoapRequestBean() {
      soapAttachments = new ArrayList<SoapAttachmentBean>();
      mimeHeaderLists = new ArrayList<MimeHeaderBean>();
      userMimeHeaderLists = new ArrayList<MimeHeaderBean>();
   }

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
      try {
         return new String(soapTextFormat.getBytes(), "UTF-8");
      } catch (UnsupportedEncodingException ex) {
         return soapTextFormat;
      }
   }

   public void setSoapTextFormat(String soapTextFormat) {
      this.soapTextFormat = soapTextFormat;
   }

   public String getSoapXMLFormat() {
      try {
         return new String(soapXMLFormat.getBytes(), "UTF-8");
      } catch (UnsupportedEncodingException ex) {
         return soapXMLFormat;
      }
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
      this.mimeHeaderLists = mimHeaderList;
   }

   /**
    * @return the SOAPRequest
    */
   public SOAPMessage getSOAPRequest() {
      return SOAPRequest;
   }

   /**
    * @param SOAPRequest the SOAPRequest to set
    */
   public void setSOAPRequest(SOAPMessage SOAPRequest) {
      this.SOAPRequest = SOAPRequest;
   }

   /**
    * @return the elementFormDefault
    */
   public String getElementFormDefault() {
      return elementFormDefault;
   }

   /**
    * @param elementFormDefault the elementFormDefault to set
    */
   public void setElementFormDefault(String elementFormDefault) {
      this.elementFormDefault = elementFormDefault;
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
    * @return the userMimeHeaders
    */
   public Map<String, String> getUserMimeHeaders() {
      return userMimeHeaders;
   }

   /**
    * @return the userMimeHeaderLists
    */
   public List<MimeHeaderBean> getUserMimeHeaderLists() {
      return userMimeHeaderLists;
   }

   /**
    * @param userMimeHeaderLists the userMimeHeaderLists to set
    */
   public void setUserMimeHeaderLists(List<MimeHeaderBean> userMimeHeaderLists) {
      this.userMimeHeaderLists = userMimeHeaderLists;
   }

   /**
    * @param userMimeHeaders the userMimeHeaders to set
    */
   public void setUserMimeHeaders(Map<String, String> userMimeHeaders) {
      this.userMimeHeaders = userMimeHeaders;

      List<MimeHeaderBean> mimHeaderList = new ArrayList<MimeHeaderBean>();
      Iterator iterator = this.userMimeHeaders.entrySet().iterator();
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
      this.userMimeHeaderLists = mimHeaderList;

   }

   /**
    * @return the secureSoapMessageValue
    */
   public String getSecureSoapMessageValue() {
      return secureSoapMessageValue;
   }

   /**
    * @param secureSoapMessageValue the secureSoapMessageValue to set
    */
   public void setSecureSoapMessageValue(String secureSoapMessageValue) {
      this.secureSoapMessageValue = secureSoapMessageValue;
   }

   /**
    * @return the secureSoapMessage
    */
   public boolean isSecureSoapMessage() {
      return secureSoapMessage;
   }

   /**
    * @param secureSoapMessage the secureSoapMessage to set
    */
   public void setSecureSoapMessage(boolean secureSoapMessage) {
      this.secureSoapMessage = secureSoapMessage;
   }

   /**
    * @return the soapRequestDownload
    */
   public String getSoapRequestDownload() {
      if (!secureSoapMessage) {
         return soapXMLFormat;
      } else {
         return secureSoapMessageValue;
      }
   }

   /**
    * @param soapRequestDownload the soapRequestDownload to set
    */
   public void setSoapRequestDownload(String soapRequestDownload) {
      this.soapRequestDownload = soapRequestDownload;
   }

   /**
    * @return the requestHeader
    */
   public String getRequestHeader() {
      return requestHeader;
   }

   /**
    * @param requestHeader the requestHeader to set
    */
   public void setRequestHeader(String requestHeader) {
      this.requestHeader = requestHeader;
   }

   /**
    * @return the soapOriginal
    */
   public String getSoapOriginal() {
      try {
         return new String(soapOriginal.getBytes(), "UTF-8");
      } catch (UnsupportedEncodingException ex) {
         return soapOriginal;
      }
   }

   /**
    * @param soapOriginal the soapOriginal to set
    */
   public void setSoapOriginal(String soapOriginal) {
      this.soapOriginal = soapOriginal;
   }

   /**
    * @return the soapAttachmentValue
    */
   public String getSoapAttachmentValue() {
      return soapAttachmentValue;
   }

   /**
    * @param soapAttachmentValue the soapAttachmentValue to set
    */
   public void setSoapAttachmentValue(String soapAttachmentValue) {
      this.soapAttachmentValue = soapAttachmentValue;
   }

   /**
    * @return the soapAttachment
    */
   public boolean isSoapAttachment() {
      return soapAttachment;
   }

   /**
    * @param soapAttachment the soapAttachment to set
    */
   public void setSoapAttachment(boolean soapAttachment) {
      this.soapAttachment = soapAttachment;
   }
}
