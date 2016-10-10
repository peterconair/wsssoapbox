/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.soap.security.keystore;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Peter
 */
public class CertificateBean implements Serializable {

   private static final long serialVersionUID = 2473068759744356253L;

   private BigInteger id;
   private String name;
   private String aliasName;
   private String filePath;
   private InputStream file;
   
   private String sigAlgName;
   private String sigAlgOID;
   private BigInteger serialNumber;
   private int basicConstraints;
   private int version;
   
   private String issuerDN;
   private String issuerCN;
   private String issuerO;
   private String issuerOU;
   private String issuerC;
   private String issuerSerialNo;
   private String subjectDN;
   private String subjectCN;
   private String subjectO;
   private String subjectOU;
   private String subjectC;
   private String subjectSerialNo;
   private boolean validity;
   private Date notBefore;
   private Date notAfter;
   private String fingerpintsSHA1;
   private String fingerpintsMD5;
   private String subKeyId;
   private String publicKeyAlg;
   private int publicKeyAlgSize;
   private String publicKeyValue;
   private String publicKeyBase64Value;
   private byte[] encoded;
   private String signature;
   private String publicFormat;

   public CertificateBean(CertificateBean cert) {
   }

   public CertificateBean() {
   }

   /**
    * @return the id
    */
   public BigInteger getId() {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(BigInteger id) {
      this.id = id;
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return the sigAlgName
    */
   public String getSigAlgName() {
      return sigAlgName;
   }

   /**
    * @param sigAlgName the sigAlgName to set
    */
   public void setSigAlgName(String sigAlgName) {
      this.sigAlgName = sigAlgName;
   }

   /**
    * @return the sigAlgOID
    */
   public String getSigAlgOID() {
      return sigAlgOID;
   }

   /**
    * @param sigAlgOID the sigAlgOID to set
    */
   public void setSigAlgOID(String sigAlgOID) {
      this.sigAlgOID = sigAlgOID;
   }

   /**
    * @return the serialNumber
    */
   public BigInteger getSerialNumber() {
      return serialNumber;
   }

   /**
    * @param serialNumber the serialNumber to set
    */
   public void setSerialNumber(BigInteger serialNumber) {
      this.serialNumber = serialNumber;
   }

   /**
    * @return the basicConstraints
    */
   public int getBasicConstraints() {
      return basicConstraints;
   }

   /**
    * @param basicConstraints the basicConstraints to set
    */
   public void setBasicConstraints(int basicConstraints) {
      this.basicConstraints = basicConstraints;
   }

   /**
    * @return the version
    */
   public int getVersion() {
      return version;
   }

   /**
    * @param version the version to set
    */
   public void setVersion(int version) {
      this.version = version;
   }

   /**
    * @return the issuerDN
    */
   public String getIssuerDN() {
      return issuerDN;
   }

   /**
    * @param issuerDN the issuerDN to set
    */
   public void setIssuerDN(String issuerDN) {
      this.issuerDN = issuerDN;
   }

   /**
    * @return the issuerO
    */
   public String getIssuerO() {
      return issuerO;
   }

   /**
    * @param issuerO the issuerO to set
    */
   public void setIssuerO(String issuerO) {
      this.issuerO = issuerO;
   }

   /**
    * @return the issuerOU
    */
   public String getIssuerOU() {
      return issuerOU;
   }

   /**
    * @param issuerOU the issuerOU to set
    */
   public void setIssuerOU(String issuerOU) {
      this.issuerOU = issuerOU;
   }

   /**
    * @return the issuerSerialNo
    */
   public String getIssuerSerialNo() {
      return issuerSerialNo;
   }

   /**
    * @param issuerSerialNo the issuerSerialNo to set
    */
   public void setIssuerSerialNo(String issuerSerialNo) {
      this.issuerSerialNo = issuerSerialNo;
   }

   /**
    * @return the subjectDN
    */
   public String getSubjectDN() {
      return subjectDN;
   }

   /**
    * @param subjectDN the subjectDN to set
    */
   public void setSubjectDN(String subjectDN) {
      this.subjectDN = subjectDN;
   }

   /**
    * @return the subjectO
    */
   public String getSubjectO() {
      return subjectO;
   }

   /**
    * @param subjectO the subjectO to set
    */
   public void setSubjectO(String subjectO) {
      this.subjectO = subjectO;
   }

   /**
    * @return the subjectOU
    */
   public String getSubjectOU() {
      return subjectOU;
   }

   /**
    * @param subjectOU the subjectOU to set
    */
   public void setSubjectOU(String subjectOU) {
      this.subjectOU = subjectOU;
   }

   /**
    * @return the subjectSerialNo
    */
   public String getSubjectSerialNo() {
      return subjectSerialNo;
   }

   /**
    * @param subjectSerialNo the subjectSerialNo to set
    */
   public void setSubjectSerialNo(String subjectSerialNo) {
      this.subjectSerialNo = subjectSerialNo;
   }

   /**
    * @return the validity
    */
   public boolean isValidity() {
      return validity;
   }

   /**
    * @param validity the validity to set
    */
   public void setValidity(boolean validity) {
      this.validity = validity;
   }

   /**
    * @return the notBefore
    */
   public Date getNotBefore() {
      return notBefore;
   }

   /**
    * @param notBefore the notBefore to set
    */
   public void setNotBefore(Date notBefore) {
      this.notBefore = notBefore;
   }

   /**
    * @return the notAfter
    */
   public Date getNotAfter() {
      return notAfter;
   }

   /**
    * @param notAfter the notAfter to set
    */
   public void setNotAfter(Date notAfter) {
      this.notAfter = notAfter;
   }

   /**
    * @return the fingerpintsSHA1
    */
   public String getFingerpintsSHA1() {
      return fingerpintsSHA1;
   }

   /**
    * @param fingerpintsSHA1 the fingerpintsSHA1 to set
    */
   public void setFingerpintsSHA1(String fingerpintsSHA1) {
      this.fingerpintsSHA1 = fingerpintsSHA1;
   }

   /**
    * @return the fingerpintsMD5
    */
   public String getFingerpintsMD5() {
      return fingerpintsMD5;
   }

   /**
    * @param fingerpintsMD5 the fingerpintsMD5 to set
    */
   public void setFingerpintsMD5(String fingerpintsMD5) {
      this.fingerpintsMD5 = fingerpintsMD5;
   }

   /**
    * @return the publicKeyAlg
    */
   public String getPublicKeyAlg() {
      return publicKeyAlg;
   }

   /**
    * @param publicKeyAlg the publicKeyAlg to set
    */
   public void setPublicKeyAlg(String publicKeyAlg) {
      this.publicKeyAlg = publicKeyAlg;
   }

   /**
    * @return the publicKeyAlgSize
    */
   public int getPublicKeyAlgSize() {
      return publicKeyAlgSize;
   }

   /**
    * @param publicKeyAlgSize the publicKeyAlgSize to set
    */
   public void setPublicKeyAlgSize(int publicKeyAlgSize) {
      this.publicKeyAlgSize = publicKeyAlgSize;
   }

   /**
    * @return the publicKeyValue
    */
   public String getPublicKeyValue() {
      return publicKeyValue;
   }

   /**
    * @param publicKeyValue the publicKeyValue to set
    */
   public void setPublicKeyValue(String publicKeyValue) {
      this.publicKeyValue = publicKeyValue;
   }

   /**
    * @return the publicKeyBase64Value
    */
   public String getPublicKeyBase64Value() {
      if (encoded != null) {
         return Base64.encode(encoded);
      }
      return publicKeyBase64Value;
   }

   /**
    * @param publicKeyBase64Value the publicKeyBase64Value to set
    */
   public void setPublicKeyBase64Value(String publicKeyBase64Value) {
      this.publicKeyBase64Value = publicKeyBase64Value;
   }

   /**
    * @return the filePath
    */
   public String getFilePath() {
      return filePath;
   }

   /**
    * @param filePath the filePath to set
    */
   public void setFilePath(String filePath) {
      this.filePath = filePath;
   }

   /**
    * @return the file
    */
   public InputStream getFile() {
      return file;
   }

   /**
    * @param file the file to set
    */
   public void setFile(InputStream file) {
      this.file = file;
   }

   /**
    * @return the encoded
    */
   public byte[] getEncoded() {
      return encoded;
   }

   /**
    * @param encoded the encoded to set
    */
   public void setEncoded(byte[] encoded) {
      this.encoded = encoded;
   }

   /**
    * @return the subjectCN
    */
   public String getSubjectCN() {
      return subjectCN;
   }

   /**
    * @param subjectCN the subjectCN to set
    */
   public void setSubjectCN(String subjectCN) {
      this.subjectCN = subjectCN;
   }

   /**
    * @return the issuerCN
    */
   public String getIssuerCN() {
      return issuerCN;
   }

   /**
    * @param issuerCN the issuerCN to set
    */
   public void setIssuerCN(String issuerCN) {
      this.issuerCN = issuerCN;
   }

   public String getSignature() {
      return signature;
   }

   public void setSignature(String signature) {
      this.signature = signature;
   }

   public String setPublicFormat() {
      return publicFormat;
   }

   public void setPublicFormat(String publicFormat) {
      this.publicFormat = publicFormat;
   }

   /**
    * @return the issuerC
    */
   public String getIssuerC() {
      return issuerC;
   }

   /**
    * @param issuerC the issuerC to set
    */
   public void setIssuerC(String issuerC) {
      this.issuerC = issuerC;
   }


   /**
    * @return the subjectC
    */
   public String getSubjectC() {
      return subjectC;
   }

   /**
    * @param subjectC the subjectC to set
    */
   public void setSubjectC(String subjectC) {
      this.subjectC = subjectC;
   }

 

   /**
    * @return the aliasName
    */
   public String getAliasName() {
      return aliasName;
   }

   /**
    * @param aliasName the aliasName to set
    */
   public void setAliasName(String aliasName) {
      this.aliasName = aliasName;
   }

   /**
    * @return the subKeyId
    */
   public String getSubKeyId() {
      return subKeyId;
   }

   /**
    * @param subKeyId the subKeyId to set
    */
   public void setSubKeyId(String subKeyId) {
      this.subKeyId = subKeyId;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final CertificateBean other = (CertificateBean) obj;
      if ((this.aliasName == null) ? (other.aliasName != null) : !this.aliasName.equals(other.aliasName)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 3;
      hash = 89 * hash + (this.aliasName != null ? this.aliasName.hashCode() : 0);
      return hash;
   }
   
   
   
}
