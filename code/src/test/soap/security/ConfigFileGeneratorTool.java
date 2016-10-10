package soap.security;

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


import java.security.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.Position.*;
import java.util.*;
import java.io.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;


// For Exception
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/*
 * Config file generator tool
 *
 * @author Abhijit Das (Abhijit.Das@Sun.COM)
 *
*/



/* This will store already added targets informations
 * targetName = {namespace uri}local name
 * targetType = {qname, xpath}
 * name = local target name
*/

class Targets {
    String targetName = null;
    String targetType = null;
    String name = null;

    Targets (String targetName, String targetNameSpace, String targetType) {
        this.name = targetName;
        if (targetType.equals("qname")) {
            this.targetName = "{" + targetNameSpace + "}" + targetName;
        } else if (targetType.equals("xpath")) {
            this.targetName = targetNameSpace + ":" + targetName;
        } else if (targetType.equals("id")) {
            this.targetName = targetName;
        }
        this.targetType = targetType;
    }

    public String getTargetName () {
        return this.targetName;
    }
};

class MyFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".xml");
    }
    public String getDescription() {
        return "*.xml";
    }
};

class MyFilter1 extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".jks");
    }
    public String getDescription() {
        return "*.jks";
    }
};


// Disable Collaps event on xwss:SecurityConfiguration
class MyTreeExpansionListener implements TreeExpansionListener {
    public void treeExpanded(TreeExpansionEvent evt) {
    }

    public void treeCollapsed(TreeExpansionEvent evt) {
        JTree tree = (JTree)evt.getSource();
        TreePath path = evt.getPath();
        if (path.toString().equals("[xwss:SecurityConfiguration]")) {
            tree.expandRow(0);
            return;
        }
    }
}



public class ConfigFileGeneratorTool extends JFrame implements ActionListener, PopupMenuListener {
    static int automaticID = 0;
    final static int START_INDEX = 0;

    // Stores the extraced target details :
    public String extractedTargetType = "qname";
    public String extractedTargetName = "";
    public String extractedTargetNamespace = "";

    // For DEBUG messages : exceptionDebug = true -> show all exceptions
    // elementDebug -> show element details
    public boolean debug = false;
    public boolean elementDebug = false;
    public boolean exceptionDebug = false;

    public boolean already_saved = true;
    // Allow one opearion when there is no current operation.
    // Means when encrypt op form is active -> sign form can not be opened
    public boolean operation_active = false;

    // This flag is for the secnarion when : Add new Target is clicked multiple times
    public boolean first_click = false;
    public boolean first_click_on_add_target = false;

    // Required for updating the treeView with config file
    public DefaultMutableTreeNode currentPlaceInTreeView = null;
    int flag = 0;

    // For addition of Text Nodes
    Text text = null;

    // Tree
    JTree tree = null;
    DefaultTreeModel treeModel = null;

    LinkedList targetList = new LinkedList();
    JButton addNewTargetButtonForSign = null;
    JButton showAvailableTargetButtonForSign = null;
    JButton addNewTargetButtonForEncrypt = null;
    JButton showAvailableTargetButtonForEncrypt = null;
    JButton addNewTargetButtonForRequireSign = null;
    JButton showAvailableTargetButtonForRequireSign = null;
    JButton addNewTargetButtonForRequireEncryption = null;
    JButton showAvailableTargetButtonForRequireEncryption = null;

    JScrollPane scrollableSoapTargetList = null;

    JPanel mainPanel, selectPanel, opPanel, genPanel;
    JPanel childOpPanel1, childOpPanel2, childOpPanel3;
    JPanel childSelectPanel1, childSelectPanel2;
    JPanel keyReferencePanel;
    JPanel keyReferencePanel1;
    JPanel choicePanel;
    JPanel cchildChoicePanel1;

    JPanel requireSignPanel;
    JPanel childRequireSignPanel1, childRequireSignPanel2;
    JPanel childRequireSignPanel11, childRequireSignPanel12;

    JPanel requireEncryptionPanel;
    JPanel childRequireEncryptionPanel1, childrequireEncryptionPanel2;
    JPanel childRequireEncryptionPanel11, childRequireEncryptionPanel12;

    JComboBox keyReferenceForRequireSign = null;
    JComboBox keyReferenceForRequireEncryption = null;
    JCheckBox signContentOption = null;
    JCheckBox encryptContentOption = null;

    JPanel requireAuthenticationPanel;
    JCheckBox encryptedTokenRequired = null;
    JCheckBox nonceRequired = null;
    JCheckBox passwordDigestRequired = null;
    JComboBox keyReferenceTypeForRequireAuthentication = null;

    // Certificate combobox
    JComboBox certificateType = null;
    JComboBox encryptCertificateType = null;

    // ID attribute
    JTextField signOpID = null;
    JTextField signCertID = null;
    JTextField encryptOpID = null;
    JTextField encryptCertID = null;


    JCheckBox togTimestampButton = null;
    JTextField timeoutInterval = null;
    JLabel timeoutIntervalLabel = null;
    JCheckBox togDumpMessabeButton = null;
    JCheckBox togAuthorizeLocallyButton = null; 
    JPanel timestampPanel = null;
    JPanel dumpChoicePanel = null;

    // Panel for username Token encryption
    JPanel usernameOptionPanel;
    JPanel usernameEncryptedOptionPanel;
    JCheckBox usernameTokenEncrypt = null;
    JTextField usernameTokenEncryptCertificate = null;
    JTextField usernameIDText = null;

    // ChildPanel of childOpPanel1
    JPanel childChildOpPanel11, childChildOpPanel12;
    JPanel cChildChildOpPanel111, cChildChildOpPanel112,
           cChildChildOpPanel121, cChildChildOpPanel122,
           cChildChildOpPanel221, cChildChildOpPanel222;

    // ChildPanel of childOpPanel2
    JPanel childChildOpPanel21, childChildOpPanel22;
    JPanel cChildChildOpPanel211, cChildChildOpPanel212;

    static JFrame firstWindow = null;
    JFrame myWindow = null;
    JFrame addSoapTarget = null;
    JFrame targetWindow = null;

    JComboBox choices = null;
    JComboBox types = null;
    JTextArea configFile = null;
    public JTextField addSoap, senderCert, receiverCert, nameSpace;
    JTextField name;
    JTextField password;
    //JPasswordField password;

    // Options
    JCheckBox signOption1, signOption2, signOption3;
    JCheckBox encryptOption1, encryptOption2, encryptOption3;
    JCheckBox usernameTokenOption1, usernameTokenOption2, usernameTokenOption3;

    JCheckBox signOpTimestamp, encryptOpTimestamp;

    // ListBox storing keyReferenceType {DEFAULT : Direct}
    JComboBox keyReference = null;
    JComboBox keyReferenceSign = null;

    JList soapTarget = null;
    DefaultListModel model = null;
    DefaultListModel model1 = null;
    JList chosenTarget = null;
    String[] userTarget = { "Body" };
    String[] keyReferenceType = { "Direct", "Identifier", "Alias", "IssuerSerialNumber" };
    String[] certificateTypeArray = { "X509Token", "SymmectricKey" };

    // Popup menu for ChosenTarget
    JPopupMenu popupMenu = null;
    JPopupMenu targetMenu = null;


    DocumentBuilderFactory factory = null;
    DocumentBuilder builder = null;
    Document doc = null;
    Element currentPos = null;
    Element insertPos = null;
    String xpath = null;

    static File resultFile = null;
    static String resultFilePath = "";

    static File resultFileFinal = null;
    static String resultFilePathFinal = "";

    String currentTargetType = "";
    String currentTargetValue = "";

    JScrollPane treeView = null;

    // For Config file tree creation
    DefaultMutableTreeNode SecurityConfiguration = null;

    // When user wants to insert after a selected node
    DefaultMutableTreeNode insertAfterNode = null;
    DefaultMutableTreeNode currentlyDeletedNode = null;
    static boolean insertFlag = false;
    boolean operationNotAllowed = false;
    JMenu insertAfter = null;
    String selectedMenuLabel = "";

    // Commit Button
    JButton commitButton = null;

    static JMenuBar menuBar = null;
    JMenu menu = null;

    File currentFile = null;

