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
 * ContentType.java
 * -------------------------------------------------------------------------
 */

package org.wsssoapbox.xml.util;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of Dragon supported content types
 * 
 * @author ofabre - eBM Websourcing
 * 
 */
public enum ContentType {
    DOC("application/msword"), PDF("application/pdf"), HTML("text/html"), XML("text/xml"), XLS(
            "application/vnd.ms-excel"), PPT("application/vnd.ms-powerpoint"), TXT("text/plain"),
    RTF("application/rtf"), ODT("application/vnd.oasis.opendocument.text"), ODS(
            "application/vnd.oasis.opendocument.spreadsheet"), ODP(
            "application/vnd.oasis.opendocument.presentation");
    private final String type;

    private static final Map<String, ContentType> stringToEnum = new HashMap<String, ContentType>();

    static {
        for (ContentType type : values()) {
            stringToEnum.put(type.toString(), type);
        }
    }

    private ContentType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

    /**
     * Return the ContentType related to the given string or null if it isn't
     * supported
     * 
     * @param type
     *            the string representing a potential ContentType
     * @return the {@link ContentType} enum related to the given String
     */
    public static ContentType fromString(String type) {
        return stringToEnum.get(type);
    }

    /**
     * Return the commonly acceptable file suffix for a given
     * {@link ContentType} <br/>
     * Example: if ContentType.XML then suffix = ".xml"
     * 
     * @param type
     *            a {@link ContentType}
     * @return the corresponding file suffix
     */
    public static String getRelatedSuffix(ContentType type) {
        String suffix = null;
        if (type != null) {
            switch (type) {
                case XML:
                    suffix = ".xml";
                    break;
                case DOC:
                    suffix = ".doc";
                    break;
                case HTML:
                    suffix = ".html";
                    break;
                case PDF:
                    suffix = ".pdf";
                    break;
                case XLS:
                    suffix = ".xls";
                    break;
                case PPT:
                    suffix = ".ppt";
                    break;
                case TXT:
                    suffix = ".txt";
                    break;
                case RTF:
                    suffix = ".rtf";
                    break;
                case ODT:
                    suffix = ".odt";
                    break;
                case ODP:
                    suffix = ".odp";
                    break;
                case ODS:
                    suffix = ".ods";
                    break;
                default:
                    suffix = null;
            }
        }
        return suffix;
    }

}
