/*
 * $Id: KeyExport.java,v 1.2 2008-07-03 05:29:29 ofung Exp $
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

package soap.security.keystore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Enumeration;

public class KeyExport {

    private static String keyStoreAlias;
    private static File keyStoreFile;
    private static char[] keyStorePassword;
    private static char[] keyPassword;
    private static String keyStoreType;
    private static String outForm;
    private static File keyFile;


    // --------------------- OPTIONS ----------------------------
    private static final String KEY_FILE_OPTION = "-keyfile";
    private static final String OUTPUT_FORMAT_OPTION = "-outform";
    private static final String KEYSTORE_FILE_OPTION = "-keystore";
    private static final String KEYSTORE_PASSWORD_OPTION = "-storepass";
    private static final String KEY_PASSWORD_OPTION = "-keypass";
    private static final String KEYSTORE_TYPE_OPTION = "-storetype";
    private static final String ALIAS_OPTION = "-alias";


    private static void printUsage() {
        System.out.println(
            "Options:\n" +
            "        -keyfile <key-file>           # location of key file\n" +

            "      [ -alias <alias> ]              # not required if there's a single\n" +
            "                                      # key in the keystore" +
            "      [ -help ]\n" +
            "      [ -keypass <key-password> ]     # user is prompted if omitted and may\n" +
            "                                      # hit return to use store-password\n" +
            "      [ -keystore <keystore-file> ]   # defaults to {user-home}/.keystore\n" +
            "      [ -outform <output-format> ]    # PEM|DER - defaults to PEM\n" +
            "      [ -storepass <store-password> ] # user is prompted if omitted\n" +
            "      [ -storetype <store-type> ]     # defaults to default keystore type\n" );
    }

    private static void adoptOptionInfo(String option, String value) {

        if (KEY_FILE_OPTION.equals(option)) {
            keyFile = new File(value);
        } else if (OUTPUT_FORMAT_OPTION.equals(option)) {
            if (!value.equalsIgnoreCase("PEM") &&
                !value.equalsIgnoreCase("DER")) {
                System.out.println(
                    "Valid values for '-outform' option are PEM | DER");
                System.exit(0);
            }
            outForm = value;
        } else if (KEYSTORE_FILE_OPTION.equals(option)) {
            keyStoreFile = new File(value);
            if (!keyStoreFile.exists()) {
                System.out.println(
                    "Keystore file at location " + value + " does not exist");
                System.exit(0);
            }
        } else if (KEYSTORE_PASSWORD_OPTION.equals(option)) {
            keyStorePassword = value.toCharArray();
        } else if (KEYSTORE_TYPE_OPTION.equals(option)) {
            keyStoreType = value;
        } else if (KEY_PASSWORD_OPTION.equals(option)) {
            keyPassword = value.toCharArray();
        } else if (ALIAS_OPTION.equals(option)) {
            keyStoreAlias = value;
        } else {
            System.out.println("Invalid option: " + option);
            printUsage();
            System.exit(0);
        }
    }

    private static void understand(String[] args) {

        if (args.length % 2 != 0) {
            printUsage();
            System.exit(0);
        }

        for (int i = 0; i < args.length; i = i + 2) {
            adoptOptionInfo(args[i], args[i + 1]);
        }
    }

    private static final char[] BASE64_CHARS = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        'a','b','c','d','e','f','g','h','i','j','k','l','m',
        'n','o','p','q','r','s','t','u','v','w','x','y','z',
        '0','1','2','3','4','5','6','7','8','9','+','/'
    };

    private static char[] base64Encode(byte[] in) {

        char[] out = new char[(in.length + 2) / 3 * 4]; // base64 is 4:3
        int accum = -1; // the accumulator
        int outPtr = 0; // pointer into the out[] array

        for (int i=0; i < in.length; i++) {
            int b = in[i] < 0 ? 256 + in[i] : in[i];
            switch (i % 3) {
                case 0:
                    out[outPtr++] = BASE64_CHARS[b / 4];
                    accum = (b % 4) * 16;
                    break;
                case 1:
                    out[outPtr++] = BASE64_CHARS[accum + (b / 16)];
                    accum = (b % 16) * 4;
                    break;
                case 2:
                    out[outPtr++] = BASE64_CHARS[accum + (b / 64)];
                    out[outPtr++] = BASE64_CHARS[b % 64];
                    accum = -1; // done with this iteration
            }
        }
        if (accum >= 0) { // there's still a little data left over...
            out[outPtr++] = BASE64_CHARS[accum];
        }
        while (outPtr % 4 != 0) { // add padding
            out[outPtr++] = '=';
        }
        return out;

    }

    private static void keyExport() {

        try {

            if (keyFile == null) {
                System.out.println(
                    "Location of key-file is mandatory");
                printUsage();
                System.exit(0);
            }

            if (outForm == null)
                outForm = "PEM";

            if (keyStoreFile == null) {
                String defaultLocation =
                    System.getProperty("user.home") +
                    File.separator +
                    ".keystore";
                keyStoreFile = new File(defaultLocation);
                if (!keyStoreFile.exists()) {
                    System.out.println(
                        "Default keystore ({user-home}/.keystore) does not exist.\n"+
                        "Please specify the keystore location using '-keystore' option");
                    System.exit(0);
                }
            }

            BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

            if (keyStorePassword == null) {
                System.out.print("keystore-password: ");
                keyStorePassword = reader.readLine().toCharArray();
            }

            if (keyStoreType == null)
                keyStoreType = KeyStore.getDefaultType();

            KeyStore store = KeyStore.getInstance(keyStoreType);
            store.load(new FileInputStream(keyStoreFile), keyStorePassword);

            if (keyStoreAlias == null) {
                int numAliases = 0;
                Enumeration enum1 = store.aliases();
                while (enum1.hasMoreElements()) {
                    keyStoreAlias = (String) enum1.nextElement();
                    if (store.isKeyEntry(keyStoreAlias)) {
                        if (store.getCertificate(keyStoreAlias) != null)
                            numAliases ++;
                    }
                }
                if (numAliases == 0) {
                    System.out.println("There are no private keys in the keystore");
                    System.exit(0);
                }
                if (numAliases > 1) {
                    System.out.println(
                        "There are more than one private keys in the keystore!");
                    System.out.println(
                        "Please use the -alias option to specify the key alias");
                    System.exit(0);
                }
            }

            if (keyPassword == null) {
                System.out.print("key-password: ");
                keyPassword = reader.readLine().toCharArray();
                if (keyPassword.length == 0)
                    keyPassword = keyStorePassword;
            }

            Key key = store.getKey(keyStoreAlias, keyPassword);

            FileOutputStream fos = new FileOutputStream(keyFile);
            PrintStream ps = new PrintStream(fos, true);
            if ("DER".equalsIgnoreCase(outForm)) {
                ps.write(key.getEncoded());
            } else {
                ps.println("-----BEGIN PRIVATE KEY-----");
                String s = new String(base64Encode(key.getEncoded()));
                for (int j = 0; j < s.length(); j += 64) {
                    ps.println(
                        s.substring(j, Math.min(j + 64, s.length())));
                }
                ps.println("-----END PRIVATE KEY-----");
            }
            ps.close();

            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        understand(args);

        keyExport();
    }
}
