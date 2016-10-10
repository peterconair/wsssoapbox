/**
 * Dragon - SOA Governance Platform.
 * Copyright (c) 2009 EBM Websourcing, http://www.ebmwebsourcing.com/
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
 * FileReaderUtil.java
 * -------------------------------------------------------------------------
 */

package org.wsssoapbox.xml.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * @author ofabre
 * 
 */
public class FileReaderUtil {

    public static List<String[]> readLines(InputStream file, String linePartSeparator)
            throws IOException {
        List<String[]> result = new ArrayList<String[]>();

        List<String> lines = IOUtils.readLines(file);
        if (lines != null) {
            for (String line : lines) {
                String[] lineParts = line.split(linePartSeparator);
                result.add(lineParts);
            }
        }

        return result;
    }

    public static List<String[]> readLines(byte[] file, String linePartSeparator) throws IOException {
        return readLines(new ByteArrayInputStream(file), linePartSeparator);
    }

    public static List<String[]> readLines(File file, String linePartSeparator)
            throws FileNotFoundException, IOException {
        return readLines(new FileInputStream(file), linePartSeparator);
    }
}
