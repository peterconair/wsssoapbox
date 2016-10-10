/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatech.payment.persistent;

import com.javatech.payment.mapping.PaymentRequest;
import java.util.List;

/**
 *
 * @author Peter
 */

public interface PersistentPayment {

    public void addPayment(List<PaymentRequest> paymentList);

    public List<PaymentRequest> getPayments();
}
