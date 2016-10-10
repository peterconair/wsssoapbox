/*******************************************************************************
 * Copyright (c) 2003 Object Knowledge Center B.V. (OKC), Object Knowledge
 * Center Group Ltd. (OKC-Group).
 *
 * This file is part of the Raw SOAP message sender plugin for Eclipse.

 * The Raw SOAP message sender plugin for Eclipse is free software;
 * you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version.
 * 
 * The Raw SOAP message sender plugin for Eclipse is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the Raw SOAP message sender plugin for Eclipse;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  
 *******************************************************************************/

package util;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * TODO: Comment here
 * 
 * @author E.A. van Rooijen
 * @version $Revision: 1.1 $ $Date: 2003/09/29 20:41:39 $
 * 
 * @pbt.cvs.log
 * 
 * $Log: EntityConverter.java,v $
 * Revision 1.1  2003/09/29 20:41:39  basilisk
 * *** empty log message ***
 *
 * Revision 1.1  2003/09/21 16:31:39  u1077
 * *** empty log message ***
 *
 * 
 */
public class EntityConverter {

	/**
	 * convert a single char to its equivalent 
	 * HTML entity.  Ordinary chars are not changed.
	 * 
	 * @param c Char to convert
	 * @return equivalent string eg. &amp;, null means leave char as is.
	 */
	public static String escapeString(String stringToEscape) {
		char[] arrayToEscape = stringToEscape.toCharArray();
		StringBuffer escapedStringBuffer = new StringBuffer(stringToEscape.length());
		for (int i = 0; i < arrayToEscape.length; i++) {
			switch (arrayToEscape[i]) {
				default :
					if (arrayToEscape[i] < 127) {
						// leave alone as equivalent string.
						escapedStringBuffer.append(arrayToEscape[i]);
						// faster than String.valueOf( c ).intern();
					} else {
						// use the &#nnn; form
						escapedStringBuffer.append("&#" + Integer.toString(arrayToEscape[i]) + ";");
					}
					break;
				case   34: escapedStringBuffer.append("&quot;"); break;
				case   38: escapedStringBuffer.append("&amp;"); break;
				case   60: escapedStringBuffer.append("&lt;"); break;
				case   62: escapedStringBuffer.append("&gt;"); break;
				case  160: escapedStringBuffer.append("&nbsp;"); break;
				case  161: escapedStringBuffer.append("&iexcl;"); break;
				case  162: escapedStringBuffer.append("&cent;"); break;
				case  163: escapedStringBuffer.append("&pound;"); break;
				case  164: escapedStringBuffer.append("&curren;"); break;
				case  165: escapedStringBuffer.append("&yen;"); break;
				case  166: escapedStringBuffer.append("&brvbar;"); break;
				case  167: escapedStringBuffer.append("&sect;"); break;
				case  168: escapedStringBuffer.append("&uml;"); break;
				case  169: escapedStringBuffer.append("&copy;"); break;
				case  170: escapedStringBuffer.append("&ordf;"); break;
				case  171: escapedStringBuffer.append("&laquo;"); break;
				case  172: escapedStringBuffer.append("&not;"); break;
				case  173: escapedStringBuffer.append("&shy;"); break;
				case  174: escapedStringBuffer.append("&reg;"); break;
				case  175: escapedStringBuffer.append("&macr;"); break;
				case  176: escapedStringBuffer.append("&deg;"); break;
				case  177: escapedStringBuffer.append("&plusmn;"); break;
				case  178: escapedStringBuffer.append("&sup2;"); break;
				case  179: escapedStringBuffer.append("&sup3;"); break;
				case  180: escapedStringBuffer.append("&acute;"); break;
				case  181: escapedStringBuffer.append("&micro;"); break;
				case  182: escapedStringBuffer.append("&para;"); break;
				case  183: escapedStringBuffer.append("&middot;"); break;
				case  184: escapedStringBuffer.append("&cedil;"); break;
				case  185: escapedStringBuffer.append("&sup1;"); break;
				case  186: escapedStringBuffer.append("&ordm;"); break;
				case  187: escapedStringBuffer.append("&raquo;"); break;
				case  188: escapedStringBuffer.append("&frac14;"); break;
				case  189: escapedStringBuffer.append("&frac12;"); break;
				case  190: escapedStringBuffer.append("&frac34;"); break;
				case  191: escapedStringBuffer.append("&iquest;"); break;
				case  192: escapedStringBuffer.append("&Agrave;"); break;
				case  193: escapedStringBuffer.append("&Aacute;"); break;
				case  194: escapedStringBuffer.append("&Acirc;"); break;
				case  195: escapedStringBuffer.append("&Atilde;"); break;
				case  196: escapedStringBuffer.append("&Auml;"); break;
				case  197: escapedStringBuffer.append("&Aring;"); break;
				case  198: escapedStringBuffer.append("&AElig;"); break;
				case  199: escapedStringBuffer.append("&Ccedil;"); break;
				case  200: escapedStringBuffer.append("&Egrave;"); break;
				case  201: escapedStringBuffer.append("&Eacute;"); break;
				case  202: escapedStringBuffer.append("&Ecirc;"); break;
				case  203: escapedStringBuffer.append("&Euml;"); break;
				case  204: escapedStringBuffer.append("&Igrave;"); break;
				case  205: escapedStringBuffer.append("&Iacute;"); break;
				case  206: escapedStringBuffer.append("&Icirc;"); break;
				case  207: escapedStringBuffer.append("&Iuml;"); break;
				case  208: escapedStringBuffer.append("&ETH;"); break;
				case  209: escapedStringBuffer.append("&Ntilde;"); break;
				case  210: escapedStringBuffer.append("&Ograve;"); break;
				case  211: escapedStringBuffer.append("&Oacute;"); break;
				case  212: escapedStringBuffer.append("&Ocirc;"); break;
				case  213: escapedStringBuffer.append("&Otilde;"); break;
				case  214: escapedStringBuffer.append("&Ouml;"); break;
				case  215: escapedStringBuffer.append("&times;"); break;
				case  216: escapedStringBuffer.append("&Oslash;"); break;
				case  217: escapedStringBuffer.append("&Ugrave;"); break;
				case  218: escapedStringBuffer.append("&Uacute;"); break;
				case  219: escapedStringBuffer.append("&Ucirc;"); break;
				case  220: escapedStringBuffer.append("&Uuml;"); break;
				case  221: escapedStringBuffer.append("&Yacute;"); break;
				case  222: escapedStringBuffer.append("&THORN;"); break;
				case  223: escapedStringBuffer.append("&szlig;"); break;
				case  224: escapedStringBuffer.append("&agrave;"); break;
				case  225: escapedStringBuffer.append("&aacute;"); break;
				case  226: escapedStringBuffer.append("&acirc;"); break;
				case  227: escapedStringBuffer.append("&atilde;"); break;
				case  228: escapedStringBuffer.append("&auml;"); break;
				case  229: escapedStringBuffer.append("&aring;"); break;
				case  230: escapedStringBuffer.append("&aelig;"); break;
				case  231: escapedStringBuffer.append("&ccedil;"); break;
				case  232: escapedStringBuffer.append("&egrave;"); break;
				case  233: escapedStringBuffer.append("&eacute;"); break;
				case  234: escapedStringBuffer.append("&ecirc;"); break;
				case  235: escapedStringBuffer.append("&euml;"); break;
				case  236: escapedStringBuffer.append("&igrave;"); break;
				case  237: escapedStringBuffer.append("&iacute;"); break;
				case  238: escapedStringBuffer.append("&icirc;"); break;
				case  239: escapedStringBuffer.append("&iuml;"); break;
				case  240: escapedStringBuffer.append("&eth;"); break;
				case  241: escapedStringBuffer.append("&ntilde;"); break;
				case  242: escapedStringBuffer.append("&ograve;"); break;
				case  243: escapedStringBuffer.append("&oacute;"); break;
				case  244: escapedStringBuffer.append("&ocirc;"); break;
				case  245: escapedStringBuffer.append("&otilde;"); break;
				case  246: escapedStringBuffer.append("&ouml;"); break;
				case  247: escapedStringBuffer.append("&divide;"); break;
				case  248: escapedStringBuffer.append("&oslash;"); break;
				case  249: escapedStringBuffer.append("&ugrave;"); break;
				case  250: escapedStringBuffer.append("&uacute;"); break;
				case  251: escapedStringBuffer.append("&ucirc;"); break;
				case  252: escapedStringBuffer.append("&uuml;"); break;
				case  253: escapedStringBuffer.append("&yacute;"); break;
				case  254: escapedStringBuffer.append("&thorn;"); break;
				case  255: escapedStringBuffer.append("&yuml;"); break;
				case  338: escapedStringBuffer.append("&OElig;"); break;
				case  339: escapedStringBuffer.append("&oelig;"); break;
				case  352: escapedStringBuffer.append("&Scaron;"); break;
				case  353: escapedStringBuffer.append("&scaron;"); break;
				case  376: escapedStringBuffer.append("&Yuml;"); break;
				case  402: escapedStringBuffer.append("&fnof;"); break;
				case  710: escapedStringBuffer.append("&circ;"); break;
				case  732: escapedStringBuffer.append("&tilde;"); break;
				case  913: escapedStringBuffer.append("&Alpha;"); break;
				case  914: escapedStringBuffer.append("&Beta;"); break;
				case  915: escapedStringBuffer.append("&Gamma;"); break;
				case  916: escapedStringBuffer.append("&Delta;"); break;
				case  917: escapedStringBuffer.append("&Epsilon;"); break;
				case  918: escapedStringBuffer.append("&Zeta;"); break;
				case  919: escapedStringBuffer.append("&Eta;"); break;
				case  920: escapedStringBuffer.append("&Theta;"); break;
				case  921: escapedStringBuffer.append("&Iota;"); break;
				case  922: escapedStringBuffer.append("&Kappa;"); break;
				case  923: escapedStringBuffer.append("&Lambda;"); break;
				case  924: escapedStringBuffer.append("&Mu;"); break;
				case  925: escapedStringBuffer.append("&Nu;"); break;
				case  926: escapedStringBuffer.append("&Xi;"); break;
				case  927: escapedStringBuffer.append("&Omicron;"); break;
				case  928: escapedStringBuffer.append("&Pi;"); break;
				case  929: escapedStringBuffer.append("&Rho;"); break;
				case  931: escapedStringBuffer.append("&Sigma;"); break;
				case  932: escapedStringBuffer.append("&Tau;"); break;
				case  933: escapedStringBuffer.append("&Upsilon;"); break;
				case  934: escapedStringBuffer.append("&Phi;"); break;
				case  935: escapedStringBuffer.append("&Chi;"); break;
				case  936: escapedStringBuffer.append("&Psi;"); break;
				case  937: escapedStringBuffer.append("&Omega;"); break;
				case  945: escapedStringBuffer.append("&alpha;"); break;
				case  946: escapedStringBuffer.append("&beta;"); break;
				case  947: escapedStringBuffer.append("&gamma;"); break;
				case  948: escapedStringBuffer.append("&delta;"); break;
				case  949: escapedStringBuffer.append("&epsilon;"); break;
				case  950: escapedStringBuffer.append("&zeta;"); break;
				case  951: escapedStringBuffer.append("&eta;"); break;
				case  952: escapedStringBuffer.append("&theta;"); break;
				case  953: escapedStringBuffer.append("&iota;"); break;
				case  954: escapedStringBuffer.append("&kappa;"); break;
				case  955: escapedStringBuffer.append("&lambda;"); break;
				case  956: escapedStringBuffer.append("&mu;"); break;
				case  957: escapedStringBuffer.append("&nu;"); break;
				case  958: escapedStringBuffer.append("&xi;"); break;
				case  959: escapedStringBuffer.append("&omicron;"); break;
				case  960: escapedStringBuffer.append("&pi;"); break;
				case  961: escapedStringBuffer.append("&rho;"); break;
				case  962: escapedStringBuffer.append("&sigmaf;"); break;
				case  963: escapedStringBuffer.append("&sigma;"); break;
				case  964: escapedStringBuffer.append("&tau;"); break;
				case  965: escapedStringBuffer.append("&upsilon;"); break;
				case  966: escapedStringBuffer.append("&phi;"); break;
				case  967: escapedStringBuffer.append("&chi;"); break;
				case  968: escapedStringBuffer.append("&psi;"); break;
				case  969: escapedStringBuffer.append("&omega;"); break;
				case  977: escapedStringBuffer.append("&thetasym;"); break;
				case  978: escapedStringBuffer.append("&upsih;"); break;
				case  982: escapedStringBuffer.append("&piv;"); break;
				case 8194: escapedStringBuffer.append("&ensp;"); break;
				case 8195: escapedStringBuffer.append("&emsp;"); break;
				case 8201: escapedStringBuffer.append("&thinsp;"); break;
				case 8204: escapedStringBuffer.append("&zwnj;"); break;
				case 8205: escapedStringBuffer.append("&zwj;"); break;
				case 8206: escapedStringBuffer.append("&lrm;"); break;
				case 8207: escapedStringBuffer.append("&rlm;"); break;
				case 8211: escapedStringBuffer.append("&ndash;"); break;
				case 8212: escapedStringBuffer.append("&mdash;"); break;
				case 8216: escapedStringBuffer.append("&lsquo;"); break;
				case 8217: escapedStringBuffer.append("&rsquo;"); break;
				case 8218: escapedStringBuffer.append("&sbquo;"); break;
				case 8220: escapedStringBuffer.append("&ldquo;"); break;
				case 8221: escapedStringBuffer.append("&rdquo;"); break;
				case 8222: escapedStringBuffer.append("&bdquo;"); break;
				case 8224: escapedStringBuffer.append("&dagger;"); break;
				case 8225: escapedStringBuffer.append("&Dagger;"); break;
				case 8226: escapedStringBuffer.append("&bull;"); break;
				case 8230: escapedStringBuffer.append("&hellip;"); break;
				case 8240: escapedStringBuffer.append("&permil;"); break;
				case 8242: escapedStringBuffer.append("&prime;"); break;
				case 8243: escapedStringBuffer.append("&Prime;"); break;
				case 8249: escapedStringBuffer.append("&lsaquo;"); break;
				case 8250: escapedStringBuffer.append("&rsaquo;"); break;
				case 8254: escapedStringBuffer.append("&oline;"); break;
				case 8260: escapedStringBuffer.append("&frasl;"); break;
				case 8364: escapedStringBuffer.append("&euro;"); break;
				case 8465: escapedStringBuffer.append("&image;"); break;
				case 8472: escapedStringBuffer.append("&weierp;"); break;
				case 8476: escapedStringBuffer.append("&real;"); break;
				case 8482: escapedStringBuffer.append("&trade;"); break;
				case 8501: escapedStringBuffer.append("&alefsym;"); break;
				case 8592: escapedStringBuffer.append("&larr;"); break;
				case 8593: escapedStringBuffer.append("&uarr;"); break;
				case 8594: escapedStringBuffer.append("&rarr;"); break;
				case 8595: escapedStringBuffer.append("&darr;"); break;
				case 8596: escapedStringBuffer.append("&harr;"); break;
				case 8629: escapedStringBuffer.append("&crarr;"); break;
				case 8656: escapedStringBuffer.append("&lArr;"); break;
				case 8657: escapedStringBuffer.append("&uArr;"); break;
				case 8658: escapedStringBuffer.append("&rArr;"); break;
				case 8659: escapedStringBuffer.append("&dArr;"); break;
				case 8660: escapedStringBuffer.append("&hArr;"); break;
				case 8704: escapedStringBuffer.append("&forall;"); break;
				case 8706: escapedStringBuffer.append("&part;"); break;
				case 8707: escapedStringBuffer.append("&exist;"); break;
				case 8709: escapedStringBuffer.append("&empty;"); break;
				case 8711: escapedStringBuffer.append("&nabla;"); break;
				case 8712: escapedStringBuffer.append("&isin;"); break;
				case 8713: escapedStringBuffer.append("&notin;"); break;
				case 8715: escapedStringBuffer.append("&ni;"); break;
				case 8719: escapedStringBuffer.append("&prod;"); break;
				case 8721: escapedStringBuffer.append("&sum;"); break;
				case 8722: escapedStringBuffer.append("&minus;"); break;
				case 8727: escapedStringBuffer.append("&lowast;"); break;
				case 8730: escapedStringBuffer.append("&radic;"); break;
				case 8733: escapedStringBuffer.append("&prop;"); break;
				case 8734: escapedStringBuffer.append("&infin;"); break;
				case 8736: escapedStringBuffer.append("&ang;"); break;
				case 8743: escapedStringBuffer.append("&and;"); break;
				case 8744: escapedStringBuffer.append("&or;"); break;
				case 8745: escapedStringBuffer.append("&cap;"); break;
				case 8746: escapedStringBuffer.append("&cup;"); break;
				case 8747: escapedStringBuffer.append("&int;"); break;
				case 8756: escapedStringBuffer.append("&there4;"); break;
				case 8764: escapedStringBuffer.append("&sim;"); break;
				case 8773: escapedStringBuffer.append("&cong;"); break;
				case 8776: escapedStringBuffer.append("&asymp;"); break;
				case 8800: escapedStringBuffer.append("&ne;"); break;
				case 8801: escapedStringBuffer.append("&equiv;"); break;
				case 8804: escapedStringBuffer.append("&le;"); break;
				case 8805: escapedStringBuffer.append("&ge;"); break;
				case 8834: escapedStringBuffer.append("&sub;"); break;
				case 8835: escapedStringBuffer.append("&sup;"); break;
				case 8836: escapedStringBuffer.append("&nsub;"); break;
				case 8838: escapedStringBuffer.append("&sube;"); break;
				case 8839: escapedStringBuffer.append("&supe;"); break;
				case 8853: escapedStringBuffer.append("&oplus;"); break;
				case 8855: escapedStringBuffer.append("&otimes;"); break;
				case 8869: escapedStringBuffer.append("&perp;"); break;
				case 8901: escapedStringBuffer.append("&sdot;"); break;
				case 8968: escapedStringBuffer.append("&lceil;"); break;
				case 8969: escapedStringBuffer.append("&rceil;"); break;
				case 8970: escapedStringBuffer.append("&lfloor;"); break;
				case 8971: escapedStringBuffer.append("&rfloor;"); break;
				case 9001: escapedStringBuffer.append("&lang;"); break;
				case 9002: escapedStringBuffer.append("&rang;"); break;
				case 9674: escapedStringBuffer.append("&loz;"); break;
				case 9824: escapedStringBuffer.append("&spades;"); break;
				case 9827: escapedStringBuffer.append("&clubs;"); break;
				case 9829: escapedStringBuffer.append("&hearts;"); break;
				case 9830: escapedStringBuffer.append("&diams;"); break;
			} // end switch
			// can't fall out bottom
		}
		return escapedStringBuffer.toString();
	}

