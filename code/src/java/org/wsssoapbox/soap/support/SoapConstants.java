package org.wsssoapbox.soap.support;

import com.sun.xml.ws.security.opt.crypto.dsig.CanonicalizationMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ws.security.saml.ext.builder.SAML2Constants;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.signature.XMLSignature;

public final class SoapConstants {

   public final static String PARA_DEFAULT_VALUE = "?";
   public final static String PARA_DEFAULT_USER_AGENT = "WS-SSOAPBox Tool";
   public final static String PARA_DEFAULT_MAX_OCCURE = "1";
   public final static int PARA_DEFAULT_MIN_OCCURE = 0;
   public final static String PREFIX_DEFAULT_VALUE = "tns";
   public final static List<String> WELL_FORM_SCHEMAS = new ArrayList<String>();
   public final static Map SIGNATURE_ALGORITHM = new HashMap();
   public final static Map SIGNATURE_CANNONICALIZATION = new HashMap();
   public final static Map ENCRYPTION_CANNONICALIZATION = new HashMap();
   public final static Map PASSWORD_TYPE = new HashMap();
   public final static Map KEY_IDENTIFIER_TYPES = new HashMap();
   public final static Map SYMMETRIC_ENCODING_ALGORITHMS = new HashMap();
   public final static Map KEY_ENCRYPTION_ALGORITHMS = new HashMap();
   public final static Map ENCRYPTION_PARTS = new HashMap();
   public final static Map SAML_VERSIONS = new HashMap();
   public final static Map SAML_SUB_CONFIRMATION_METHODS = new HashMap();
   public final static Map SAML_NAMEID_FORMATS = new HashMap();
   public final static Map SAML_AUTH_CONTEXTS = new HashMap();
   public final static String SUBJECT_KEY_INDENTIFIER = "Subjectkey Identifier";
   public final static String X509_CERTIFICATE = "X509 Certificate";
   public final static String ISSUER_NAME = "Issuer Name and Serial Number";
   public final static String BINARY_SEC_TOKEN = "Binary Security Token";
   public final static String PASSWORD_TEXT = "PasswordText";
   public final static String PASSWORD_DIGEST = "PasswordDigest";
   public final static String SAML_SUB_CONF_HOLDER_KEY = "Holder-of-Key";
   public final static String SAML_SUB_CONF_SENDER_VOUCHES = "Sender-Vouches";
   public final static String SAML_SUB_CONF_CONF_BEARER = "Bearer";
   public final static String SAML_STATEMENT_AUTHN = "Authentication";
   public final static String SAML_STATEMENT_AUTHZ = "Autherization";
   public final static String SAML_STATEMENT_ATTR = "Attribute";
   public final static String SAML_SUBJECT_NAME = PARA_DEFAULT_USER_AGENT;

