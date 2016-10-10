/*
 * $Id: PKCS12Import.java,v 1.5 2008-09-23 10:40:47 ashutoshshahi Exp $
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PKCS12Import {

    private static File pkcs12File;
    private static char[] pkcs12Password;
    private static String keyStoreAlias;
    private static File keyStoreFile;
    private static char[] keyStorePassword;
    private static char[] keyPassword;
    private static String keyStoreType;


    // --------------------- OPTIONS ----------------------------
    private static final String PKCS12_FILE_OPTION = "-file";
    private static final String PKCS12_PASSWORD_OPTION = "-pass";
    private static final String KEYSTORE_FILE_OPTION = "-keystore";
    private static final String KEYSTORE_PASSWORD_OPTION = "-storepass";
    private static final String KEY_PASSWORD_OPTION = "-keypass";
    private static final String KEYSTORE_TYPE_OPTION = "-storetype";
    private static final String ALIAS_OPTION = "-alias";


    private static void printUsage() {
        System.out.println(
            "Options:\n" +
            "        -file <pkcs12-file>           # pkcs12 file to be imported\n" +

            "      [ -alias <alias> ]              # alias to use\n" +
            "                                      # defaults to pkcs12 alias"+
            "      [ -help]\n" +  
            "      [ -keypass <key-password> ]     # user is prompted if omitted and may\n" +
            "                                      # hit return to use store-password\n" +
            "      [ -keystore <keystore-file> ]   # defaults to {user-home}/.keystore\n" +
            "      [ -pass <pkcs12-password> ]     # user is prompted if omitted\n" +
            "      [ -storepass <store-password> ] # user is prompted if omitted\n" +
            "      [ -storetype <store-type> ]     # defaults to default keystore type\n");
    }

    private static void adoptOptionInfo(String option, String value) {
        
        value = value.replaceAll("\\r", "");

        if (PKCS12_FILE_OPTION.equals(option)) {
            pkcs12File = new File(value);
            if (!pkcs12File.exists()) {
                System.out.println(
                    "PKCS12 file at location " + value + " does not exist");
                System.exit(0);
            }
        } else if (PKCS12_PASSWORD_OPTION.equals(option)) {
            pkcs12Password = value.toCharArray();
        } else if (KEYSTORE_FILE_OPTION.equals(option)) {
            keyStoreFile = new File(value);
            /*if (!keyStoreFile.exists()) {
                System.out.println(
                    "Keystore file at location " + value + " does not exist");
                System.exit(0);
            }*/
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
        // If keystore does not exist, create one
        if (keyStoreFile != null && !keyStoreFile.exists()) {
            try {
                
                if (keyStorePassword == null) {
                    BufferedReader reader =
                        new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("keystore-password: ");
                    keyStorePassword = reader.readLine().toCharArray();
                }
                
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                ks.load(null, keyStorePassword);
                
                java.io.FileOutputStream fos = new java.io.FileOutputStream(keyStoreFile);
                ks.store(fos, keyStorePassword);
                fos.close();
            } catch (IOException ex) {
                 System.out.println(
                    "Could not create Keystore file at specified location");
                System.exit(0);
            } catch (NoSuchAlgorithmException ex) {
                 System.out.println(
                    "Could not create Keystore file at specified location");
                System.exit(0);
            } catch (CertificateException ex) {
                 System.out.println(
                    "Could not create Keystore file at specified location");
                System.exit(0);
            } catch (KeyStoreException ex) {
                System.out.println(
                    "Could not create Keystore file at specified location");
                System.exit(0);
            }
        }
    }

    private static void pkcs12import() {

        try {

            if (pkcs12File == null) {
                System.out.println(
                    "Location of pkcs12-file to import is mandatory");
                printUsage();
                System.exit(0);
            }

            BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

            if (pkcs12Password == null) {
                System.out.print("pkcs12-password: ");
                pkcs12Password = reader.readLine().toCharArray();
            }

            KeyStore source = KeyStore.getInstance("PKCS12");
            source.load(new FileInputStream(pkcs12File), pkcs12Password);

            // Assert that pkcs12 file has a single entry which is the keyentry
            int numAliases = 0;
            String pkcs12Alias = null;
            Enumeration enum1 = source.aliases();
            while (enum1.hasMoreElements()) {
                pkcs12Alias = (String) enum1.nextElement();
                numAliases ++;
            }
            if (numAliases == 0) {
                System.out.println("PKCS12 file is empty!");
                System.exit(0);
            }
            if (numAliases > 1) {
                System.out.println("PKCS12 file has more then one entries!");
                System.exit(0);
            }
            if (!source.isKeyEntry(pkcs12Alias)) {
                System.out.println("PKCS12 file doesn't have a key!");
                System.exit(0);
            }

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

            if (keyStorePassword == null) {
                System.out.print("keystore-password: ");
                keyStorePassword = reader.readLine().toCharArray();
            }

            if (keyStoreType == null)
                keyStoreType = KeyStore.getDefaultType();

            KeyStore dest = KeyStore.getInstance(keyStoreType);
            dest.load(new FileInputStream(keyStoreFile), keyStorePassword);

            if (keyPassword == null) {
                System.out.print("key-password: ");
                keyPassword = reader.readLine().toCharArray();
                if (keyPassword.length == 0)
                    keyPassword = keyStorePassword;
            }

            if (keyStoreAlias == null)
                keyStoreAlias = pkcs12Alias;

            if (dest.containsAlias(keyStoreAlias)) {
                System.out.println(
                    "The alias " + keyStoreAlias + " is already present in keystore!");
                System.exit(0);
            }

            dest.setKeyEntry(
                keyStoreAlias,
                source.getKey(pkcs12Alias, pkcs12Password),
                keyPassword,
                source.getCertificateChain(pkcs12Alias));

            dest.store(new FileOutputStream(keyStoreFile), keyStorePassword);

            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        understand(args);

        pkcs12import();
    }
}
