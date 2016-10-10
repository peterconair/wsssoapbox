/*
 * $Id: UsernameTokenTest.java,v 1.3 2008-07-03 05:29:26 ofung Exp $
 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
/**
 * Tests to verify security headers for Username Token profile are
 * generated correctly.
 *
 * @author Manveen Kaur (manveen.kaur@sun.com)
 */
package soap.security.usernametoken;

import com.sun.xml.wss.XWSSecurityException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.junit.Test;
import static org.junit.Assert.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.xml.wss.core.UsernameToken;
import com.sun.xml.wss.impl.MessageConstants;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import java.util.Iterator;
import org.junit.Before;
import org.wsssoapbox.xml.util.XMLUtils;

public class UsernameTokenTest {

   SOAPMessage soapMessage;
   SOAPEnvelope envelope;
   SecurableSoapMessage secureMessage;

   @Before
   public void setUp() throws Exception {

      soapMessage = MessageFactory.newInstance().createMessage();
      envelope = soapMessage.getSOAPPart().getEnvelope();
      secureMessage = new SecurableSoapMessage(soapMessage);
      System.out.println("Original Soap message :");
      XMLUtils.print(secureMessage);
      System.out.println("\n**************************** \n");
   }

   public void testBasicConstructor() throws XWSSecurityException,Exception {

         UsernameToken usernameToken =
                 new UsernameToken(secureMessage.getSOAPPart(), "Ron", "noR");
         Element tokenElement = usernameToken.getAsSoapElement();

         XMLUtils.print(secureMessage);

         assertEquals("UsernameToken", tokenElement.getLocalName());
         assertEquals("wsse", tokenElement.getPrefix());
      
   }

   public void testSetGetUsername() throws Exception, XWSSecurityException {

      UsernameToken usernameToken =
              new UsernameToken(secureMessage.getSOAPPart(), "Ron", "noR");

      System.out.println("Username : " + usernameToken.getUsername());
      System.out.println("Pawword : " + usernameToken.getPassword());

      assertEquals(usernameToken.getUsername(), "Ron");
   }

   public void testSetGetPassword() throws XWSSecurityException {
      UsernameToken usernameToken =
              new UsernameToken(secureMessage.getSOAPPart(), "Ron", "noR");

      assertEquals(usernameToken.getPassword(), "noR");
   }

   public void testSetEmptyPassword() throws XWSSecurityException {
      UsernameToken usernameToken =
              new UsernameToken(secureMessage.getSOAPPart(), "Ron", null);
      assertEquals(usernameToken.getPassword(), null);
   }

   @Test
   public void testCreateNonce() throws XWSSecurityException, SOAPException {

      // base 64 nonce encoding
      UsernameToken usernameToken =
              new UsernameToken(
              secureMessage.getSOAPPart(),
              "Peter",
              "1234",
              true,
              true);

      assertTrue(usernameToken.getNonce() != null);

      Element tokenElement = usernameToken.getAsSoapElement();

      NodeList list = tokenElement.getElementsByTagName("wsse:Nonce");
      assertEquals(1, list.getLength());

      list = tokenElement.getElementsByTagName("wsu:Created");
      assertEquals(0, list.getLength());

      System.out.println("UserName : " + usernameToken.getUsername());
      System.out.println("Password : " + usernameToken.getPassword());
      System.out.println("Password Digest : " + usernameToken.getPasswordDigest());
      System.out.println("Password Type : " + usernameToken.getPasswordType());
      System.out.println("Nounce : " + usernameToken.getNonce());
      System.out.println("Created : " + usernameToken.getCreated());
      System.out.println("Nonce Encoding Type : " + usernameToken.getNonceEncodingType());


      Iterator it = secureMessage.getSOAPHeader().extractAllHeaderElements();
      while (it.hasNext()) {
         System.out.println(it.next());
      }

      assertEquals(MessageConstants.BASE64_ENCODING_NS,
              usernameToken.getNonceEncodingType());





   }

   public void testCreateNonceAndCreated() throws XWSSecurityException {

      // do not create nonce.
      UsernameToken usernameToken =
              new UsernameToken(
              secureMessage.getSOAPPart(),
              "Ron",
              "noR",
              true,
              true);

      Element tokenElement = usernameToken.getAsSoapElement();

      NodeList list = tokenElement.getElementsByTagName("wsse:Nonce");
      assertEquals(1, list.getLength());

      list = tokenElement.getElementsByTagName("wsu:Created");
      assertEquals(0, list.getLength());
   }
}