   static {
      WELL_FORM_SCHEMAS.add("http://schemas.xmlsoap.org/soap/envelope/");
      WELL_FORM_SCHEMAS.add("http://schemas.xmlsoap.org/soap/encoding/");
      WELL_FORM_SCHEMAS.add("http://www.w3.org/2001/XMLSchema-instance");
      WELL_FORM_SCHEMAS.add("http://www.w3.org/2001/XMLSchema");


      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_DSA, XMLSignature.ALGO_ID_SIGNATURE_DSA);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA384, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA384);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA512, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA512);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5, XMLSignature.ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_SIGNATURE_RSA_RIPEMD160, XMLSignature.ALGO_ID_SIGNATURE_RSA_RIPEMD160);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_SHA1, XMLSignature.ALGO_ID_MAC_HMAC_SHA1);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_SHA256, XMLSignature.ALGO_ID_MAC_HMAC_SHA256);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_SHA384, XMLSignature.ALGO_ID_MAC_HMAC_SHA384);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_SHA256, XMLSignature.ALGO_ID_MAC_HMAC_SHA256);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_SHA512, XMLSignature.ALGO_ID_MAC_HMAC_SHA512);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5, XMLSignature.ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5);
      SIGNATURE_ALGORITHM.put(XMLSignature.ALGO_ID_MAC_HMAC_RIPEMD160, XMLSignature.ALGO_ID_MAC_HMAC_RIPEMD160);



      SIGNATURE_CANNONICALIZATION.put(CanonicalizationMethod.INCLUSIVE, CanonicalizationMethod.INCLUSIVE);
      SIGNATURE_CANNONICALIZATION.put(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS);
      SIGNATURE_CANNONICALIZATION.put(CanonicalizationMethod.EXCLUSIVE, CanonicalizationMethod.EXCLUSIVE);
      SIGNATURE_CANNONICALIZATION.put(CanonicalizationMethod.EXCLUSIVE_WITH_COMMENTS, CanonicalizationMethod.EXCLUSIVE_WITH_COMMENTS);

      ENCRYPTION_CANNONICALIZATION.putAll(SIGNATURE_CANNONICALIZATION);


      PASSWORD_TYPE.put(PASSWORD_TEXT, PASSWORD_TEXT);
      PASSWORD_TYPE.put(PASSWORD_DIGEST, PASSWORD_DIGEST);

      KEY_IDENTIFIER_TYPES.put(BINARY_SEC_TOKEN, BINARY_SEC_TOKEN);
      KEY_IDENTIFIER_TYPES.put(ISSUER_NAME, ISSUER_NAME);
      KEY_IDENTIFIER_TYPES.put(X509_CERTIFICATE, X509_CERTIFICATE);
      KEY_IDENTIFIER_TYPES.put(SUBJECT_KEY_INDENTIFIER, SUBJECT_KEY_INDENTIFIER);


      SYMMETRIC_ENCODING_ALGORITHMS.put(XMLCipher.TRIPLEDES, XMLCipher.TRIPLEDES);
      SYMMETRIC_ENCODING_ALGORITHMS.put(XMLCipher.AES_128, XMLCipher.AES_128);
      SYMMETRIC_ENCODING_ALGORITHMS.put(XMLCipher.AES_192, XMLCipher.AES_192);
      SYMMETRIC_ENCODING_ALGORITHMS.put(XMLCipher.AES_256, XMLCipher.AES_256);


      KEY_ENCRYPTION_ALGORITHMS.put(XMLCipher.RSA_v1dot5, XMLCipher.RSA_v1dot5);
      KEY_ENCRYPTION_ALGORITHMS.put(XMLCipher.RSA_OAEP, XMLCipher.RSA_OAEP);

      ENCRYPTION_PARTS.put("Content", "Content");
      ENCRYPTION_PARTS.put("Element", "Element");


      SAML_VERSIONS.put("2.0", "2.0");


      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_X509_SUBJECT_NAME, SAML2Constants.NAMEID_FORMAT_X509_SUBJECT_NAME);
      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_EMAIL_ADDRESS, SAML2Constants.NAMEID_FORMAT_EMAIL_ADDRESS);
      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_ENTITY, SAML2Constants.NAMEID_FORMAT_ENTITY);
      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_PERSISTENT, SAML2Constants.NAMEID_FORMAT_PERSISTENT);
      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_TRANSIENT, SAML2Constants.NAMEID_FORMAT_TRANSIENT);
      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_WINDOWS_DQN, SAML2Constants.NAMEID_FORMAT_WINDOWS_DQN);
      SAML_NAMEID_FORMATS.put(SAML2Constants.NAMEID_FORMAT_KERBEROS, SAML2Constants.NAMEID_FORMAT_KERBEROS);

      SAML_SUB_CONFIRMATION_METHODS.put(SAML_SUB_CONF_CONF_BEARER, SAML_SUB_CONF_CONF_BEARER);
      SAML_SUB_CONFIRMATION_METHODS.put(SAML_SUB_CONF_HOLDER_KEY, SAML_SUB_CONF_HOLDER_KEY);
      SAML_SUB_CONFIRMATION_METHODS.put(SAML_SUB_CONF_SENDER_VOUCHES, SAML_SUB_CONF_SENDER_VOUCHES);

      SAML_AUTH_CONTEXTS.put("Password", "Password");
      SAML_AUTH_CONTEXTS.put("X509", "X509");
      SAML_AUTH_CONTEXTS.put("PGP", "PGP");
   }
}
