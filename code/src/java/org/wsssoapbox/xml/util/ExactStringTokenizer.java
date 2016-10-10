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
 * ExactStringTokenizer.java
 * -------------------------------------------------------------------------
 */
package org.wsssoapbox.xml.util;

import java.util.ArrayList;

/**
 * Works as Java String Tokenizer but doesn't ignore empty tokens.<br>
 * Example:
 * <code>new StringTokenizer(";a;;bb;ccc;", ";") --> ["a", "bb", "ccc"]</code><br>
 * <code>new ExactStringTokenizer(";a;;bb;ccc;", ";") --> ["", "a", "", "bb", "ccc", ""]</code>
 */
public class ExactStringTokenizer {
    private static String[] STRING_ARRAY = {};

    public static String[] split(String iString, char delim) {
        ArrayList<String> ret = new ArrayList<String>();
        ExactStringTokenizer st = new ExactStringTokenizer(iString, delim);
        while (st.hasMoreTokens())
            ret.add(st.nextToken());
        return ret.toArray(STRING_ARRAY);
    }

    private String _str;

    private int _curTokenPos;

    private int _nextDelimPos;

    private char _delim;

    public ExactStringTokenizer(String iString, char delim) {
        _str = iString;
        _delim = delim;
        reinit();
    }

    private void reinit() {
        _curTokenPos = 0;
        _nextDelimPos = Integer.MIN_VALUE;
    }

    public boolean hasMoreTokens() {
        if (_nextDelimPos == Integer.MIN_VALUE)
            _nextDelimPos = _str.indexOf(_delim, _curTokenPos);
        return _nextDelimPos >= 0 || _curTokenPos <= _str.length();
    }

    public String nextToken() {
        if (!hasMoreTokens())
            return null;
        String ret = null;
        if (_nextDelimPos >= 0) {
            ret = _str.substring(_curTokenPos, _nextDelimPos);
            _curTokenPos = _nextDelimPos + 1;
            _nextDelimPos = Integer.MIN_VALUE;
        } else {
            // --- end of string
            ret = _str.substring(_curTokenPos);
            _curTokenPos = _str.length() + 1;
        }
        return ret;
    }
}
