/**
 * Dragon - SOA Governance Platform.
 * Copyright (c) 2008 EBM Websourcing, http://www.ebmwebsourcing.com/
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * -------------------------------------------------------------------------
 * WSDLValidator.java
 * -------------------------------------------------------------------------
 */

package org.wsssoapbox.xml.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.ow2.easywsdl.wsdl.impl.wsdl11.Constants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author ofabre - eBM Websourcing
 * 
 */
public class WSDLValidator {

    private static Validator wsdl11Validator;

    private static Validator wsdl20Validator;

    /**
     * @param args
     * @throws SAXException
     * @throws IOException
     */
    public static void main(String[] args) throws SAXException, IOException {
        createValidators();

        // InputStream inputStream
        // =WSDLValidator.class.getResourceAsStream("/globalweather.wsdl");
        InputStream inputStream = WSDLValidator.class.getResourceAsStream("/wsdl.wsdl");

        Document document = XMLUtils.loadDocument(inputStream);

        validateWSDL(document);

    }

    public static void validateWSDL(Document wsdl) throws SAXException, IOException {
        // Identify WSDL version and create validator
        if ((wsdl.getDocumentElement().getLocalName()
                .equals(org.ow2.easywsdl.wsdl.impl.wsdl20.Constants.WSDL20_ROOT_TAG))
                && (wsdl.getDocumentElement().getNamespaceURI()
                        .equals(org.ow2.easywsdl.wsdl.impl.wsdl20.Constants.WSDL_20_NAMESPACE))) {
            wsdl20Validator.validate(new DOMSource(wsdl));
        }
        if ((wsdl.getDocumentElement().getLocalName()
                .equals(org.ow2.easywsdl.wsdl.impl.wsdl11.Constants.WSDL11_ROOT_TAG))
                && (wsdl.getDocumentElement().getNamespaceURI()
                        .equals(org.ow2.easywsdl.wsdl.impl.wsdl11.Constants.WSDL_11_NAMESPACE))) {
            wsdl11Validator.validate(new DOMSource(wsdl));
        }
    }

    private static void createValidators() throws SAXException {
        // Create a SchemaFactory capable of understanding WXS schemas.
        System.setProperty("javax.xml.validation.SchemaFactory:http://www.w3.org/2001/XMLSchema",
                "org.apache.xerces.jaxp.validation.XMLSchemaFactory");

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.XML_NS_URI);

        InputStream schemaStream11 = WSDLValidator.class.getResourceAsStream("/"
                + Constants.XSD_WSDL_11);
        InputStream schemaStream20 = WSDLValidator.class.getResourceAsStream("/"
                + org.ow2.easywsdl.wsdl.impl.wsdl20.Constants.XSD_WSDL_20);

        Source schemaFile11 = new StreamSource(schemaStream11);
        Source schemaFile20 = new StreamSource(schemaStream20);

        Schema schema11 = factory.newSchema(schemaFile11);
        Schema schema20 = factory.newSchema(schemaFile20);

        // Create a Validator object, which can be used to validate
        // an instance document.
        wsdl11Validator = schema11.newValidator();
        wsdl20Validator = schema20.newValidator();
    }

}
