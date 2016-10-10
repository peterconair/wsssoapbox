package com.javatech.payment.service;

import com.javatech.payment.persistent.PersistentPaymentService;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.javatech.payment.mapping.PaymentRequest;
import com.javatech.payment.persistent.PersistentPayment;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles payment services like adding a new record .
 * <p>
 * This is a Spring MVC @Service. Spring WS can communicate with this service.
 */
@Service("paymentService")
@Transactional
public class PaymentService {

    // @Resource(name = "persistentPayment")
    @Autowired
    PersistentPayment persistentPayment;
    protected static Logger logger = Logger.getLogger("service");
  
     /*
    @Autowired
    ServletContext servletContext; 
    private String fileName = "payments.xml";
    private String fileDir = servletContext.getRealPath("/WEB-INFO/config/") ;
    private String xmlFile = fileDir + fileName;
     */
   
    private double totalAmount = 0.0;
    private int id = 0;
    private List<PaymentRequest> payments = new ArrayList<PaymentRequest>();

    /**
     * Processes the actual subscriptions. Throws an error if the user already exists
     */
    synchronized public void doPayment(PaymentRequest paymentRequest) {

        persistentPayment = new PersistentPaymentService();
        List<PaymentRequest> paymentReqList = new ArrayList<PaymentRequest>();
        paymentReqList = persistentPayment.getPayments();

        id = getID(paymentReqList);

        // payment id generator
        DecimalFormat nft = new DecimalFormat("#0000000.###");
        nft.setDecimalSeparatorAlwaysShown(false);
        String paymentID = nft.format(++id);


        // payment date
        Date date = new Date();
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = fm.format(date);

        logger.debug("Incomming Card No. :" + paymentRequest.getCardNumber());

        logger.debug("New payment ID" + paymentID);
        // Search HashMap
        if (checkPaid(paymentRequest.getCardNumber())) {

            logger.error("You is already paid!");

            paymentRequest.setPaymentDate(strDate);
            paymentRequest.setPaymentID("P-" + paymentID);
            paymentRequest.setPaymentStatus("Rejected");
            logger.error("Payment ID: " + paymentRequest.getPaymentID() + " is Rejected");

            // Add to HashMap
            //  payments.add(paymentRequest);
            paymentReqList.add(paymentRequest);
            persistentPayment.addPayment(paymentReqList);

            throw new RuntimeException("You have already paid. and you cannot paid twice");
        }

        paymentRequest.setPaymentDate(strDate);
        paymentRequest.setPaymentID("P-" + paymentID);
        paymentRequest.setPaymentStatus("Compleated");

        // payments.add(paymentRequest);
        paymentReqList.add(paymentRequest);
        persistentPayment.addPayment(paymentReqList);

        logger.debug("Name :" + paymentRequest.getNameOnCard());
        logger.debug("Payment ID: " + paymentRequest.getPaymentID() + " is Compleated");
        logger.debug("The payment has been successfully.");
        // Add to HashMap

    }

    private int getID(List<PaymentRequest> payList) {
        Iterator it = payList.iterator();
        int id = 0;
        while (it.hasNext()) {
            PaymentRequest pr = (PaymentRequest) it.next();
            int tmp = Integer.parseInt(pr.getPaymentID().replaceAll("P-", "").trim());
            if (tmp < id || tmp > id) {
                id = tmp;
            }
        }
        return id;
    }

    /**
     * Retrieves all subscriptions. Not the best way to retrieve however
     * 
     * @return list of subscribed users
     */
    public List<PaymentRequest> getAll() {
        /*
        if (payments != null && !payments.isEmpty()) {
        return (ArrayList<PaymentRequest>) payments;
        } */

        persistentPayment = new PersistentPaymentService();
        return payments = persistentPayment.getPayments();
    }

    /**
     * @return the totalAmount
     */
    public double getTotalAmount() {
        totalAmount = 0;
        if (payments != null && !payments.isEmpty()) {
            Iterator<PaymentRequest> it = payments.iterator();

            while (it.hasNext()) {
                PaymentRequest req = it.next();
                totalAmount += Double.parseDouble(req.getAmount());
            }
        }
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // find dupicated Card Number
    private boolean checkPaid(String card) {
        if (!payments.isEmpty()) {
            Iterator it = payments.iterator();
            while (it.hasNext()) {
                PaymentRequest preq = (PaymentRequest) it.next();
                if (preq.getCardNumber().equals(card)) {
                    return true;
                }
            }
        }
        return false;
    }
}
