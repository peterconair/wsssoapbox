/**
 * PETALS - PETALS Services Platform.
 * Copyright (c) 2005-2006 EBM WebSourcing, http://www.ebmwebsourcing.com/
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
 * $Id: SourceHelper.java 94 2006-03-26 17:11:05Z alouis $
 * -------------------------------------------------------------------------
 */

package org.wsssoapbox.util.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Initial developer(s): Adrien LOUIS
 * 
 * @author alouis, ofabre - EBM Websourcing
 * 
 */
public final class StringHelper {

    /**
     * Test the equality of the specified strings. test is : ( (a==b==null) ||
     * a.equals(b) )
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equal(final String a, final String b) {
        boolean result;
        if (a == null) {
            result = b == null;
        } else {
            result = a.equals(b);
        }
        return result;
    }

    /**
     * Same as equal, with ignoreCase
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equalIgnoreCase(final String a, final String b) {
        boolean result;
        if (a == null) {
            result = b == null;
        } else {
            result = a.equalsIgnoreCase(b);
        }
        return result;
    }

    /**
     * TODO use Tokenizer Extract in the given String the value corresponding to
     * the given attribute: ".......<ATTRIBUTE>=VALUE<SEPARATOR>......". - not
     * case sensitive - return the first value for the first matching attribute
     * - the separator between ATT and VALUE must be : "="
     * 
     * @param string
     *            can be null or empty (return null)
     * @param attribute
     *            can be null or empty (return null)
     * @param separator
     *            (";"," ","&"...) can be null or empty. in this case, take the
     *            end of the String : "ATT=<VALUE etc etc>"
     * @return the found attribute value, null otherwise
     */
    public static String extractValueForAttribute(final String string, final String attribute,
            final String separator) {
        String result = null;

        // check string and att are not empty
        if (!isNullOrEmpty(string) && !isNullOrEmpty(attribute)) {
            // find start index
            int start = string.indexOf(attribute);

            if ((start >= 0) && (start < string.length())) {
                start += attribute.length() + 1;

                if (start < string.length()) {
                    // find end index
                    int end = 0;

                    if (isNullOrEmpty(separator)) {
                        // no separator, end = end of string
                        end = string.length();
                    } else {
                        // a separator is specified, find end index
                        end = string.indexOf(separator, start);

                        if (end < 0) {
                            end = string.length();
                        }
                    }
                    result = string.substring(start, end);
                }
            }
        }
        return result;
    }

    /**
     * Return true if the String is null or its size is 0.
     * 
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(final String s) {
        return (s == null) || s.trim().equals("");
    }

    /**
     * Split the given String path into String path elements. For example, the
     * following path "/foo/bar" will be split into two parts "foo" and "bar",
     * returned in List. If the given path includes empty path elements (Ex:
     * "/foo//bar//"), the returned List doesn't contain these elements (for
     * this exemple result will be "foo","bar").
     * 
     * @param path
     *            the String path to split, can be null or empty (return an
     *            empty ArrayList)
     * @return a List of String path elements, cannot be null, can be empty
     */
    public static List<String> splitPathElements(final String path) {
        final List<String> pathElements = new ArrayList<String>();

        if (!isNullOrEmpty(path)) {
            final StringTokenizer tokenizer = new StringTokenizer(path, "/");

            while (tokenizer.hasMoreTokens()) {
                pathElements.add(tokenizer.nextToken());
            }
        }
        return pathElements;
    }

    /**
     * Replace an old character by a new one in the given string
     * 
     * @param line
     *            the String to modify
     * @param oldChar
     *            the character to be replaced
     * @param newChar
     *            the replacement character
     * @return the new String with character replaced
     */
    public static String replace(String line, char oldChar, char newChar) {
        String result = "";
        if (line != null) {
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == oldChar) {
                    result += newChar;
                } else {
                    result += line.charAt(j);
                }
            }
        }
        return result;
    }

    public static String toString(final InputStream inputStream) throws IOException {
        final StringWriter writer = new StringWriter();
        final InputStreamReader streamReader = new InputStreamReader(inputStream);
        final BufferedReader buffer = new BufferedReader(streamReader);
        String line = "";
        while (null != (line = buffer.readLine())) {
            writer.write(line);
        }
        return writer.toString();
    }

    private StringHelper() {
        super();
    }
}
