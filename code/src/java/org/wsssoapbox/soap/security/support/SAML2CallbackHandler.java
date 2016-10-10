/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wsssoapbox.soap.security.support;

import org.apache.ws.security.saml.ext.SAMLCallback;
import org.apache.ws.security.saml.ext.bean.KeyInfoBean;
import org.apache.ws.security.saml.ext.bean.SubjectBean;
import org.apache.ws.security.saml.ext.builder.SAML2Constants;
import org.opensaml.common.SAMLVersion;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.components.crypto.CryptoType;
import org.apache.ws.security.saml.ext.bean.AuthDecisionStatementBean;
import org.apache.ws.security.saml.ext.bean.AuthenticationStatementBean;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.security.support.CertConstants;

/**
 * A Callback Handler implementation for a SAML 2 assertion. By default it creates an
 * authentication assertion using Sender Vouches.
 */
public class SAML2CallbackHandler extends AbstractSAMLCallbackHandler {

   private static final Logger logger = LoggerFactory.getLogger(SAML2CallbackHandler.class);
   private SubjectBean subjectBean;

   public SAML2CallbackHandler() throws Exception {
      if (certs == null) { // default certificate
         String password = CertConstants.DEFAULT_KEY_KEYSTORE_PASSWORD;
         String aliasName = CertConstants.DEFAULT_KEY_KEYSTORE_ALIAS_NAME;
         String keyStoreFile = CertConstants.DEFAULT_KEY_STORE_FILE;
         Properties merlinProp = new Properties();
         merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", CertConstants.KEY_STORE_TYPE);
         merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
         merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
         Crypto crypto = CryptoFactory.getInstance(merlinProp);
         CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
         cryptoType.setAlias(aliasName);
         certs = crypto.getX509Certificates(cryptoType);

         logger.debug("Using default certificate........");
         if (certs != null) {
            logger.debug("Using default certificate  ........");
            System.out.println("Cert DN : " + certs[0].getIssuerDN());
         } else {
            logger.debug("Using default certificate is null ........");
         }
      }

      // default value
      subjectName = "WSSOAPBox Tool";
      subjectQualifier = "www.example.com";
      confirmationMethod = SAML2Constants.CONF_BEARER;
   }

   @Override
   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      for (int i = 0; i < callbacks.length; i++) {
         if (callbacks[i] instanceof SAMLCallback) {
            SAMLCallback callback = (SAMLCallback) callbacks[i];
            callback.setSamlVersion(SAMLVersion.VERSION_20);
            callback.setIssuer(issuer);
            if (conditions != null) {
               callback.setConditions(conditions);
            }

            SubjectBean subjBean = null;
            if (subjectBean != null) { // set by user
               if (statement.equals(Statement.AUTHN)) { // Authentication 
                  if (authBean.getSubject() != null) {
                     subjBean = authBean.getSubject();
                  }
               } else { // Authorization
                  if (authzBean.getSubject() != null) {
                     subjBean = authzBean.getSubject();
                  }
               }

               confirmationMethod = subjBean.getSubjectConfirmationMethod();
            }
            // default value
            subjBean = new SubjectBean(subjectName, subjectQualifier, confirmationMethod);

            if (this.subjectBean != null) { // set by user
               subjBean = this.subjectBean;
            }

            if (subjectNameIDFormat != null) {
               subjBean.setSubjectNameIDFormat(subjectNameIDFormat);
            }
            // for HOK case
            if (SAML2Constants.CONF_HOLDER_KEY.equals(confirmationMethod)) {
               try {
                  KeyInfoBean keyInfo = createKeyInfo();
                  subjBean.setKeyInfo(keyInfo);
               } catch (Exception ex) {
                  throw new IOException("Problem creating KeyInfo: " + ex.getMessage());
               }
            }
            authBean = getAuthBean();
            authzBean = getAuthzBean();
            callback.setSubject(subjBean);
            createAndSetStatement(null, callback);


         } else {
            throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
         }
      }
   }

   /**
    * @return the authBean
    */
   @Override
   public AuthenticationStatementBean getAuthBean() {
      return authBean;
   }

   /**
    * @param authBean the authBean to set
    */
   @Override
   public void setAuthBean(AuthenticationStatementBean authBean) {
      this.authBean = authBean;
   }

   /**
    * @return the subjectBean
    */
   public SubjectBean getSubjectBean() {
      return subjectBean;
   }

   /**
    * @param subjectBean the subjectBean to set
    */
   public void setSubjectBean(SubjectBean subjectBean) {
      this.subjectBean = subjectBean;
   }

   /**
    * @return the authzBean
    */
   @Override
   public AuthDecisionStatementBean getAuthzBean() {
      return authzBean;
   }

   /**
    * @param authzBean the authzBean to set
    */
   @Override
   public void setAuthzBean(AuthDecisionStatementBean authzBean) {
      this.authzBean = authzBean;
   }
}