	/**
	 * Converts HTML to text converting entities
	 * such as &quot; back to "
	 * and &lt; back to &lt;
	 * Ordinary text passes unchanged.
	 * @param text raw text to be processed.  Must not be null.
	 * @return translated text. It also handles HTML 4.0 entities such as
	 * &hearts;  &#123; and &x#123;
	 */
	public static String unescapeString(String text) {
		if (text.indexOf('&') < 0) {
			// are no entities, nothing to do
			return text;
		}
		int originalTextLength = text.length();
		StringBuffer sb = new StringBuffer(originalTextLength);
		for (int i = 0; i < originalTextLength; i++) {
			int whereAmp = text.indexOf('&', i);
			if (whereAmp < 0) {
				// no more &s, we are done
				// append all remaining text
				sb.append(text.substring(i));
				break;
			} else {
				// append all text to left of next &
				sb.append(text.substring(i, whereAmp));
				// avoid reprocessing those chars
				i = whereAmp;
				// text.charAt(i) is an &
				// possEntity has lead & stripped.  
				String possEntity =
					text.substring(
						i + 1,
						Math.min(i + LONGEST_ENTITY, text.length()));
				char t = possEntityToChar(possEntity);
				if (t != 0) {
					// was a good entity, keep its equivalent char.
					sb.append(t);
					// avoid reprocessing chars forming the entity
					int whereSemi =
						possEntity.indexOf(";", SHORTEST_ENTITY - 2);
					i += whereSemi + 1;
				} else {
					// treat & just as ordinary character
					sb.append('&');
				}
			} // end else
		} // end for
		// if result is not shorter, we did not do anything. Saves RAM.
		return (sb.length() == originalTextLength) ? text : sb.toString();
	} // end stripEntities

