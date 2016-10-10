/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatech.payment;

import org.jdom.Element;
import org.jdom.Namespace;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.jdom.xpath.XPath;

@org.springframework.ws.server.endpoint.annotation.Endpoint
public class PaymentEndpoint extends AbstractJDomPayloadEndpoint {

    private static final Namespace NS = Namespace.getNamespace("tns", "http://javatech.com/payment/ws/schema/oss");

    @Override
    protected Element invokeInternal(Element requestElement) throws Exception {
        // Obtain the spanner id from the message

        /*
        XPath xpath = XPath.newInstance("//tns:amount");
        xpath.addNamespace(NS);
        String amount = xpath.valueOf(requestElement);
        
        xpath = XPath.newInstance("//tns:cardNumber");
        xpath.addNamespace(NS);
        String cardNumber = xpath.valueOf(requestElement);
        xpath = XPath.newInstance("//tns:securityCode");
        xpath.addNamespace(NS);
        String securityCode = xpath.valueOf(requestElement);
        xpath = XPath.newInstance("//tns:expirationDate");
        xpath.addNamespace(NS);
        String expirationDate = xpath.valueOf(requestElement);
        xpath = XPath.newInstance("//tns:nameOnCard");
        xpath.addNamespace(NS);
        String nameOnCard = xpath.valueOf(requestElement);
         */


        // Assemble response
        Element response = new Element("paymentResponse", NS);
        response.addContent(new Element("code", NS).setText("Compleated"));
        response.addContent(new Element("description", NS).setText("Your payment successfully."));
        response.addContent(new Element("referenceCode", NS).setText("xxxxxxxxxxx"));

        return response;
    }
}