    public ConfigFileGeneratorTool() {

        super("ConfigFileGeneratorTool");
        // Enter securityConfigurator element

        SecurityConfiguration = new DefaultMutableTreeNode("xwss:SecurityConfiguration");
        tree = new JTree(SecurityConfiguration) ;

        //tree.addTreeWillExpandListener(new MyTreeWillExpandListener());
        tree.addTreeExpansionListener(new MyTreeExpansionListener());

        tree.setSize(400,400);
        treeModel = (DefaultTreeModel)tree.getModel();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeView = new JScrollPane(tree);

        // Add a popup menu (Add) that allows deletion of the element
        targetMenu = new JPopupMenu();
        insertAfter = new JMenu("Insert Before") ;

        JMenuItem sign                  = new JMenuItem("Sign");
        JMenuItem encrypt               = new JMenuItem("Encrypt");
        JMenuItem authenticate          = new JMenuItem("UsernameAndPassword");
        JMenuItem requireSignature      = new JMenuItem("requireSignature");
        JMenuItem requireEncryption     = new JMenuItem("requireEncryption");
        JMenuItem requireAuthentication = new JMenuItem("requireAuthentication");

        sign.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     operationNotAllowed = true;
                     modifyConfigFileElement();
                     selectedMenuLabel = "Sign";
                     if (operationNotAllowed)
                         showForm();
                 }
            }
        );


        encrypt.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     operationNotAllowed = true;
                     modifyConfigFileElement();
                     selectedMenuLabel = "Encrypt";
                     if (operationNotAllowed)
                         showForm();
                 }
            }
        );

        authenticate.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     operationNotAllowed = true;
                     modifyConfigFileElement();
                     selectedMenuLabel = "UsernameAndPassword";
                     if (operationNotAllowed)
                         showForm();
                 }
            }
        );

        requireSignature.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     operationNotAllowed = true;
                     modifyConfigFileElement();
                     selectedMenuLabel = "requireSignature";
                     if (operationNotAllowed)
                         showForm();
                 }
            }
        );

        requireEncryption.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     operationNotAllowed = true;
                     modifyConfigFileElement();
                     selectedMenuLabel = "requireEncryption";
                     if (operationNotAllowed)
                         showForm();
                 }
            }
        );

        requireAuthentication.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     operationNotAllowed = true;
                     modifyConfigFileElement();
                     selectedMenuLabel = "requireAuthentication";
                     if (operationNotAllowed)
                         showForm();
                 }
            }
        );

        insertAfter.add(sign);
        insertAfter.add(encrypt);
        insertAfter.add(authenticate);
        insertAfter.add(requireSignature);
        insertAfter.add(requireEncryption);
        insertAfter.add(requireAuthentication);


        JMenuItem deleteElement = new JMenuItem("Delete Element") ;
        deleteElement.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     deleteConfigFileElement();
                 }
            }
        );
        targetMenu.add(insertAfter);
        targetMenu.add(deleteElement);

        tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    targetMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    targetMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });


        //Create the phase selection and display panels.
        selectPanel = new JPanel();
        opPanel = new JPanel();
        genPanel = new JPanel();

        //Add various widgets to the sub panels.
        addWidgets();

        //Create the main panel to contain the two sub panels.
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //Add the select and display panels to the main panel.
        mainPanel.add(selectPanel);
        mainPanel.add(opPanel);
        mainPanel.add(genPanel);
        menuBar = new JMenuBar();
        menu = new JMenu("Session");
        menuBar.add(menu);

        JMenuItem item1 = new JMenuItem("Open Existing");
        item1.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     if (!already_saved) {
                         int answer = JOptionPane.showConfirmDialog(myWindow,
                                         "You have not saved the file \n Exit the session without saving");
                         if (answer != JOptionPane.YES_OPTION)
                            return;
                     }

                     already_saved = true;

                     JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
                     fc.addChoosableFileFilter(new MyFilter());
                     fc.showOpenDialog(firstWindow);
                     File selFile = fc.getSelectedFile();
                     if (selFile == null || !selFile.exists()) {
                         JOptionPane.showMessageDialog(myWindow, "Choose a valid file");
                         return;
                     }
                     try {
                         resultFilePathFinal = selFile.getAbsolutePath();
                         resultFileFinal = new File(resultFilePathFinal);

                         FileReader in = new FileReader(selFile.getAbsolutePath());
                         FileWriter out = new FileWriter(selFile.getAbsolutePath() + ".tmp");
                         int c;
                         int startcomment = 0;
                         while ((c = in.read()) != -1) {
                             if ( c == 60 )
                                 startcomment = 1;
                             if ( c == 62 )
                                 startcomment = 0;

                             //System.out.println(c);
                             if ( c != 9 && c != 10 && c != 32 )
                                 out.write(c);
                             if ( c == 32 && startcomment == 1 ) {
                                 out.write(c);
                                 startcomment = startcomment + 1;
                             } else if ( c == 32 && startcomment >= 2 ) {
                                 startcomment = startcomment + 1;
                             } else if ( c != 32 && startcomment >= 2 ) {
                                 startcomment = 1;
                             }

                         }
                         in.close();
                         out.close();

                         in = new FileReader(selFile.getAbsolutePath() + ".tmp");
                         out = new FileWriter(selFile.getAbsolutePath());

                         while ((c = in.read()) != -1)
                             out.write(c);
                         in.close();
                         out.close();

                         resultFilePath = selFile.getAbsolutePath() + ".tmp";
                         resultFile = new File(resultFilePath);
                         resultFile.deleteOnExit();
                     } catch (Exception ex) {
                         if (exceptionDebug) {
                             System.out.println("Error in Open Existing Block");
                         }
                     }

                     currentFile = selFile;

                     try {
                         // Clear the current available Target List / and targetList datastructure
                         model1.clear();
                         targetList.clear();

                         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                         factory.setValidating(false);
                         factory.setIgnoringComments(true);

                         Document updateTreeViewDoc = factory.newDocumentBuilder().parse(selFile);
                         doc = updateTreeViewDoc;
                         NodeList list = doc.getElementsByTagName("*");
                         currentPos = ((Element)list.item(0));
                         // Initialize the Tree
                         int totalChild = treeModel.getChildCount(treeModel.getRoot());

                         for (int j=totalChild-1; j>=0; j--) {
                             //System.out.println((DefaultMutableTreeNode)treeModel.getChild(treeModel.getRoot(), j));
                             treeModel.removeNodeFromParent(
                                    (DefaultMutableTreeNode)treeModel.getChild(treeModel.getRoot(), j));
                         }
                         currentPlaceInTreeView = SecurityConfiguration;
                         togTimestampButton.setEnabled(true);
                         togDumpMessabeButton.setEnabled(true);
                         togAuthorizeLocallyButton.setEnabled(true); 

                         togTimestampButton.setSelected(false);
                         togDumpMessabeButton.setSelected(false);
                         togAuthorizeLocallyButton.setSelected(false);

			 automaticID = 0;
                         updateTreeViewWithCurrentFile(updateTreeViewDoc, SecurityConfiguration);
			 automaticID = automaticID + 1;

                         choices.setEnabled(true);
                         expandAll(tree);

			 // Updating toggle TimeStamp check box
  			 TreePath path = tree.getNextMatch("xwss:Timestamp", 0, Bias.Forward);
			 if ( path != null) {
			      togTimestampButton.setSelected(true);
			 }
			 timeoutInterval.setEnabled(true);
        		 timeoutIntervalLabel.setEnabled(true);

                     } catch (Exception ex) {
                         ex.printStackTrace();
                     }
                 }
            }
        );

        JMenuItem item2 = new JMenuItem("Create New");
        item2.addActionListener (
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     if (!already_saved) {
                         int answer = JOptionPane.showConfirmDialog(myWindow,
                                         "You have not saved the file \n Exit the session without saving");
                        if (answer != JOptionPane.YES_OPTION)
                            return;
                     }

                     JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
                     fc.addChoosableFileFilter(new MyFilter());
                     fc.showOpenDialog(firstWindow);
                     File selFile = fc.getSelectedFile();
                     if (selFile == null) {
                         JOptionPane.showMessageDialog(myWindow, "Enter a file name");
                         return;
                     }

                     if (selFile.exists()) {
                         JOptionPane.showMessageDialog(myWindow, "Such file name exists");
                         return;
                     }

                     //System.out.println("New File : " + selFile.getAbsolutePath());

                     resultFilePath = selFile.getAbsolutePath() + ".tmp";
                     resultFilePathFinal = selFile.getAbsolutePath();
                     resultFile = new File(resultFilePath);
                     resultFile.deleteOnExit();

                     try {
                         factory = DocumentBuilderFactory.newInstance();
                         factory.setIgnoringComments(true);
                         builder = factory.newDocumentBuilder();
                         if ( !resultFile.exists() ) {
							 if (debug) {
								 System.out.println("Creating new File : " + resultFilePathFinal);
							 }
                             doc = createDomDocument();
                             currentPos = doc.createElement("xwss:SecurityConfiguration");
                             currentPos.setAttribute("xmlns:xwss", "http://com.sun.xml.wss.configuration");
                             doc.appendChild(currentPos);

                             DefaultMutableTreeNode category = null;

                             DOMSource source1 = new DOMSource(doc);
                             StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

                             TransformerFactory transFactory = TransformerFactory.newInstance();
                             Transformer transformer = transFactory.newTransformer();

                             transformer.transform(source1, result);

                             togTimestampButton.setEnabled(true);
                             togDumpMessabeButton.setEnabled(true);
                             togAuthorizeLocallyButton.setEnabled(true);

                             togTimestampButton.setSelected(false);
                             togDumpMessabeButton.setSelected(false);
			     togAuthorizeLocallyButton.setSelected(false);

                             choices.setEnabled(true);

                             already_saved = false;
                             // Clear the available Target List / and targetList Datastructure
                             model1.clear();
                             targetList.clear();
                             automaticID = 0;
                         }
                     } catch (Exception ex) {
                         //ex.printStackTrace();
                         JOptionPane.showMessageDialog(myWindow, "You have not selected any file");
                     }


                     try {
                         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                         factory.setIgnoringComments(true);
                         factory.setValidating(false);

                         // Initialize the Tree
                         int totalChild = treeModel.getChildCount(treeModel.getRoot());

                         for (int j=totalChild-1; j>=0; j--) {
                             treeModel.removeNodeFromParent(
                                    (DefaultMutableTreeNode)treeModel.getChild(treeModel.getRoot(), j));
                         }

                         // Create initial attribute nodes

                         DefaultMutableTreeNode category = null;

                         category = new DefaultMutableTreeNode("xmlns:xwss : http://com.sun.xml.wss.configuration");
                         treeModel.insertNodeInto(category, SecurityConfiguration, SecurityConfiguration.getChildCount());

                         category = new DefaultMutableTreeNode("dumpMessages : false");
                         treeModel.insertNodeInto(category, SecurityConfiguration, SecurityConfiguration.getChildCount());

                         category = new DefaultMutableTreeNode("authorizeLocally : false");
                         treeModel.insertNodeInto(category, SecurityConfiguration, SecurityConfiguration.getChildCount());

                         //category = new DefaultMutableTreeNode("useTimestamps : true");
                         //treeModel.insertNodeInto(category, SecurityConfiguration, SecurityConfiguration.getChildCount());

                         initailizeConfigFile();
                         expandAll(tree);
                         timeoutInterval.setEnabled(true);
        			     timeoutIntervalLabel.setEnabled(true);

                     } catch (Exception ex) {
                         ex.printStackTrace();
                     }
                 }
            }
        );

        JMenuItem item3 = new JMenuItem("Save");
        item3.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                     try {
                         FileReader in = new FileReader(resultFilePath);
                         FileWriter out = new FileWriter(resultFilePathFinal);
                         int c;
                         while ((c = in.read()) != -1)
                             out.write(c);
                         in.close();
                         out.close();

                         JOptionPane.showMessageDialog(myWindow, "File SuccessFully saved");
                         already_saved = true;
                     } catch (Exception ex) {
                         if (exceptionDebug) {
                             System.out.println("Error while saving the file");
                         }
                     }
                }
            }
        );


        JMenuItem item4 = new JMenuItem("Save As ...");
        item4.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentFile == null ) {
                        System.out.println("No file is chosen");
                        return;
                    }
                    JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
                    fc.showSaveDialog(firstWindow);
                    File selFile = fc.getSelectedFile();
                    if (selFile == null ) {
                         JOptionPane.showMessageDialog(myWindow, "Enter a File name");
                         return;
                    }
                    if (selFile.exists()) {
                        int answer = JOptionPane.showConfirmDialog(myWindow,
                                         "File exists : Overwrite it ??");
                        if (answer != JOptionPane.YES_OPTION)
                            return;
                    }
                    String storeFile = selFile.getAbsolutePath();
                    if (selFile.exists())
                         selFile.delete();


                    if (selFile == null) {
                        return;
                    }



                    try {
                         FileReader in = new FileReader(resultFilePath);
                         FileWriter out = new FileWriter(storeFile);

                         int c;
                         while ((c = in.read()) != -1)
                             out.write(c);
                         in.close();
                         out.close();

                         JOptionPane.showMessageDialog(myWindow, "File SuccessFully saved");
                     } catch (Exception ex) {
                         if (exceptionDebug) {
                             System.out.println("Error while saving the file");
                         }
                     }
                }
            }
        );

        JMenuItem item5 = new JMenuItem("Exit");
        item5.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (debug) {
                        System.out.println("Already Saved Flag : " + already_saved);
                    }
                    if (already_saved) {
                        System.exit(0);
                    } else {
                        int answer = JOptionPane.showConfirmDialog(myWindow,
                                         "Quit without saving the file");
                        if (answer == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                    }
                }
            }
        );

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menu.add(item5);

    }

    private void addWidgets() {

        //Create a combo box with all possible options.
        String[] phases = { "Choose Operation", "Sign",
                            "Encrypt", "UsernameToken",
                            "Require Signature",
                            "Require Encryption",
                            "Require Authentication"
                          };
        choices = new JComboBox(phases);
        choices.setSelectedIndex(START_INDEX);
        choices.addActionListener(
            new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     showForm();
                 }
            }
        );



        Action addTargetForRequireEncryption = new AbstractAction("Add new Targets") {
            public void actionPerformed(ActionEvent evt) {
                if (first_click_on_add_target)
                    return;
                first_click_on_add_target = true;
                addSoapTarget = new JFrame("New Target Addition");
                addSoapTarget.setLocation((int)targetWindow.getLocation().getX()-200, (int)targetWindow.getLocation().getY() + 50);
                addSoapTarget.getContentPane().add(childSelectPanel2);
                addSoapTarget.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                addSoapTarget.pack();
                addSoapTarget.show();
            }
        };
        addNewTargetButtonForRequireEncryption = new JButton(addTargetForRequireEncryption);


        Action showTargetForRequireEncryption = new AbstractAction("Required Encrypted Targets") {
            public void actionPerformed(ActionEvent evt) {
                if (first_click)
                    return;
                first_click = true;
                targetWindow = new JFrame("Available Targets informations");
                targetWindow.setLocation((int)myWindow.getLocation().getX()-200, (int)myWindow.getLocation().getY());
                targetWindow.getContentPane().add(childChildOpPanel12);
                targetWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                targetWindow.pack();
                targetWindow.show();
            }
        };
        showAvailableTargetButtonForRequireEncryption = new JButton(showTargetForRequireEncryption);



        Action addTargetForSign = new AbstractAction("Add new Targets") {
            public void actionPerformed(ActionEvent evt) {
                if (first_click_on_add_target)
                    return;
                first_click_on_add_target = true;
                addSoapTarget = new JFrame("New Target Addition");
                addSoapTarget.setLocation((int)targetWindow.getLocation().getX()-200, (int)targetWindow.getLocation().getY() + 50);
                addSoapTarget.getContentPane().add(childSelectPanel2);
                addSoapTarget.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                addSoapTarget.pack();
                addSoapTarget.show();
            }
        };
        addNewTargetButtonForSign = new JButton(addTargetForSign);


        Action showTargetForSign = new AbstractAction("Targets for Signing") {
            public void actionPerformed(ActionEvent evt) {
                if (first_click)
                    return;
                first_click = true;
                targetWindow = new JFrame("Available Targets informations");
                targetWindow.setLocation((int)myWindow.getLocation().getX()-200, (int)myWindow.getLocation().getY());
                targetWindow.getContentPane().add(childChildOpPanel12);
                targetWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                targetWindow.pack();
                targetWindow.show();
            }
        };
        showAvailableTargetButtonForSign = new JButton(showTargetForSign);

        Action addTargetForEncrypt = new AbstractAction("Add new Targets") {
            public void actionPerformed(ActionEvent evt) {
                if (first_click_on_add_target)
                    return;
                first_click_on_add_target = true;
                addSoapTarget = new JFrame("New Target Addition");
                addSoapTarget.setLocation((int)targetWindow.getLocation().getX()-200, (int)targetWindow.getLocation().getY() + 50);
                addSoapTarget.getContentPane().add(childSelectPanel2);
                addSoapTarget.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                addSoapTarget.pack();
                addSoapTarget.show();
            }
        };
        addNewTargetButtonForEncrypt = new JButton(addTargetForEncrypt);


        Action showTargetForEncrypt = new AbstractAction("Targets for Encryption") {
            public void actionPerformed(ActionEvent evt) {
                if (first_click)
                    return;
                first_click = true;
                targetWindow = new JFrame("Available Targets informations");
                targetWindow.setLocation((int)myWindow.getLocation().getX()-200, (int)myWindow.getLocation().getY());
                targetWindow.getContentPane().add(childChildOpPanel12);
                targetWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                targetWindow.pack();
                targetWindow.show();
            }
        };
        showAvailableTargetButtonForEncrypt = new JButton(showTargetForEncrypt);

        //Add a border around the select panel.
        selectPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        //Add a border around the display panel.
        opPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Choose appropriate attributes"),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        // Generated Config File
        genPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Generated Config File"),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        //Add Option Attributes.

        // childOpPanel1 -----------START------------------------------
        childChildOpPanel11 = new JPanel(new GridLayout(0,1,0,1));
        childChildOpPanel11.setSize(400, 400);

        cChildChildOpPanel111 = new JPanel(new GridLayout(0,1));
        cChildChildOpPanel111.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Sign Op. attributes"),
            BorderFactory.createEmptyBorder(0,0,0,0)));
        JPanel signOpTimePanel = new JPanel();
        signOption1 = new JCheckBox("Sign The Token", true);
        signOpTimePanel.add(signOption1);
        signOpTimestamp = new JCheckBox("use Timestamp", true);
	signOpTimePanel.add(signOpTimestamp);
        cChildChildOpPanel111.add(signOpTimePanel);

        JLabel signOpIDLabel = new JLabel("Token ID");
        signOpID = new JTextField(10);

        //signOpTimestamp = new JCheckBox("use Timestamp", true);

	JPanel signOpIDPanel = new JPanel();
	signOpIDPanel.add(signOpIDLabel);
	signOpIDPanel.add(signOpID);
        //signOpIDPanel.add(signOpTimestamp);

        JLabel signCertIDLabel = new JLabel("Certificate ID");
	signCertID = new JTextField(8);

	JPanel signCertIDPanel = new JPanel();
	signCertIDPanel.add(signCertIDLabel);
	signCertIDPanel.add(signCertID);

        cChildChildOpPanel111.add(signOpIDPanel);
        cChildChildOpPanel111.add(signCertIDPanel);


        cChildChildOpPanel112 = new JPanel(new GridLayout(0,1));
        cChildChildOpPanel112.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Sender Certificate Alias"),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        senderCert = new JTextField("DEFAULT");

        //Add button action : choose one alias from keystore or trustore
        Action showAliasActionForSenderCert = new AbstractAction("Get alias from keystore") {
            public void actionPerformed(ActionEvent evt) {
                   showAllAliases("Sign");
            }
        };
        JButton chooseAliasForSenderCert = new JButton(showAliasActionForSenderCert);
        cChildChildOpPanel112.add(senderCert);
        cChildChildOpPanel112.add(chooseAliasForSenderCert);
        //cChildChildOpPanel112.add(showAvailableTargetButtonForSign);


        Action action1 = new AbstractAction("Apply") {
            public void actionPerformed(ActionEvent evt) {
                   updateConfigFileWithSign();
                   toggleInsertFlag();
            }
        };
        JButton updateConfigFile = new JButton(action1);

        Action closeSignOpAction = new AbstractAction("Close") {
            public void actionPerformed(ActionEvent evt) {
                   automaticID = automaticID - 2;
                   toggleInsertFlag();
                   operation_active = false;
                   first_click = false;
                   first_click_on_add_target = false;
                   if (myWindow != null) {
                       myWindow.setVisible(false);
                   }
                   if (addSoapTarget != null) {
                       addSoapTarget.setVisible(false);
                   }
                   if (targetWindow != null) {
                       targetWindow.setVisible(false);
                   }
            }
        };
        JButton closeSignOpActionButton = new JButton(closeSignOpAction);


        childChildOpPanel11.add(cChildChildOpPanel111);
        childChildOpPanel11.add(cChildChildOpPanel112);

        // Add a new Panel to store the keyReferenceType
        keyReferencePanel = new JPanel(new GridLayout(0,1));
        keyReferencePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Key Reference Type for Signing"),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        keyReference = new JComboBox(keyReferenceType);
        keyReferenceSign = new JComboBox(keyReferenceType);
        keyReference.setSelectedIndex(START_INDEX);
        keyReferenceSign.setSelectedIndex(START_INDEX);

        certificateType = new JComboBox(certificateTypeArray);
        certificateType.setSelectedIndex(START_INDEX);

	keyReferencePanel.add(certificateType);
        keyReferencePanel.add(keyReferenceSign);


        childChildOpPanel11.add(keyReferencePanel);

        JPanel signPanelButtonPanel = new JPanel(new GridLayout(0,1,0,10));

        signPanelButtonPanel.add(showAvailableTargetButtonForSign);
        signPanelButtonPanel.add(updateConfigFile);
        signPanelButtonPanel.add(closeSignOpActionButton);

        childChildOpPanel11.add(signPanelButtonPanel);

        childChildOpPanel12 = new JPanel(new GridLayout(0,1));
        childChildOpPanel12.setPreferredSize(new Dimension(200, 350));

        cChildChildOpPanel121 = new JPanel(new GridLayout(0,1));
        cChildChildOpPanel121.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Available Targets"),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        model1 = new DefaultListModel();
        soapTarget = new JList(model1);


        soapTarget.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                AddSelectedToChosenTarget();
            }
        });

        targetMenu.addPopupMenuListener(this);


        scrollableSoapTargetList = new JScrollPane(soapTarget);
        cChildChildOpPanel121.add(scrollableSoapTargetList);

        cChildChildOpPanel122 = new JPanel(new GridLayout(0,1));
        cChildChildOpPanel122.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Targets chosen"),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        // Generate a List that allows addition and removal
        model = new DefaultListModel();
        chosenTarget = new JList(model);
        //model.add(0, "Body");

        // Add a popup menu (Delete) that allows deletion of the element
        popupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Selected Targets Deleted") ;

        popupMenu.add(item);

        chosenTarget.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
               DeleteSelectedFromChosenTarget();
            }
        });

        popupMenu.addPopupMenuListener(this);
        // Add the new configurable list to the panel
        JScrollPane scrollableChosenTargetList = new JScrollPane(chosenTarget);
        cChildChildOpPanel122.add(scrollableChosenTargetList);

        childChildOpPanel12.add(cChildChildOpPanel121);
        childChildOpPanel12.add(cChildChildOpPanel122);
        childChildOpPanel12.add(addNewTargetButtonForSign);

        childOpPanel1 = new JPanel();
        childOpPanel1.add(childChildOpPanel11);



        // childOpPanel1 ------------END-------------------------------


        // childOpPanel2 -----------START------------------------------
        childChildOpPanel21 = new JPanel(new GridLayout(0,1,0,10));
        childChildOpPanel21.setSize(new Dimension(400, 400));

        cChildChildOpPanel211 = new JPanel(new GridLayout(0,1));
        cChildChildOpPanel211.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Encrypt Op. attributes"),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        JPanel encryptIDPanel = new JPanel();
        JLabel encryptCertIDLabel = new JLabel("Certificate ID");
        encryptCertID = new JTextField(8);
        encryptIDPanel.add(encryptCertIDLabel);
        encryptIDPanel.add(encryptCertID);


        JLabel encryptOpIDLabel = new JLabel("Token ID");
        encryptOpID = new JTextField(10);

	JPanel encryptOpIDPanel = new JPanel();
	encryptOpIDPanel.add(encryptOpIDLabel);
	encryptOpIDPanel.add(encryptOpID);

	cChildChildOpPanel211.add(encryptOpIDPanel);
        cChildChildOpPanel211.add(encryptIDPanel);

        cChildChildOpPanel212 = new JPanel(new GridLayout(0,1));
        cChildChildOpPanel212.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Receiver Certificate Alias"),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        receiverCert = new JTextField("DEFAULT");

        //Add button action : choose one alias from keystore or trustore
        Action showAliasActionForReceiverCert = new AbstractAction("Get Alias from Truststore") {
            public void actionPerformed(ActionEvent evt) {
                   showAllAliases("Encrypt");
            }
        };
        JButton chooseAliasForReceiverCert = new JButton(showAliasActionForReceiverCert);


        cChildChildOpPanel212.add(receiverCert);
        cChildChildOpPanel212.add(chooseAliasForReceiverCert);
        childChildOpPanel21.add(cChildChildOpPanel211);
        childChildOpPanel21.add(cChildChildOpPanel212);


        Action action2 = new AbstractAction("Apply") {
            public void actionPerformed(ActionEvent evt) {
                   updateConfigFileWithEncrypt();
                   toggleInsertFlag();
            }
        };
        JButton updateConfigFile1 = new JButton(action2);

        Action closeEncryptOpAction = new AbstractAction("Close") {
            public void actionPerformed(ActionEvent evt) {
                   automaticID = automaticID - 2;
                   operation_active = false;
                   first_click = false;
                   first_click_on_add_target = false;
                   toggleInsertFlag();
                   if (myWindow != null) {
                       myWindow.setVisible(false);
                   }
                   if (addSoapTarget != null) {
                       addSoapTarget.setVisible(false);
                   }
                   if (targetWindow != null) {
                       targetWindow.setVisible(false);
                   }
            }
        };
        JButton closeEncryptOpActionButton = new JButton(closeEncryptOpAction);

        // Add a new Panel to store the keyReferenceType
        keyReferencePanel1 = new JPanel(new GridLayout(0,1));
        keyReferencePanel1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Key Reference Type"),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        keyReference = new JComboBox(keyReferenceType);
        keyReference.setSelectedIndex(START_INDEX);

        encryptCertificateType = new JComboBox(certificateTypeArray);
	encryptCertificateType.setSelectedIndex(START_INDEX);

	keyReferencePanel1.add(encryptCertificateType);
        keyReferencePanel1.add(keyReference);


        JPanel encryptPanelButtonPanel = new JPanel(new GridLayout(0,1,0,10));
        encryptPanelButtonPanel.add(showAvailableTargetButtonForEncrypt);
        encryptPanelButtonPanel.add(updateConfigFile1);
        encryptPanelButtonPanel.add(closeEncryptOpActionButton);


        childOpPanel2 = new JPanel(new GridLayout(0,1,0,10));
        childOpPanel2.add(cChildChildOpPanel211);
        childOpPanel2.add(cChildChildOpPanel212);
        childOpPanel2.add(keyReferencePanel1);
        childOpPanel2.add(encryptPanelButtonPanel);


        // childOpPanel2 -----------END-------------------------------

        // childOpPanel3 -----------START-----------------------------
        childOpPanel3 = new JPanel();
        childOpPanel3.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        Action action3 = new AbstractAction("Import Username Token") {
            public void actionPerformed(ActionEvent evt) {
                   updateConfigFileWithUsernameToken();
                   toggleInsertFlag();
            }
        };
        JButton updateConfigFile2 = new JButton(action3);

        Action closeUsernameTokenOpAction = new AbstractAction("Close") {
            public void actionPerformed(ActionEvent evt) {
                   automaticID = automaticID - 1;
                   operation_active = false;
                   first_click = false;
                   first_click_on_add_target = false;
                   toggleInsertFlag();
                   if (myWindow != null) {
                       myWindow.setVisible(false);
                   }
                   if (addSoapTarget != null) {
                       addSoapTarget.setVisible(false);
                   }
                   if (targetWindow != null) {
                       targetWindow.setVisible(false);
                   }
            }
        };
        JButton closeUsernameTokenOpActionButton = new JButton(closeUsernameTokenOpAction);

        JLabel nameLabel = new JLabel("User Name");
        name = new JTextField(6);

        JLabel passwordLabel = new JLabel("Password");
        //password = new JPasswordField();
        //password.setEchoChar('*');
        password = new JTextField(6);

        JPanel usernameIDPanel = new JPanel();
        JLabel usernameID = new JLabel("Token ID : ");
        usernameIDText = new JTextField(3);
        usernameIDPanel.add(usernameID);
        usernameIDPanel.add(usernameIDText);


        usernameTokenOption1 = new JCheckBox("Use Nonce", true);
        usernameTokenOption2 = new JCheckBox("Digest Password", true);
        usernameOptionPanel = new JPanel(new GridLayout(0,1));
        usernameOptionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        usernameOptionPanel.add(nameLabel);
        usernameOptionPanel.add(name);
        usernameOptionPanel.add(passwordLabel);
        usernameOptionPanel.add(password);
        usernameOptionPanel.add(usernameID);
        usernameOptionPanel.add(usernameIDText);
        usernameOptionPanel.add(usernameTokenOption1);
        usernameOptionPanel.add(usernameTokenOption2);
        usernameOptionPanel.add(updateConfigFile2);
        usernameOptionPanel.add(closeUsernameTokenOpActionButton);

        JPanel usernameEncryptedOptionPanelParent = new JPanel(new GridLayout(0,1));
		usernameEncryptedOptionPanelParent.setBorder(BorderFactory.createCompoundBorder(
		            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        usernameEncryptedOptionPanel = new JPanel(new GridLayout(0,1));
        usernameEncryptedOptionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Encrypt UsernameToken"),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        usernameTokenEncrypt = new JCheckBox("Encrypt Content only", false);
        usernameTokenEncrypt.setEnabled(false);
        final JLabel usernameTokenEncryptCertificateLabel = new JLabel("Receiver Certificate Alias");
        usernameTokenEncryptCertificateLabel.setEnabled(false);
        usernameTokenEncryptCertificate = new JTextField(6);
        usernameTokenEncryptCertificate.setEnabled(false);
        usernameTokenOption3 = new JCheckBox("Encrypted Form", false);

        usernameTokenOption3.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (usernameTokenOption3.isSelected()) {
                        usernameTokenEncrypt.setEnabled(true);
                        usernameTokenEncryptCertificate.setEnabled(true);
                        usernameTokenEncryptCertificateLabel.setEnabled(true);
                    } else {
                        usernameTokenEncrypt.setEnabled(false);
                        usernameTokenEncryptCertificate.setEnabled(false);
                        usernameTokenEncryptCertificateLabel.setEnabled(false);
                    }
                }
            }
        );
        usernameEncryptedOptionPanel.add(usernameTokenOption3);
        usernameEncryptedOptionPanel.add(usernameTokenEncrypt);
        usernameEncryptedOptionPanel.add(usernameTokenEncryptCertificateLabel);
        usernameEncryptedOptionPanel.add(usernameTokenEncryptCertificate);
        childOpPanel3.add(usernameOptionPanel);


	JPanel emptyPanel = new JPanel();
	JLabel IDHelp = new JLabel("The ID should be unique");
	emptyPanel.add(IDHelp);


	JPanel usernameEncryptedOptionPanelChild = new JPanel(new GridLayout(0,1));
	usernameEncryptedOptionPanelChild.add(emptyPanel);
        usernameEncryptedOptionPanelChild.add(usernameIDPanel);
        usernameEncryptedOptionPanelParent.add(usernameEncryptedOptionPanelChild);
        usernameEncryptedOptionPanelParent.add(usernameEncryptedOptionPanel);
        //childOpPanel3.add(usernameEncryptedOptionPanelParent);

        // childOpPanle3 -----------END------------------------------

        opPanel.add(childOpPanel1);
        opPanel.add(childOpPanel2);
        opPanel.add(childOpPanel3);

        // Add TestArea in genPanel
        configFile = new JTextArea("<!-- Generated Config File -->");
        genPanel.add(configFile);

        // Configure Select Panel
        childSelectPanel1 = new JPanel(new GridLayout(0,1));
        childSelectPanel1.setPreferredSize(new Dimension(400, 400));
        childSelectPanel1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(1,1,1,1)));




        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();



        choicePanel = new JPanel(gridbag);

        JPanel childChoicePanel = new JPanel(new GridLayout(0,1,0,150));
        childChoicePanel.setSize(100, 400);
        /*childChoicePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(5,5,5,5)));*/

        JPanel cchildChoicePanel = new JPanel(new GridLayout(0,1,0,10));
        /*cchildChoicePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(0,0,0,0)));*/

        Action togTimestamp = new AbstractAction("useTimeStamp") {
            public void actionPerformed(ActionEvent evt) {
                   if (debug) {
                       System.out.println("Toggle timestamp");
                   }
                   updateConfigFileWithTimeStamp();
            }
        };
        togTimestampButton = new JCheckBox(togTimestamp);
        timeoutInterval = new JTextField("300", 3);
        timeoutIntervalLabel = new JLabel("            TimeOut Interval : ");

        timeoutInterval.setEnabled(false);
        timeoutIntervalLabel.setEnabled(false);

        togTimestampButton.setSelected(false);
        togTimestampButton.setEnabled(false);

        timestampPanel = new JPanel();
        /*timestampPanel.setBorder(BorderFactory.createCompoundBorder(
		            BorderFactory.createTitledBorder(""),
                    BorderFactory.createEmptyBorder(0,0,0,0)));*/
        timestampPanel.add(togTimestampButton);
        timestampPanel.add(timeoutIntervalLabel);
        timestampPanel.add(timeoutInterval);

        Action togDumpMessage = new AbstractAction("dumpMessages                  ") {
            public void actionPerformed(ActionEvent evt) {
                   updateConfigFileWithDumpMessage();
            }
        };
        togDumpMessabeButton = new JCheckBox(togDumpMessage);
        togDumpMessabeButton.setSelected(false);
        togDumpMessabeButton.setEnabled(false);

        Action togAuthorizeLocally = new AbstractAction("authorizeLocally") {
            public void actionPerformed(ActionEvent evt) {
                   updateConfigFileWithAuthorizeLocally();
            }
        };
        togAuthorizeLocallyButton = new JCheckBox(togAuthorizeLocally);
        togAuthorizeLocallyButton.setSelected(false);
        togAuthorizeLocallyButton.setEnabled(false);


	dumpChoicePanel = new JPanel();
        /*dumpChoicePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(0,0,0,0)));*/

        dumpChoicePanel.add(togDumpMessabeButton);
        dumpChoicePanel.add(togAuthorizeLocallyButton);

        commitButton = new JButton("Generate Config File");
        //cchildChoicePanel.add(togDumpMessabeButton);

        choices.setEnabled(false);
        //cchildChoicePanel.add(choices);
	//dumpChoicePanel.add(togDumpMessabeButton);
	//dumpChoicePanel.add(choices);

        cchildChoicePanel.add(timestampPanel);
        cchildChoicePanel.add(dumpChoicePanel);
        cchildChoicePanel.add(choices);


        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        gridbag.setConstraints(treeView, c);

        c.weighty = 0.0;		   //reset to the default
 	c.gridwidth = GridBagConstraints.RELATIVE; //end row
        c.gridheight = 1;

        gridbag.setConstraints(cchildChoicePanel, c);

        choicePanel.add(treeView);
        choicePanel.add(cchildChoicePanel);

        childSelectPanel1.add(choicePanel);

        childSelectPanel2 = new JPanel(new GridLayout(0,1));
        childSelectPanel2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Add SOAP elements"),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        // Action added for Add SOAP Element button
        Action action = new AbstractAction("Add new SOAP element") {
            public void actionPerformed(ActionEvent evt) {
                int flag = 0;
                String soapElement = "";

                    if (types.getSelectedItem().toString().trim().equals("qname")) {
                        soapElement = "{" + nameSpace.getText().toString() + "}" + addSoap.getText().toString();
                    } else if (types.getSelectedItem().toString().trim().equals("xpath")) {
                        soapElement = nameSpace.getText().toString() + ":" + addSoap.getText().toString();
                    } else if (types.getSelectedItem().toString().trim().equals("id")) {
                        soapElement = addSoap.getText().toString();
                    }

		// Check if the element has already been added
                for (int j=0; j<soapTarget.getModel().getSize(); j++) {
                    if (soapTarget.getModel().getElementAt(j).toString().trim().equals(
                            soapElement)) {
                        flag = 1;
                        JOptionPane.showMessageDialog(myWindow, "Soap element : " +
                                       soapElement + " has already been added");
                    }
                }
                if (0 == flag) {
                    model.add(chosenTarget.getModel().getSize(), soapElement);
                    model1.add(soapTarget.getModel().getSize(), soapElement);
                    JOptionPane.showMessageDialog(myWindow, "New SOAP element is added");
                    // Added the new target in the LinkList
                    Targets target = new Targets ( addSoap.getText().toString().trim(),
                                                   nameSpace.getText().toString().trim(),
                                                   types.getSelectedItem().toString().trim()
                                                 );
                    targetList.add(target);
                    if (!targetList.isEmpty()) {
                        Iterator iterator = targetList.iterator();
                        while (iterator.hasNext()) {
                            Targets soapTarget = (Targets) iterator.next();
                        }
                    }
                }
            }
        };

        JButton addSOAPElement = new JButton(action);
        JLabel addSoapLabel = new JLabel("Local name");
        addSoap = new JTextField(6);
        JLabel nameSpaceLabel = new JLabel("Namespace URI");
        nameSpace = new JTextField(6);
        String[] type = { "qname", "xpath", "id" };
        types = new JComboBox(type);
        types.setSelectedIndex(START_INDEX);

        childSelectPanel2.add(addSoapLabel);
        childSelectPanel2.add(addSoap);
        childSelectPanel2.add(nameSpaceLabel);
        childSelectPanel2.add(nameSpace);
        childSelectPanel2.add(types);
        childSelectPanel2.add(addSOAPElement);

        selectPanel.add(childSelectPanel2);
        selectPanel.add(childSelectPanel1);

        //Listen to events from the combo box.
        choices.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
    }


    public void DeleteSelectedFromChosenTarget() {
        int[] selectedIx = chosenTarget.getSelectedIndices();
        // Sort the selectedIx array in desceending order
        java.util.Arrays.sort(selectedIx);
        int k = 0;
	int l = selectedIx.length -1;
        int temp;
        for (;k<=l;k++, l--) {
            temp = selectedIx[k];
            selectedIx[k] = selectedIx[l];
            selectedIx[l] = temp;
        }
        // End Sort
        for (int i=0; i<selectedIx.length; i++) {
            if (elementDebug) {
                System.out.println("SOAP element : " +
                chosenTarget.getModel().getElementAt(selectedIx[i]) +
                    " is removed from chosen Target");
            }
            model.remove(selectedIx[i]);
        }
    }

    public void DeleteFromChosenTarget() {
        int i = 0;
        int size = chosenTarget.getModel().getSize();
        for (i=size-1; i>=0; i--) {
            model.remove(i);
        }

    }



    public void AddSelectedToChosenTarget() {
        int[] selectedIx = soapTarget.getSelectedIndices();
        int size = 0;
        int flag = 0;
        for (int i=0; i<selectedIx.length; i++) {
            size = chosenTarget.getModel().getSize();

            for (int j=0; j<size; j++) {
                if (chosenTarget.getModel().getElementAt(j).toString().trim().equals(
                        soapTarget.getModel().getElementAt(selectedIx[i]).toString().trim()))
                    flag = 1;
            }
            if (flag == 0) {
                if (elementDebug) {
                    System.out.println("SOAP element : " +
                    soapTarget.getModel().getElementAt(selectedIx[i]) +
                        " is added from available Target");
                }
                model.add(chosenTarget.getModel().getSize(),
                          soapTarget.getModel().getElementAt(selectedIx[i]));
            }
        }
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        MenuElement[] path =
           MenuSelectionManager.defaultManager().getSelectedPath();
        for (int i=0; i<path.length; i++) {
            Component c = path[i].getComponent();

            if (c instanceof JMenuItem) {
                JMenuItem mi = (JMenuItem)c;
                String label = mi.getText();
                if (label.equals("Selected Targets Deleted")) {
                    DeleteSelectedFromChosenTarget();
                } else {
                    AddSelectedToChosenTarget();
                }
            }
        }
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    }



    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create a new instance of ConfigFileGeneratorTool.
        ConfigFileGeneratorTool phases = new ConfigFileGeneratorTool();

        //Create and set up the window.
        firstWindow = new JFrame("Config File Generator");
        firstWindow.setLocationRelativeTo(null);
        firstWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        firstWindow.setContentPane(phases.childSelectPanel1);

        firstWindow.setJMenuBar(menuBar);

        //Display the window.
        firstWindow.pack();
        firstWindow.setVisible(true);
    }

    public Document createDomDocument() {
        try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                docFactory.setIgnoringComments(true);
                DocumentBuilder builder = docFactory.newDocumentBuilder();
                Document doc = builder.newDocument();
                return doc;
        }
        catch (ParserConfigurationException e) {
        }
        return null;
    }

    public void updateConfigFileWithUsernameToken() {
        try {
            Element usernameToken = doc.createElement("xwss:UsernameAndPassword");
            usernameToken.setAttribute("name", name.getText().toString().trim());
            usernameToken.setAttribute("password", password.getText().toString().trim());
            if (debug) {
                System.out.println("Entered into updateConfigFileWithUsernameToken Block");
                System.out.println("Insert Flag : " + insertFlag);
            }
            if (usernameTokenOption1.isSelected()) {
                usernameToken.setAttribute("useNonce", "true");
            }
            if (usernameTokenOption2.isSelected()) {
                usernameToken.setAttribute("digestPassword", "true");
            }

            usernameToken.setAttribute("id", usernameIDText.getText().toString());


            Element usernameTokenEncryptElement = doc.createElement("xwss:Encrypt");

            DefaultMutableTreeNode newNode = null;
            DefaultMutableTreeNode tempNode = SecurityConfiguration;


            // Add UsernameAndPassword node to JTree
            newNode = new DefaultMutableTreeNode("xwss:UsernameAndPassword");
            if (insertFlag && insertAfterNode != null) {
	        treeModel.insertNodeInto(newNode, tempNode, treeModel.getIndexOfChild(tempNode, insertAfterNode) );
	    } else {
	        treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            //treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            tempNode = newNode;

            newNode = new DefaultMutableTreeNode("name : " + name.getText().toString().trim());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

            newNode = new DefaultMutableTreeNode("password : " + password.getText().toString().trim());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

            if (usernameTokenOption1.isSelected()) {
               newNode = new DefaultMutableTreeNode("useNonce : true");
               treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }

            if (usernameTokenOption2.isSelected()) {
               newNode = new DefaultMutableTreeNode("digestPassword : true");
               treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }

            newNode = new DefaultMutableTreeNode("id : " + usernameIDText.getText().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
      
            /*
            // Add Encrypt Sub element if the userName token encryption option is chosen
            if (usernameTokenOption3.isSelected()) {
                newNode = new DefaultMutableTreeNode("xwss:Encrypt");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;


                if (usernameTokenEncrypt.isSelected()) {
                    usernameTokenEncryptElement.setAttribute("encryptContentOnly", "true");
                    newNode = new DefaultMutableTreeNode("encryptContentOnly : true");
                    treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                }
                if(!usernameTokenEncryptCertificate.getText().toString().equals(""))
                {
                    usernameTokenEncryptElement.setAttribute("receiverCertificateAlias",
                             usernameTokenEncryptCertificate.getText().toString());
                    newNode = new DefaultMutableTreeNode("receiverCertificateAlias : " +
                                                          usernameTokenEncryptCertificate.getText().toString());
                    treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                }
                usernameToken.appendChild(usernameTokenEncryptElement);
            }
			//element.appendChild(usernameToken);
            */

            if (insertFlag && insertPos != null) {
                currentPos.insertBefore(usernameToken, insertPos);
            } else {
                currentPos.appendChild(usernameToken);
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(myWindow, "User name Token is inserted");

            AddNewTargetsInAvailableTargetCache(usernameIDText.getText().toString());

	    automaticID = automaticID + 1;
            usernameIDText.setText(new Integer(automaticID).toString());

            already_saved = false;
            insertFlag = false;
            if (debug) {
                System.out.println("Insert Flag Unset : " + insertFlag);
            }
            insertPos = null;
        } catch (Exception e) {
            if (exceptionDebug) {
                System.out.println("Error in UsernameToken Block");
            }
            //e.printStackTrace();
        }
    }



    public void updateConfigFileWithSign() {
        try {
            if (0 == chosenTarget.getModel().getSize()) {
                int answer = JOptionPane.showConfirmDialog(myWindow,
                                         "No Targets : Perform Default Op : Sign the Body");
                if (answer != JOptionPane.YES_OPTION)
                    return;
            }
            Element element = doc.createElement("xwss:Sign");

            DefaultMutableTreeNode newNode = null;
            DefaultMutableTreeNode tempNode = SecurityConfiguration;


            // Add SIGN node to JTree
            newNode = new DefaultMutableTreeNode("xwss:Sign");
            if (insertFlag && insertAfterNode != null) {
                treeModel.insertNodeInto(newNode, tempNode, treeModel.getIndexOfChild(tempNode, insertAfterNode) );
            } else {
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            tempNode = newNode;

            element.setAttribute("id", signOpID.getText().toString());
            newNode = new DefaultMutableTreeNode("id : " + signOpID.getText().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

            if (signOption1.isSelected()) {
                element.setAttribute("includeKeyToken", "true");
                newNode = new DefaultMutableTreeNode("includeKeyToken : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }

            if (signOpTimestamp.isSelected()) {
                element.setAttribute("includeTimestamp", "true");
                newNode = new DefaultMutableTreeNode("includeTimestamp : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
	    }

            // --------------ADD X509Token or SymmectricKey---------------------------
            if (certificateType.getSelectedItem().toString().equals("X509Token")) {
                 Element x509Token = doc.createElement("X509Token");
                 x509Token.setAttribute("id", signCertID.getText().toString());
	         if (!senderCert.getText().toString().trim().equals("DEFAULT")) {
                     x509Token.setAttribute("certificateAlias",
                                      senderCert.getText().toString().trim());
                     x509Token.setAttribute("keyReferenceType", keyReferenceSign.getSelectedItem().toString());
		 }
                 newNode = new DefaultMutableTreeNode("X509Token");
                 treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                 DefaultMutableTreeNode newNode1 = new DefaultMutableTreeNode("id : " + 
                                                                  signCertID.getText().toString().trim());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());

                 if (!senderCert.getText().toString().trim().equals("DEFAULT")) {
                     newNode1 = new DefaultMutableTreeNode("certificateAlias : " + 
                                                                  senderCert.getText().toString().trim());
                     treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
	         }

                 newNode1 = new DefaultMutableTreeNode("keyReferenceType : " + 
                                                                  keyReferenceSign.getSelectedItem().toString());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                 element.appendChild(x509Token);
            }
            if (certificateType.getSelectedItem().toString().equals("SymmectricKey")) {
                 Element symmectricKey = doc.createElement("SymmectricKey");
                 symmectricKey.setAttribute("id", signCertID.getText().toString());
                 symmectricKey.setAttribute("keyAlias", senderCert.getText().toString().trim());

                 newNode = new DefaultMutableTreeNode("SymmectricKey");
                 treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                                                                                                                             
                 DefaultMutableTreeNode newNode1 = new DefaultMutableTreeNode("id : " +
                                                                  signCertID.getText().toString().trim());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());

                 newNode1 = new DefaultMutableTreeNode("keyAlias : " + senderCert.getText().toString().trim());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                 element.appendChild(symmectricKey);
            }
            /*if (!senderCert.getText().toString().trim().equals("<DEFAULT>")) {
                element.setAttribute("senderCertificateAlias",
                                      senderCert.getText().toString().trim());
                newNode = new DefaultMutableTreeNode("senderCertificateAlias : " + senderCert.getText().toString().trim());
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            element.setAttribute("keyReferenceType",
                                 keyReferenceSign.getSelectedItem().toString());

            newNode = new DefaultMutableTreeNode("keyReferenceType : " + keyReferenceSign.getSelectedItem().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());*/
	    //----------------END------------------------------------------------------

            if (0 == chosenTarget.getModel().getSize()) {
                getTargetDetail("Body");
                Element target = doc.createElement("xwss:Target");
                target.setAttribute("type", "qname");
                text = doc.createTextNode("{http://schemas.xmlsoap.org/soap/envelope}Body");
                target.appendChild(text);
                //target.setAttribute("{http://schemas.xmlsoap.org/soap/envelope}Body");
                element.appendChild(target);

                newNode = new DefaultMutableTreeNode("xwss:Target");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;

                newNode = new DefaultMutableTreeNode("type : " + "qname");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                newNode = new DefaultMutableTreeNode("value : " + "{http://schemas.xmlsoap.org/soap/envelope}Body");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }

            for (int j=0; j<chosenTarget.getModel().getSize(); j++) {

                // Used to store previous tempNode ... as it is the only position
                // where we can add a Target
                DefaultMutableTreeNode storeTempNode = tempNode;

                if (elementDebug) {
                    System.out.println("Target : " +
                        chosenTarget.getModel().getElementAt(j).toString() + " will be Signed");
                }
                getTargetDetail(chosenTarget.getModel().getElementAt(j).toString().trim());
                Element target = doc.createElement("xwss:Target");
                target.setAttribute("type", currentTargetType);
                text = doc.createTextNode(currentTargetValue);
                target.appendChild(text);
                //target.setAttribute("value", currentTargetValue);
                element.appendChild(target);

                newNode = new DefaultMutableTreeNode("xwss:Target");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;

                newNode = new DefaultMutableTreeNode("type : " + currentTargetType);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                newNode = new DefaultMutableTreeNode("value : " + currentTargetValue);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = storeTempNode;
            }
            if (insertFlag && insertPos != null) {
                currentPos.insertBefore(element, insertPos);
            } else {
                currentPos.appendChild(element);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(myWindow, "Config File is updated");

            AddNewTargetsInAvailableTargetCache(signOpID.getText().toString());
            AddNewTargetsInAvailableTargetCache(signCertID.getText().toString());

            // Update Automatic ID value 
            signOpID.setText(new Integer(automaticID).toString());
            automaticID = automaticID + 1;
	    signCertID.setText(new Integer(automaticID).toString());

            already_saved = false;
            insertFlag = false;
            if (debug) {
                System.out.println("Insert Flag Unset : " + insertFlag);
            }
            insertPos = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateConfigFileWithEncrypt() {
        try {
            if (0 == chosenTarget.getModel().getSize()) {
                int answer = JOptionPane.showConfirmDialog(myWindow,
                                         "No Targets : Perform Default Op : Encrypt the Body");
                if (answer != JOptionPane.YES_OPTION)
                    return;
            }
            Element element = doc.createElement("xwss:Encrypt");
            element.setAttribute("id", encryptOpID.getText().toString());

            DefaultMutableTreeNode newNode = null;
            DefaultMutableTreeNode tempNode = SecurityConfiguration;

            // Add ENCRYPT node to JTree
            newNode = new DefaultMutableTreeNode("xwss:Encrypt");
            if (insertFlag && insertAfterNode != null) {
                treeModel.insertNodeInto(newNode, tempNode, treeModel.getIndexOfChild(tempNode, insertAfterNode) );
            } else {
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            tempNode = newNode;
            newNode = new DefaultMutableTreeNode("id : " + encryptOpID.getText().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

            /*if (encryptOption1.isSelected()) {
                element.setAttribute("encryptContentOnly", "true");

                newNode = new DefaultMutableTreeNode("encryptContentOnly : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            if (!receiverCert.getText().toString().trim().equals("<DEFAULT>")) {
                element.setAttribute("receiverCertificateAlias",
                                      receiverCert.getText().toString().trim());

                newNode = new DefaultMutableTreeNode("receiverCertificateAlias : " +
                                                     receiverCert.getText().toString().trim());
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            element.setAttribute("keyReferenceType",
                                 keyReference.getSelectedItem().toString());

            newNode = new DefaultMutableTreeNode("keyReferenceType : " + keyReference.getSelectedItem().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());*/

            if (encryptCertificateType.getSelectedItem().toString().equals("X509Token")) {
                 Element x509Token = doc.createElement("X509Token");
                 x509Token.setAttribute("id", encryptCertID.getText().toString());
                 if (!receiverCert.getText().toString().trim().equals("DEFAULT")) {
                     x509Token.setAttribute("certificateAlias",
                                      receiverCert.getText().toString().trim());
                     x509Token.setAttribute("keyReferenceType", keyReferenceSign.getSelectedItem().toString());
                 }
                 newNode = new DefaultMutableTreeNode("X509Token");
                 treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                                                                                                                             
                 DefaultMutableTreeNode newNode1 = new DefaultMutableTreeNode("id : " +
                                                                  encryptCertID.getText().toString().trim());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                                                                                                                             
                 if (!senderCert.getText().toString().trim().equals("DEFAULT")) {
                     newNode1 = new DefaultMutableTreeNode("certificateAlias : " +
                                                                  receiverCert.getText().toString().trim());
                     treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                 }
                                                                                                                             
                 newNode1 = new DefaultMutableTreeNode("keyReferenceType : " +
                                                                  keyReferenceSign.getSelectedItem().toString());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                 element.appendChild(x509Token);
            }

            if (encryptCertificateType.getSelectedItem().toString().equals("SymmectricKey")) {
                 Element symmectricKey = doc.createElement("SymmectricKey");
                 symmectricKey.setAttribute("id", encryptCertID.getText().toString());
                 symmectricKey.setAttribute("keyAlias", receiverCert.getText().toString().trim());
                                                                                                                             
                 newNode = new DefaultMutableTreeNode("SymmectricKey");
                 treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                                                                                                                             
                 DefaultMutableTreeNode newNode1 = new DefaultMutableTreeNode("id : " +
                                                                  encryptCertID.getText().toString().trim());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                                                                                                                             
                 newNode1 = new DefaultMutableTreeNode("keyAlias : " + receiverCert.getText().toString().trim());
                 treeModel.insertNodeInto(newNode1, newNode, newNode.getChildCount());
                 element.appendChild(symmectricKey);
            }

            int encryptContentOnly = 0;

            if (0 == chosenTarget.getModel().getSize()) {
                getTargetDetail("Body");
                Element target = doc.createElement("xwss:Target");

                newNode = new DefaultMutableTreeNode("xwss:Target");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;

                target.setAttribute("type", "qname");

                newNode = new DefaultMutableTreeNode("type : qname");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

	        text = doc.createTextNode("{http://schemas.xmlsoap.org/soap/envelope}Body");
		target.appendChild(text);
                //target.setAttribute("value", "{http://schemas.xmlsoap.org/soap/envelope}Body");

                newNode = new DefaultMutableTreeNode("value : {http://schemas.xmlsoap.org/soap/envelope}Body");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                /*encryptContentOnly = JOptionPane.showConfirmDialog(myWindow,
                    "For Target {http://schemas.xmlsoap.org/soap/envelope}Body \n Is contentOnly true ?");
                if (encryptContentOnly == JOptionPane.YES_OPTION) {
                    target.setAttribute("contentOnly", "true");
                    newNode = new DefaultMutableTreeNode("contentOnly : true");
                    treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                } else {
                    target.setAttribute("contentOnly", "false");
                    newNode = new DefaultMutableTreeNode("contentOnly : false");
                    treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                }
    		*/

                element.appendChild(target);
            }

            for (int j=0; j<chosenTarget.getModel().getSize(); j++) {

                // tempNode is the only position where we can add targets
                DefaultMutableTreeNode storeTempNode = tempNode;

                if (elementDebug) {
                    System.out.println("Target : " +
                         chosenTarget.getModel().getElementAt(j).toString() + " will be Encrypted");
                }
                getTargetDetail(chosenTarget.getModel().getElementAt(j).toString().trim());
                Element target = doc.createElement("xwss:Target");

                newNode = new DefaultMutableTreeNode("xwss:Target");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;

                target.setAttribute("type", currentTargetType);

                newNode = new DefaultMutableTreeNode("type : " + currentTargetType);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

		text = doc.createTextNode(currentTargetValue);
		target.appendChild(text);
                //target.setAttribute("value", currentTargetValue);

                newNode = new DefaultMutableTreeNode("value : " + currentTargetValue);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                encryptContentOnly = JOptionPane.showConfirmDialog(myWindow,
                    "For Target " + currentTargetValue + " \n Is contentOnly true ?");
                if (encryptContentOnly == JOptionPane.YES_OPTION) {
                    target.setAttribute("contentOnly", "true");
                    newNode = new DefaultMutableTreeNode("contentOnly : true");
                    treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                } else {
                    target.setAttribute("contentOnly", "false");
                    newNode = new DefaultMutableTreeNode("contentOnly : false");
                    treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                }

                element.appendChild(target);
                tempNode = storeTempNode;
            }
            if (insertFlag && insertPos != null) {
                currentPos.insertBefore(element, insertPos);
            } else {
                currentPos.appendChild(element);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(myWindow, "Config File is updated");


            AddNewTargetsInAvailableTargetCache(encryptOpID.getText().toString());
            AddNewTargetsInAvailableTargetCache(encryptCertID.getText().toString());
                                                                                                                             
            // Update Automatic ID value
            encryptOpID.setText(new Integer(automaticID).toString());
            automaticID = automaticID + 1;
            encryptCertID.setText(new Integer(automaticID).toString());
                                                                                                                             

            already_saved = false;
            insertFlag = false;
            if (debug) {
                System.out.println("Insert Flag Unset : " + insertFlag);
            }
            insertPos = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConfigFileWithDumpMessage() {
        try {

            TreePath path = tree.getNextMatch("dump", 0, Bias.Forward);
            MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();


            // Toggle the value
            if (node.toString().trim().equals("dumpMessages : false")) {
                currentPos.setAttribute("dumpMessages", "true");

                treeModel.removeNodeFromParent(node);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("dumpMessages : true");
                treeModel.insertNodeInto(newNode , SecurityConfiguration, 1);
            } else {
                currentPos.setAttribute("dumpMessages", "false");

                treeModel.removeNodeFromParent(node);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("dumpMessages : false");
                treeModel.insertNodeInto(newNode , SecurityConfiguration, 1);
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            already_saved = false;
            //JOptionPane.showMessageDialog(myWindow, "Config File is updated");
        } catch (Exception e) {
            if (exceptionDebug) {
                 System.out.println("Error Block");
            }
            //e.printStackTrace();
        }
    }
    public void updateConfigFileWithAuthorizeLocally() {
       try {
                                                                                                                             
            TreePath path = tree.getNextMatch("authorize", 0, Bias.Forward);
            MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
                                                                                                                             
            // Toggle the value
            if (node.toString().trim().equals("authorizeLocally : false")) {
                currentPos.setAttribute("authorizeLocally", "true");
                                                                                                                             
                treeModel.removeNodeFromParent(node);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("authorizeLocally : true");
                treeModel.insertNodeInto(newNode , SecurityConfiguration, 2);
            } else {
                currentPos.setAttribute("authorizeLocally", "false");
                                                                                                                             
                treeModel.removeNodeFromParent(node);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("authorizeLocally : false");
                treeModel.insertNodeInto(newNode , SecurityConfiguration, 2);
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));
                                                                                                                             
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            already_saved = false;
            //JOptionPane.showMessageDialog(myWindow, "Config File is updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConfigFileWithTimeStamp() {
		try {
			NodeList rootList = doc.getChildNodes();
			Node rootNode = rootList.item(0);
			NodeList list = rootNode.getChildNodes();

		    TreePath path = tree.getNextMatch("xwss:Timestamp", 0, Bias.Forward);

            if (path != null) {
		        MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
 		    	treeModel.removeNodeFromParent(node);

				Node timeStamp = list.item(0);
				Node deletedNode = rootNode.removeChild(timeStamp);

		    } else {
		    	DefaultMutableTreeNode newNode = null;
    	        DefaultMutableTreeNode tempNode = SecurityConfiguration;

                newNode = new DefaultMutableTreeNode("xwss:Timestamp");
                treeModel.insertNodeInto(newNode, tempNode, 3);
                tempNode = newNode;

                newNode = new DefaultMutableTreeNode("timeout : " + timeoutInterval.getText().toString());
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                Element element = doc.createElement("xwss:Timestamp");
                element.setAttribute("timeout", timeoutInterval.getText().toString());

                Element position = (Element)list.item(0);

                currentPos.insertBefore(element, position);
		    }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            already_saved = false;
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("updateConfigFileWithTimeStamp error block");
		}
	}

    /*public void updateConfigFileWithTimeStamp() {
        try {

            TreePath path = tree.getNextMatch("useTime", 0, Bias.Forward);
            MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();

            // Toggle the value
            if (node.toString().trim().equals("useTimestamps : false")) {
                currentPos.setAttribute("useTimestamps", "true");

                treeModel.removeNodeFromParent(node);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("useTimestamps : true");
                treeModel.insertNodeInto(newNode , SecurityConfiguration, 2);
            } else {
                currentPos.setAttribute("useTimestamps", "false");

                treeModel.removeNodeFromParent(node);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("useTimestamps : false");
                treeModel.insertNodeInto(newNode , SecurityConfiguration, 2);
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            already_saved = false;
            //JOptionPane.showMessageDialog(myWindow, "Config File is updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    public void getTargetDetail( String targetName ) {
        Iterator iterator = targetList.iterator();
        while (iterator.hasNext()) {
            Targets soapTarget = (Targets) iterator.next();
            if (soapTarget.targetName.equals(targetName)) {
                currentTargetType = soapTarget.targetType;
                currentTargetValue = soapTarget.targetName;
                return;
            }
        }
    }

    public void updateConfigFileWithRequireSign() {
        try {
            if (0 == chosenTarget.getModel().getSize()) {
                JOptionPane.showMessageDialog(myWindow, "Choose Targets before performing the operation");
                return;
            }
            Element element = doc.createElement("xwss:requireSignature");

            DefaultMutableTreeNode newNode = null;
            DefaultMutableTreeNode tempNode = SecurityConfiguration;

            // Add REQUIRE SIGNATURE node to JTree
            newNode = new DefaultMutableTreeNode("xwss:requireSignature");
            if (insertFlag && insertAfterNode != null) {
                treeModel.insertNodeInto(newNode, tempNode, treeModel.getIndexOfChild(tempNode, insertAfterNode) );
            } else {
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            tempNode = newNode;

            if (signContentOption.isSelected()) {
                element.setAttribute("signedTokenRequired", "true");

                newNode = new DefaultMutableTreeNode("signedTokenRequired : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            element.setAttribute("keyReferenceType",
                                 keyReferenceForRequireSign.getSelectedItem().toString());

            newNode = new DefaultMutableTreeNode("keyReferenceType : " +
                                                  keyReferenceForRequireSign.getSelectedItem().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

            for (int j=0; j<chosenTarget.getModel().getSize(); j++) {

                DefaultMutableTreeNode storeTempNode = tempNode;

                if (elementDebug) {
                    System.out.println("Target : " +
                        chosenTarget.getModel().getElementAt(j).toString() + " should be Signed");
                }
                getTargetDetail(chosenTarget.getModel().getElementAt(j).toString().trim());
                Element target = doc.createElement("xwss:Target");

                newNode = new DefaultMutableTreeNode("xwss:Target");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;

                target.setAttribute("type", currentTargetType);

                newNode = new DefaultMutableTreeNode("type : " + currentTargetType);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

	        text = doc.createTextNode(currentTargetValue);
		target.appendChild(text);
                //target.setAttribute("value", currentTargetValue);

                newNode = new DefaultMutableTreeNode("value : " + currentTargetValue);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                element.appendChild(target);
                tempNode = storeTempNode;
            }
            if (insertFlag && insertPos != null) {
                currentPos.insertBefore(element, insertPos);
            } else {
                currentPos.appendChild(element);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(myWindow, "Config File is updated");
            already_saved = false;
            insertFlag = false;
            if (debug) {
                System.out.println("Insert Flag Unset : " + insertFlag);
            }
            insertPos = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConfigFileWithRequireEncryption() {
        try {
            if (0 == chosenTarget.getModel().getSize()) {
                JOptionPane.showMessageDialog(myWindow, "Choose Targets before performing the operation");
                return;
            }
            Element element = doc.createElement("xwss:requireEncryption");

            DefaultMutableTreeNode newNode = null;
            DefaultMutableTreeNode tempNode = SecurityConfiguration;

            // Add REQUIRE ENCRYPTION node to JTree
            newNode = new DefaultMutableTreeNode("xwss:requireEncryption");
            if (insertFlag && insertAfterNode != null) {
                treeModel.insertNodeInto(newNode, tempNode, treeModel.getIndexOfChild(tempNode, insertAfterNode) );
            } else {
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            tempNode = newNode;

            if (encryptContentOption.isSelected()) {
                element.setAttribute("encryptedContentRequired", "true");

                newNode = new DefaultMutableTreeNode("encryptedContentRequired : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            element.setAttribute("keyReferenceType",
                                 keyReferenceForRequireEncryption.getSelectedItem().toString());

            newNode = new DefaultMutableTreeNode("keyReferenceType : " +
                                                 keyReferenceForRequireEncryption.getSelectedItem().toString());
            treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

            for (int j=0; j<chosenTarget.getModel().getSize(); j++) {

                DefaultMutableTreeNode storeTempNode = tempNode;

                if (elementDebug) {
                    System.out.println("Target : " +
                    chosenTarget.getModel().getElementAt(j).toString() + " should be in Encrypted Form");
                }
                getTargetDetail(chosenTarget.getModel().getElementAt(j).toString().trim());
                Element target = doc.createElement("xwss:Target");

                newNode = new DefaultMutableTreeNode("xwss:Target");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                tempNode = newNode;

                target.setAttribute("type", currentTargetType);

                newNode = new DefaultMutableTreeNode("type : " + currentTargetType);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

		text = doc.createTextNode(currentTargetValue);
		target.appendChild(text);
                //target.setAttribute("value", currentTargetValue);

                newNode = new DefaultMutableTreeNode("value : " + currentTargetValue);
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                element.appendChild(target);
                tempNode = storeTempNode;
            }
            if (insertFlag && insertPos != null) {
                currentPos.insertBefore(element, insertPos);
            } else {
                currentPos.appendChild(element);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(myWindow, "Config File is updated");
            already_saved = false;
            insertFlag = false;
            if (debug) {
                System.out.println("Insert Flag Unset : " + insertFlag);
            }
            insertPos = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConfigFileWithRequireAuthentication() {
        try {
            Element element = doc.createElement("xwss:requireAuthentication");

            DefaultMutableTreeNode newNode = null;
            DefaultMutableTreeNode tempNode = SecurityConfiguration;

            // Add REQUIRE ENCRYPTION node to JTree
            newNode = new DefaultMutableTreeNode("xwss:requireAuthentication");
            if (insertFlag && insertAfterNode != null) {
                treeModel.insertNodeInto(newNode, tempNode, treeModel.getIndexOfChild(tempNode, insertAfterNode) );
            } else {
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            tempNode = newNode;

            if (encryptedTokenRequired.isSelected()) {
                element.setAttribute("encryptedTokenRequired", "true");

                newNode = new DefaultMutableTreeNode("encryptedTokenRequired : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());

                element.setAttribute("keyReferenceType",
                                 keyReferenceTypeForRequireAuthentication.getSelectedItem().toString());

                newNode = new DefaultMutableTreeNode("keyReferenceType : " +
                                                   keyReferenceTypeForRequireAuthentication.getSelectedItem().toString());
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            } else {
                newNode = new DefaultMutableTreeNode("encryptedTokenRequired : false");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            if (nonceRequired.isSelected()) {
                element.setAttribute("nonceRequired", "true");
                newNode = new DefaultMutableTreeNode("nonceRequired : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            } else {
                newNode = new DefaultMutableTreeNode("nonceRequired : false");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }
            if (passwordDigestRequired.isSelected()) {
                newNode = new DefaultMutableTreeNode("passwordDigestRequired : true");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
                element.setAttribute("passwordDigestRequired", "true");
            } else {
                newNode = new DefaultMutableTreeNode("passwordDigestRequired : false");
                treeModel.insertNodeInto(newNode, tempNode, tempNode.getChildCount());
            }

            if (insertFlag && insertPos != null) {
                //System.out.println("insertFlag : " + insertFlag + " insertPos : " + insertPos.toString());
                currentPos.insertBefore(element, insertPos);
            } else {
                //System.out.println("InsertFlag : " + insertFlag );
                currentPos.appendChild(element);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(myWindow, "Config File is updated");
            insertFlag = false;
            already_saved = false;
            if (debug) {
                System.out.println("Insert Flag Unset : " + insertFlag);
            }
            insertPos = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Config file is initialized when u click on Create New menu item
    public void initailizeConfigFile() {

        try {
            resultFile.delete();
            doc = createDomDocument();
                currentPos = doc.createElement("xwss:SecurityConfiguration");
                currentPos.setAttribute("xmlns:xwss", "http://com.sun.xml.wss.configuration");
                currentPos.setAttribute("dumpMessages", "false");
                currentPos.setAttribute("authorizeLocally", "true");
                doc.appendChild(currentPos);

                DOMSource source1 = new DOMSource(doc);
                StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();

                transformer.transform(source1, result);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    public void updateTreeViewWithCurrentFile(Node node, DefaultMutableTreeNode currentPlace) {
      try {
        NodeList list = null;
	list =  node.getChildNodes();
        if ( 0 == list.getLength()) {
            flag = 0;
            return;
        }

        Node childNode = null;
        DefaultMutableTreeNode newNode = null;
        DefaultMutableTreeNode storeNode = null;

        for (int i=0; i<list.getLength(); i++) {
            childNode = list.item(i);
	    String elementName = null;
	    try {
                elementName = ((Element)childNode).getTagName();
	    } catch(Exception ex) {
		//System.out.println("Child Node : " + childNode.toString());
		break;
	    }
            if (debug) {
                System.out.println(i + "of " + list.getLength() + " : " + childNode.toString());
                System.out.println("Element : " + elementName);
            }

            if (!elementName.equals("xwss:SecurityConfiguration")) {
                newNode = new DefaultMutableTreeNode(elementName);
                treeModel.insertNodeInto(newNode, currentPlace, currentPlace.getChildCount());
                storeNode = newNode;
            } else {
                storeNode = currentPlace;
            }

            NamedNodeMap attrs = childNode.getAttributes();

            int numAttrs = attrs.getLength();
            for (int j=0; j<numAttrs; j++) {
	      if (!elementName.startsWith("xwss:Target")) {
                Attr attr = (Attr)attrs.item(j);
                newNode = new DefaultMutableTreeNode(attr.getNodeName().toString() + " : " +
                                                     attr.getNodeValue());
                treeModel.insertNodeInto(newNode, storeNode, storeNode.getChildCount());
                if (debug) {
                    System.out.println("Attribute : " + attr.getNodeName().toString() + "  Value : " +
                                           attr.getNodeValue().toString());
                }
                if (attr.getNodeName().toString().equals("dumpMessages") &&
                        attr.getNodeValue().toString().equals("true")) {
                    togDumpMessabeButton.setSelected(true);
                }
                if (attr.getNodeName().toString().equals("authorizeLocally") &&
                        attr.getNodeValue().toString().equals("true")) {
                    togAuthorizeLocallyButton.setSelected(true);
                }
	      }

              if (elementName.startsWith("xwss:Sign") || elementName.startsWith("xwss:Encrypt") 
                                         || elementName.startsWith("xwss:UsernameAndPassword") 
                                         || elementName.startsWith("X509Token") 
					 || elementName.startsWith("SymmectricKey")) {
                    if (attrs.item(j).getNodeName().toString().equals("id")) {
                        AddNewTargetsInAvailableTargetCache(attrs.item(j).getNodeValue().toString());
		        try {
			    if (automaticID <= new Integer(attrs.item(j).getNodeValue().toString()).intValue()) {
                                automaticID = new Integer(attrs.item(j).getNodeValue().toString()).intValue() + 1;
                            }
			} catch (Exception ex) {}
		    }
              } 

              if (elementName.startsWith("xwss:Target")) {
                    if (attrs.item(j).getNodeName().toString().equals("type")) {
                        extractedTargetType = attrs.item(j).getNodeValue().toString();
                    }

		    text = (Text)childNode.getFirstChild();
                    System.out.println("Target Value : " + text.getData());
                    resolveExtractedTarget(text.getData());

                    if (debug) {
                        System.out.println("Target Name : " + extractedTargetName);
                        System.out.println("Name Space  : " + extractedTargetNamespace);
                        System.out.println("_______________________________________________________\n\n");
                    }

                    Targets target = new Targets ( extractedTargetName,
                                                   extractedTargetNamespace,
                                                   extractedTargetType
                                                 );
                    // Insert value : Target Name in the tree

	            newNode = new DefaultMutableTreeNode("value : " + text.getData());
                    treeModel.insertNodeInto(newNode, storeNode, storeNode.getChildCount());

		    newNode = new DefaultMutableTreeNode("type : " + extractedTargetType);
                    treeModel.insertNodeInto(newNode, storeNode, storeNode.getChildCount());

                    if (extractedTargetType.equals("id")) {
		        try {
                            if ( new Integer(extractedTargetName).intValue() > automaticID)
  	                        automaticID = new Integer(extractedTargetName).intValue() + 1;
		        } catch (Exception ex) {}
   		    }

                    // Add the Target to the TargetList only when no such target present
                    if ( !searchTargetList(text.getData()) ) {
                        targetList.add(target);
                        model1.add(soapTarget.getModel().getSize(), text.getData());
                    }
		    break;
              }
            }

            if (debug) {
                System.out.println("\n\nCalling function again with.............");
                System.out.println(childNode.toString() + "\n\n\n");
            }
            updateTreeViewWithCurrentFile(childNode, storeNode);
        }
      } catch (Exception ex) {
	    ex.printStackTrace();
      }
    }

    public void showForm() {
                     if (debug) {
                         System.out.println("Insert Flag in show Form : " + insertFlag);
                     }

                     if ( (choices.getSelectedItem().equals("Sign") && !(insertFlag)) ||
                           (selectedMenuLabel.equals("Sign") && insertFlag) ) {
                         if (operation_active) {
                             JOptionPane.showMessageDialog(myWindow, "Stop the active operation first \n before starting another operation");
                             return;
                         }
                         myWindow = new JFrame("SignOperation");
                         myWindow.setLocation((int)firstWindow.getLocation().getX()-200, (int)firstWindow.getLocation().getY());
			 myWindow.getContentPane().add(childOpPanel1);
                         myWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                         myWindow.pack();
                         myWindow.show();
                         signOpID.setText(new Integer(automaticID).toString());
                         automaticID = automaticID + 1;
			 signCertID.setText(new Integer(automaticID).toString());
                         automaticID = automaticID + 1;
                         DeleteFromChosenTarget();
                         operation_active = true;
                     } else if ((choices.getSelectedItem().equals("Encrypt") && !(insertFlag)) ||
                           (selectedMenuLabel.equals("Encrypt") && insertFlag) ) {
                         if (operation_active) {
                             JOptionPane.showMessageDialog(myWindow, "Stop the active operation first \n before starting another operation");
                             return;
                         }
                         myWindow = new JFrame("EncryptOperation");
                         myWindow.setLocation((int)firstWindow.getLocation().getX()-200, (int)firstWindow.getLocation().getY());
			 myWindow.getContentPane().add(childOpPanel2);
                         myWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                         myWindow.pack();
                         myWindow.show();
                         encryptOpID.setText(new Integer(automaticID).toString());
                         automaticID = automaticID + 1;
                         encryptCertID.setText(new Integer(automaticID).toString());
                         automaticID = automaticID + 1;
                         DeleteFromChosenTarget();
                         operation_active = true;
                     } else if ((choices.getSelectedItem().equals("UsernameToken") && !(insertFlag)) ||
                           (selectedMenuLabel.equals("UsernameAndPassword") && insertFlag) ) {
                         if (operation_active) {
                             JOptionPane.showMessageDialog(myWindow, "Stop the active operation first \n before starting another operation");
                             return;
                         }
                         myWindow = new JFrame("Import UsernameAndPassword Element");
                         myWindow.setLocation((int)firstWindow.getLocation().getX()-200, (int)firstWindow.getLocation().getY());
                         myWindow.getContentPane().add(childOpPanel3);
                         myWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                         myWindow.pack();
                         myWindow.show();
                         automaticID = automaticID + 1;
                         usernameIDText.setText(new Integer(automaticID).toString());
                         DeleteFromChosenTarget();
                         operation_active = true;
                     } else if ((choices.getSelectedItem().equals("Require Signature") && !(insertFlag)) ||
                           (selectedMenuLabel.equals("requireSignature") && insertFlag) ) {
                         if (operation_active) {
                             JOptionPane.showMessageDialog(myWindow, "Stop the active operation first \n before starting another operation");
                             return;
                         }
                         childRequireSignPanel11 = new JPanel(new GridLayout(0,1,0,0));
                         childRequireSignPanel11.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder("Sign Op. attributes"),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));
                         signContentOption = new JCheckBox("Sign The Token", true);
                         childRequireSignPanel11.add(signContentOption);

                         childRequireSignPanel12 = new JPanel(new GridLayout(0,1));
                         childRequireSignPanel12.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder(""),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));

                         Action addTargetForRequireSign = new AbstractAction("Add new Targets") {
                             public void actionPerformed(ActionEvent evt) {
                                 if (first_click_on_add_target)
                                     return;
                                 first_click_on_add_target = true;
                                 addSoapTarget = new JFrame("New Target Addition");
                                 addSoapTarget.setLocation((int)targetWindow.getLocation().getX()-200, (int)targetWindow.getLocation().getY() + 50);
                                 addSoapTarget.getContentPane().add(childSelectPanel2);
                                 addSoapTarget.getContentPane().add(requireSignPanel);
                                 addSoapTarget.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                 addSoapTarget.pack();
                                 addSoapTarget.show();
                             }
                         };
                         addNewTargetButtonForRequireSign = new JButton(addTargetForRequireSign);


                         Action showTargetForRequireSign = new AbstractAction("Required Signed Targets") {
                             public void actionPerformed(ActionEvent evt) {
                                 if (first_click)
                                     return;
                                 first_click = true;
                                 targetWindow = new JFrame("Available Targets informations");
                                 targetWindow.setLocation((int)myWindow.getLocation().getX()-200,
                                                                       (int)myWindow.getLocation().getY());
                                 targetWindow.getContentPane().add(childChildOpPanel12);
                                 targetWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                 targetWindow.pack();
                                 targetWindow.show();
                             }
                         };
                         showAvailableTargetButtonForRequireSign = new JButton(showTargetForRequireSign);

                         childRequireSignPanel12.add(showAvailableTargetButtonForRequireSign);

                         childRequireSignPanel1 = new JPanel(new GridLayout(0,1));
                         childRequireSignPanel1.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder(""),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));
                         childRequireSignPanel1.add(childRequireSignPanel11);
                         childRequireSignPanel1.add(childRequireSignPanel12);

                         JPanel childRequireSignPanel2 = new JPanel();
                         childRequireSignPanel2.setBorder(BorderFactory.createCompoundBorder(
                                 BorderFactory.createTitledBorder("Key Reference Type"),
                                 BorderFactory.createEmptyBorder(5,5,5,5)));

                         keyReferenceForRequireSign = new JComboBox(keyReferenceType);
                         keyReferenceForRequireSign.setSelectedIndex(START_INDEX);
                         childRequireSignPanel2.add(keyReferenceForRequireSign);

                         Action actionForRequireSign = new AbstractAction("Apply") {
                             public void actionPerformed(ActionEvent evt) {
                                 updateConfigFileWithRequireSign();
                                 toggleInsertFlag();
                             }
                         };
                         JButton updateConfigFileForRequireSign = new JButton(actionForRequireSign);

                         Action closeRequireSignOpAction = new AbstractAction("Close") {
                             public void actionPerformed(ActionEvent evt) {
                                 operation_active = false;
                                 first_click = false;
                                 first_click_on_add_target = false;
                                 toggleInsertFlag();
                                 if (myWindow != null) {
                                     myWindow.setVisible(false);
                                 }
                                 if (addSoapTarget != null) {
                                     addSoapTarget.setVisible(false);
                                 }
                                 if (targetWindow != null) {
                                     targetWindow.setVisible(false);
                                 }
                             }
                         };
                         JButton closeRequireSignOpActionButton = new JButton(closeRequireSignOpAction);

                         childRequireSignPanel2.add(updateConfigFileForRequireSign);

                         requireSignPanel = new JPanel(new GridLayout(0,1,0,10));


                         requireSignPanel.add(childRequireSignPanel11);
                         requireSignPanel.add(childRequireSignPanel2);

                         JPanel requireSignPanelButtonPanel = new JPanel(new GridLayout(0,1,0,5));
                         requireSignPanelButtonPanel.add(showAvailableTargetButtonForRequireSign);
                         requireSignPanelButtonPanel.add(updateConfigFileForRequireSign);
                         requireSignPanelButtonPanel.add(closeRequireSignOpActionButton);

                         requireSignPanel.add(requireSignPanelButtonPanel);

                         myWindow = new JFrame("Require Signature");
                         myWindow.setLocation((int)firstWindow.getLocation().getX()-200, (int)firstWindow.getLocation().getY());
                         myWindow.getContentPane().add(requireSignPanel);
                         myWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                         myWindow.pack();
                         myWindow.show();
                         DeleteFromChosenTarget();
                         operation_active = true;
                     } else if ( (choices.getSelectedItem().equals("Require Encryption") && !(insertFlag)) ||
                           (selectedMenuLabel.equals("requireEncryption") && insertFlag) ) {
                         if (operation_active) {
                             JOptionPane.showMessageDialog(myWindow, "Stop the active operation first \n before starting another operation");
                             return;
                         }
                         childRequireEncryptionPanel11 = new JPanel(new GridLayout(0,1));
                         childRequireEncryptionPanel11.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder("Encryption Op. attributes"),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));
                         encryptContentOption = new JCheckBox("Encrypt content only", true);
                         childRequireEncryptionPanel11.add(encryptContentOption);

                         childRequireEncryptionPanel12 = new JPanel(new GridLayout(0,1));
                         childRequireEncryptionPanel12.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder(""),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));

                         Action addTargetForRequireEncryption = new AbstractAction("Add new Targets") {
                             public void actionPerformed(ActionEvent evt) {
                                 if (first_click_on_add_target)
                                     return;
                                 first_click_on_add_target = true;
                                 addSoapTarget = new JFrame("New Target Addition");
                                 addSoapTarget.setLocation((int)targetWindow.getLocation().getX()-200, (int)targetWindow.getLocation().getY() + 50);
                                 addSoapTarget.getContentPane().add(childSelectPanel2);
                                 addSoapTarget.getContentPane().add(requireEncryptionPanel);
                                 addSoapTarget.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                 addSoapTarget.pack();
                                 addSoapTarget.show();
                             }
                         };
                         addNewTargetButtonForRequireEncryption = new JButton(addTargetForRequireEncryption);


                         Action showTargetForRequireEncryption = new AbstractAction("Required Encrypted Targets") {
                             public void actionPerformed(ActionEvent evt) {
                                 if (first_click)
                                     return;
                                 first_click = true;
                                 targetWindow = new JFrame("Available Targets informations");
                                 targetWindow.setLocation((int)myWindow.getLocation().getX()-200,
                                                                              (int)myWindow.getLocation().getY());
                                 targetWindow.getContentPane().add(childChildOpPanel12);
                                 targetWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                 targetWindow.pack();
                                 targetWindow.show();
                             }
                         };
                         showAvailableTargetButtonForRequireEncryption = new JButton(showTargetForRequireEncryption);

                         childRequireEncryptionPanel12.add(showAvailableTargetButtonForRequireEncryption);

                         childRequireEncryptionPanel1 = new JPanel(new GridLayout(0,1));
                         childRequireEncryptionPanel1.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder(""),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));
                         childRequireEncryptionPanel1.add(childRequireEncryptionPanel11);
                         childRequireEncryptionPanel1.add(childRequireEncryptionPanel12);

                         JPanel childRequireEncryptionPanel2 = new JPanel();
                         childRequireEncryptionPanel2.setBorder(BorderFactory.createCompoundBorder(
                                 BorderFactory.createTitledBorder("Key Reference Type"),
                                 BorderFactory.createEmptyBorder(5,5,5,5)));

                         keyReferenceForRequireEncryption = new JComboBox(keyReferenceType);
                         keyReferenceForRequireEncryption.setSelectedIndex(START_INDEX);
                         childRequireEncryptionPanel2.add(keyReferenceForRequireEncryption);

                         Action actionForRequireEncryption = new AbstractAction("Apply") {
                             public void actionPerformed(ActionEvent evt) {
                                 updateConfigFileWithRequireEncryption();
                                 toggleInsertFlag();
                             }
                         };
                         JButton updateConfigFileForRequireEncryption = new JButton(actionForRequireEncryption);

                         Action closeRequireEncryptionOpAction = new AbstractAction("Close") {
                             public void actionPerformed(ActionEvent evt) {
                                 operation_active = false;
                                 first_click = false;
                                 first_click_on_add_target = false;
                                 toggleInsertFlag();
                                 if (myWindow != null) {
                                     myWindow.setVisible(false);
                                 }
                                 if (addSoapTarget != null) {
                                     addSoapTarget.setVisible(false);
                                 }
                                 if (targetWindow != null) {
                                     targetWindow.setVisible(false);
                                 }
                             }
                         };
                         JButton closeRequireEncryptionOpActionButton = new JButton(closeRequireEncryptionOpAction);

                         childRequireEncryptionPanel2.add(updateConfigFileForRequireEncryption);

                         requireEncryptionPanel = new JPanel(new GridLayout(0,1,0,5));
                         requireEncryptionPanel.setBorder(BorderFactory.createCompoundBorder(
                                 BorderFactory.createTitledBorder(""),
                                 BorderFactory.createEmptyBorder(5,5,5,5)));


                         requireEncryptionPanel.add(childRequireEncryptionPanel11);
                         requireEncryptionPanel.add(childRequireEncryptionPanel2);

                         JPanel requireEncryptionPanelButtonPanel = new JPanel(new GridLayout(0,1,0,5));
                         requireEncryptionPanelButtonPanel.add(showAvailableTargetButtonForRequireEncryption);
                         requireEncryptionPanelButtonPanel.add(updateConfigFileForRequireEncryption);
                         requireEncryptionPanelButtonPanel.add(closeRequireEncryptionOpActionButton);

                         requireEncryptionPanel.add(requireEncryptionPanelButtonPanel);

                         myWindow = new JFrame("Require Encryption");
                         myWindow.setLocation((int)firstWindow.getLocation().getX()-200, (int)firstWindow.getLocation().getY());
                         myWindow.getContentPane().add(requireEncryptionPanel);
                         myWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                         myWindow.pack();
                         myWindow.show();
                         DeleteFromChosenTarget();
                         operation_active = true;
                     } else if ((choices.getSelectedItem().equals("Require Authentication") && !(insertFlag)) ||
                           (selectedMenuLabel.equals("requireAuthentication") && insertFlag) ) {
                         if (operation_active) {
                             JOptionPane.showMessageDialog(myWindow, "Stop the active operation first \n before starting another operation");
                             return;
                         }
                         requireAuthenticationPanel = new JPanel(new GridLayout(0,1));
                         requireAuthenticationPanel.setBorder(BorderFactory.createCompoundBorder(
                                          BorderFactory.createTitledBorder("Require Authentication Op. attributes"),
                                          BorderFactory.createEmptyBorder(5,5,5,5)));

                         encryptedTokenRequired = new JCheckBox("Encrypted Form", false);

                         nonceRequired = new JCheckBox("Required Nonce", false);
                         passwordDigestRequired = new JCheckBox("Required Digested Password", false);
                         keyReferenceTypeForRequireAuthentication = new JComboBox(keyReferenceType);

                         Action actionForRequireAuthentication = new AbstractAction("Apply") {
                             public void actionPerformed(ActionEvent evt) {
                                 updateConfigFileWithRequireAuthentication();
                                 toggleInsertFlag();
                             }
                         };
                         JButton updateConfigFileForRequireAuthentication = new JButton(actionForRequireAuthentication);

                         Action closeRequireAuthenticationOpAction = new AbstractAction("Close") {
                             public void actionPerformed(ActionEvent evt) {
                                 operation_active = false;
                                 first_click = false;
                                 first_click_on_add_target = false;
                                 toggleInsertFlag();
                                 if (myWindow != null) {
                                     myWindow.setVisible(false);
                                 }
                                 if (addSoapTarget != null) {
                                     addSoapTarget.setVisible(false);
                                 }
                                 if (targetWindow != null) {
                                     targetWindow.setVisible(false);
                                 }
                             }
                         };
                         JButton closeRequireAuthenticationOpActionButton = new JButton(closeRequireAuthenticationOpAction);

                         requireAuthenticationPanel.add(encryptedTokenRequired);
                         requireAuthenticationPanel.add(nonceRequired);
                         requireAuthenticationPanel.add(passwordDigestRequired);
                         requireAuthenticationPanel.add(keyReferenceTypeForRequireAuthentication);
                         requireAuthenticationPanel.add(updateConfigFileForRequireAuthentication);
                         requireAuthenticationPanel.add(closeRequireAuthenticationOpActionButton);

                         myWindow = new JFrame("Require Authentication");
                         myWindow.setLocation((int)firstWindow.getLocation().getX()-200, (int)firstWindow.getLocation().getY());
                         myWindow.getContentPane().add(requireAuthenticationPanel);
                         myWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                         myWindow.pack();
                         myWindow.show();
                         DeleteFromChosenTarget();
                         operation_active = true;
                     }

    }

    //Algorithm to modify the config file. When a node is selected and press Delete button then
    //the node is deleted from the tree Node. Also the config file element is deleted automatically.
    //Algo that can find the relationship between tree Node and the config file element

    public void modifyConfigFileElement() {

        TreePath currentSelection = tree.getSelectionPath();

        if (currentSelection == null) {
            JOptionPane.showMessageDialog(myWindow, "Choose one Node");
            return;
        }
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());

        String patternStr = currentNode.toString();
        boolean allowOrNot = false;

            if ( patternStr.equals("xwss:Sign") ||
                 patternStr.equals("xwss:Encrypt") ||
                 patternStr.equals("xwss:UsernameAndPassword") ||
                 patternStr.equals("xwss:requireSignature") ||
                 patternStr.equals("xwss:requireEncryption") ||
                 patternStr.equals("xwss:requireAuthentication") ) {

                allowOrNot = true;
            }

            if (!allowOrNot) {
                JOptionPane.showMessageDialog(myWindow, "Choose one Valid Node \n Sign, Encrypt, UsernameAndPassword, requireSignature \n requireEncryption, requireAuthentication");
                operationNotAllowed = false;
                return;
            }

            /// Initialize the insertPosition :
            NodeList rootList = doc.getChildNodes();
            Node rootNode = rootList.item(0);
            NodeList list = rootNode.getChildNodes();

            insertPos = (Element)list.item(treeModel.getIndexOfChild(SecurityConfiguration, currentNode) - 3);
            if (debug) {
                System.out.println("Insert Pos : " + insertPos.toString() +
                     " Position : " + (treeModel.getIndexOfChild(SecurityConfiguration, currentNode) - 3) );
                System.out.println("Current Node : " + currentNode.toString());
                for (int j=0; j<list.getLength(); j++) {
                    System.out.println(list.item(j) + "\n\n");
                }
            }

            insertFlag = true;
            if (debug) {
                System.out.println("Insert Flag set: " + insertFlag);
            }
            insertAfterNode = currentNode;
            return;
    }

    public void deleteConfigFileElement() {
      try {
        TreePath currentSelection = tree.getSelectionPath();

        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());

            currentlyDeletedNode = currentNode;
            //Allow the user to delete only Sign/Encrypt/Target/UsernameAndPassword/RequireSignature
            //RequireEncryption/RequireAuthentication nodes

            String patternStr = currentNode.toString();
            boolean allowOrNot = false;

            if ( patternStr.equals("xwss:Sign") ||
                 patternStr.equals("xwss:Encrypt") ||
                 patternStr.equals("xwss:UsernameAndPassword") ||
                 patternStr.equals("xwss:requireSignature") ||
                 patternStr.equals("xwss:requireEncryption") ||
                 patternStr.equals("xwss:requireAuthentication") ) {

                 allowOrNot = true;
            }

            if (!allowOrNot)
                return;

            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                // remove the DOM node
                NodeList rootList = doc.getChildNodes();
                Node rootNode = rootList.item(0);
                NodeList list = rootNode.getChildNodes();

                Node removeNode = list.item(treeModel.getIndexOfChild(SecurityConfiguration, currentlyDeletedNode) - 3);
                if (debug) {
                    System.out.println("Removed Node : " + removeNode.toString() +
                          " Position : " + treeModel.getIndexOfChild(SecurityConfiguration, currentlyDeletedNode) );
                }
                Node deletedNode = rootNode.removeChild(removeNode);
                // Write it to the Config File
                try {
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new FileOutputStream(resultFilePath));

                    TransformerFactory transFactory = TransformerFactory.newInstance();
                    Transformer transformer = transFactory.newTransformer();
                    transformer.transform(source, result);
                    already_saved = false;
                } catch (Exception ex) {
                    if (exceptionDebug) {
                        System.out.println("Error in deleteConfigFileElement Block");
                    }
                }

                // Remove Node from Tree View
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
      } catch (Exception ex) {
            JOptionPane.showMessageDialog(myWindow, "This node can not be deleted \n Only child nodes of the xwss:SecurityConfiguration can be deleted");
      }
    }

    //Button Action to show all the aliases present in the keystore/ truststore
    public void showAllAliases(final String signOrEncrypt) {
        JFrame showAliasFrame = new JFrame("Get Aliases");
        showAliasFrame.setLocation((int)firstWindow.getLocation().getX()+50, (int)firstWindow.getLocation().getY()-200);

        JPanel showAliasPanel = new JPanel(new GridLayout(0,1));
        showAliasPanel.setPreferredSize(new Dimension(200,200));

        final JTextField passwordField;

        if ( (signOrEncrypt.equals("Sign")) )
            passwordField = new JTextField("keystore password");
        else if ( (signOrEncrypt.equals("Encrypt")) )
            passwordField = new JTextField("truststore password");
        else
            passwordField = new JTextField("");


        final DefaultListModel aliasModel = new DefaultListModel();
        JList aliasList = new JList(aliasModel);
        JScrollPane scrollableList = new JScrollPane(aliasList);

        String label = "";
        if ( (signOrEncrypt.equals("Sign")) )
           label = "Choose one keyStore";
        if ( (signOrEncrypt.equals("Encrypt")) )
           label = "Choose one TrustStore";

        Action showAlias = new AbstractAction(label) {
            public void actionPerformed(ActionEvent evt) {
                JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
                fc.addChoosableFileFilter(new MyFilter1());
                fc.showOpenDialog(firstWindow);
                File selFile = fc.getSelectedFile();
                if (selFile == null || !selFile.exists()) {
                    JOptionPane.showMessageDialog(myWindow, "Choose a valid file");
                    return;
                }
                try {
                    // Load the keystore in the user's home directory
                    FileInputStream is = new FileInputStream(selFile);
                    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
                    String password = passwordField.getText().toString();
                    keystore.load(is, password.toCharArray());
                   // List the aliases
                   Enumeration enumVal = keystore.aliases();
                   aliasModel.clear();
                   for (int i=0; enumVal.hasMoreElements();i++ ) {
                       String alias = (String)enumVal.nextElement();
                       // Does alias refer to a private key?
                       boolean privateKey = keystore.isKeyEntry(alias);
                       // Does alias refer to a trusted certificate?
                       boolean trustKey = keystore.isCertificateEntry(alias);
                       if ( (signOrEncrypt.equals("Sign")) && (privateKey) ) {
                           aliasModel.add(i, alias);
                       }
                       if ( (signOrEncrypt.equals("Encrypt")) && (trustKey) ) {
                           aliasModel.add(i, alias);
                       }
                   }
                   is.close();
               } catch (java.security.cert.CertificateException e) {
               } catch (NoSuchAlgorithmException e) {
               } catch (FileNotFoundException e) {
                   // Keystore does not exist
               } catch (KeyStoreException e) {
               } catch (IOException e) {
               }

            }
        };
        JButton chooseKeystore = new JButton(showAlias);

        showAliasPanel.add(passwordField);
        showAliasPanel.add(chooseKeystore);
        showAliasPanel.add(aliasList);

        showAliasFrame.getContentPane().add(showAliasPanel);
        showAliasFrame.pack();
        showAliasFrame.show();
    }

    public void toggleInsertFlag() {
        insertFlag = false;
        if (debug) {
            System.out.println("Toggle Insert Flag -> Insert Flag Unset : " + insertFlag);
        }
    }

    // This function will expand the whole Tree
    public void expandAll(JTree tree) {
       if (debug) {
           System.out.println("ExpandAll Tree called");
       }
       tree.expandRow(0);
    }

    // Extract target name and namespace from target
    public void resolveExtractedTarget(String target) {
        if (extractedTargetType.equals("qname")) {
            extractedTargetName = target.substring(target.lastIndexOf('}') + 1, target.length());
            extractedTargetNamespace = target.substring(1, target.lastIndexOf('}') );
        }

        if (extractedTargetType.equals("xpath")) {
            extractedTargetName = target.substring(target.lastIndexOf(':') + 1, target.length());
            extractedTargetNamespace = target.substring(0, target.lastIndexOf(':') );
        }
     
	if (extractedTargetType.equals("id")) {
	    extractedTargetName = target;
	}
    }

    // TO DO: search the target list : return true if such target present else return false
    public boolean searchTargetList(String targetName) {
        if (debug) {
            System.out.println("___________________entered_______________");
        }

        Targets soapTarget = null;
        if (!targetList.isEmpty()) {
            Iterator iterator = targetList.iterator();
            while (iterator.hasNext()) {
                soapTarget = (Targets) iterator.next();
                if (debug) {
                    System.out.println("soapTaget : " + soapTarget.getTargetName());
                    System.out.println("Passed Target : " + targetName);
                }
                if (soapTarget.getTargetName().equals(targetName)) {
                    if (debug) {
                        System.out.println("___________________returned_______________\n\n\n");
                    }
                    return true;
                }
            }
        }

        if (debug) {
             System.out.println("___________________returned_______________\n\n\n");
        }

        return false;
    }

    public void AddNewTargetsInAvailableTargetCache(String soapElement) {
        int flag = 0;
                                                                                                                             
        //System.out.println("SOAP Element : " + soapElement);
        // Check if the element has already been added
        for (int j=0; j<soapTarget.getModel().getSize(); j++) {
            if (soapTarget.getModel().getElementAt(j).toString().trim().equals(soapElement)) {
                flag = 1;
                JOptionPane.showMessageDialog(myWindow, "Soap element : " +
                                       soapElement + " has already been added");
            }
        }
        if (0 == flag) {
            //model.add(chosenTarget.getModel().getSize(), soapElement);
            model1.add(soapTarget.getModel().getSize(), soapElement);
            // Added the new target in the LinkList
            Targets target = new Targets ( soapElement, "", "id" );
            targetList.add(target);
            if (!targetList.isEmpty()) {
                Iterator iterator = targetList.iterator();
                while (iterator.hasNext()) {
                   Targets soapTarget = (Targets) iterator.next();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }
}