	/**
	 * Checks a number of gauntlet conditions to ensure this is a valid entity.
	 * Converts Entity to corresponding char.
	 * 
	 * @param possEntity string that may hold an entity.
	 * Lead & must be stripped, but may contain text past the ;
	 * @return corresponding unicode character, 
	 * or 0 if the entity is invalid.
	 */
	private static char possEntityToChar(String possEntity) {
		if (possEntity.length() < SHORTEST_ENTITY - 1)
			return 0;

		// find the trailing ;
		int whereSemi = possEntity.indexOf(";", SHORTEST_ENTITY - 2);
		if (whereSemi < 0)
			return 0;

		// we found a potential entity, at least it has &xxxx;
		// lead & already stripped, now strip trailing ;  
		// and look it up in a table.
		// Will return 0 for an invalid entity.
		return entityToChar(possEntity.substring(0, whereSemi));
	} // end possEntityToChar

	/**
	 * Longest an entity can be, at least in our tables, including the lead & and trail ;
	 */
	private static final int LONGEST_ENTITY = 10; /* &thetasym; */

	/**
	 * The shortest an entity can be, at least in our tables, including the lead & and trailing ;
	 */
	private static final int SHORTEST_ENTITY = 4; /* &#1; &lt; */

	/**
	 * convert a single char to its equivalent 
	 * HTML entity.  Ordinary chars are not changed.
	 * 
	 * @param entity String entity to convert convert. must have lead & and trail ; stripped;
	 * may be a x#123 or #123 style entity. Works faster if entity in lower case.
	 * @return equivalent character. 0 if not recognised.
	 */
	protected static char entityToChar(String entity) {
		Character code = (Character) entityToChar.get(entity);
		if (code != null) {
			return code.charValue();
		}
		code = (Character) entityToChar.get(entity.toLowerCase());
		if (code != null) {
			return code.charValue();
		}

		if (entity.length() < 2)
			return 0;
		try {

			switch (entity.charAt(0)) {
				case 'x' :
				case 'X' :
					// handle hex entities
					if (entity.charAt(1) != '#')
						return 0;
					if (entity.length() < 3)
						return 0;
					// had &x#123; 
					return (
						char) Integer.parseInt(entity.substring(2), /* hex */
					16);

				case '#' :
					// handle decimal entities 
					// had &#123; 
					return (char) Integer.parseInt(entity.substring(1));

				default :
					return 0;
			}
		} catch (NumberFormatException e) {
			return 0;
		}
	} // end entityToChar

	/**
	 * allows lookup by entity name, to get 
	 * the corresponding char.
	 */
	private static HashMap entityToChar;
	static {
		// ready in prebuilt table of associations,
		// Note that lead & and trailing ; has been stripped.
		// O P E N
		try {
			// O P E N
			InputStream is = EntityConverter.class.getResourceAsStream("/entitytochar.ser");
			// look in jar, on classpath, locally or server as appropriate, will have package name prepended
			ObjectInputStream ois = new ObjectInputStream(is);

			// R E A D
			entityToChar = (HashMap) ois.readObject();
			// C L O S E
			ois.close();

		} catch (Exception e) {
			System.err.println(
				"Missing entitytochar.ser " + e.getMessage() + "\n");
		}

	}
}
