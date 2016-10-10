/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatech.payment.persistent;

import com.javatech.payment.mapping.PaymentRequest;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Peter
 */
@Service("persistentPayment")
public class PersistentPaymentService implements PersistentPayment {

    protected static Logger logger = Logger.getLogger("service");
    @Autowired
    ServletContext servletContext;
    private String fileName = "payment-ws.xml";
    private String fileDir;
    private String xmlFile;

    public PersistentPaymentService() {
        if (servletContext != null) {
            fileDir = servletContext.getRealPath("/WEB-INFO/config/");
            xmlFile = fileDir + fileName;
        } else {
            xmlFile = fileName;
        }
        logger.debug("servletContext :" + servletContext);
        logger.debug("File :" + xmlFile);
    }

    @Override
    public void addPayment(List<PaymentRequest> paymentList) {



        Payments payments = new Payments();
        Iterator it = paymentList.iterator();
        while (it.hasNext()) {
            PaymentRequest preq = (PaymentRequest) it.next();
            payments.add(preq);
        }

        if (payments != null) {
            XStream xstream = new XStream();
            xstream.alias("payment", PaymentRequest.class);
            xstream.alias("payments", Payments.class);
            xstream.addImplicitCollection(Payments.class, "payments");

            File file = new File(xmlFile);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("File : " + file.getAbsolutePath());
            FileWriter fw;
            try {
                fw = new FileWriter(file, false);
                xstream.toXML(payments, fw);
                fw.flush();
                fw.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public List<PaymentRequest> getPayments() {

        Payments payments = new Payments();
        XStream xstream = new XStream();
        xstream.alias("payment", PaymentRequest.class);
        xstream.alias("payments", Payments.class);
        xstream.addImplicitCollection(Payments.class, "payments");

        try {
            File file = new File(xmlFile);

            if (!file.exists()) {
                file.createNewFile();
            }
            payments = (Payments) xstream.fromXML(file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return payments.getPayments();
    }
}
