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
 * XMLPrettyPrinter.java
 * -------------------------------------------------------------------------
 */

package org.wsssoapbox.xml.util;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;

/**
 * An util class to prettify XML document
 * 
 * @author ofabre - eBM WebSourcing
 * 
 */
public class XMLPrettyPrinter {

    /**
     * Prettify the node into the output stream.
     * 
     * @param node
     * @param out
     * @throws Exception
     */
    public static void prettify(Node node, OutputStream out) throws Exception {
        Source source = new DOMSource(node);
        Source stylesheetSource = getStyleSheetSource();

        TransformerFactory tf = TransformerFactory.newInstance();
        Templates templates = tf.newTemplates(stylesheetSource);
        Transformer transformer = templates.newTransformer();
        transformer.transform(source, new StreamResult(out));
    }
    
    /**
     * Prettify the xml input stream into the output stream.
     * 
     * @param node
     * @param out
     * @throws Exception
     */
    public static void prettify(InputStream in, OutputStream out) throws Exception {
        StreamSource source = new StreamSource(in);
        Source stylesheetSource = getStyleSheetSource();

        TransformerFactory tf = TransformerFactory.newInstance();
        Templates templates = tf.newTemplates(stylesheetSource);
        Transformer transformer = templates.newTransformer();
        transformer.transform(source, new StreamResult(out));
    }

    private static Source getStyleSheetSource() {
        Source stylesheetSource = new StreamSource(XMLPrettyPrinter.class
                .getResourceAsStream("/prettyPrint.xsl"));
        return stylesheetSource;
    }
}
