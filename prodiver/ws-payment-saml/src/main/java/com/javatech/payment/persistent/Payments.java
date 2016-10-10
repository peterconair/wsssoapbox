/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatech.payment.persistent;

import com.javatech.payment.mapping.PaymentRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter
 */
public class Payments {
    private List<PaymentRequest> payments = new ArrayList();

        /**
         * @return the payments
         */
        public List<PaymentRequest> getPayments() {
            return payments;
        }

        /**
         * @param payments the payments to set
         */
        public void add(PaymentRequest payment) {
            this.payments.add(payment);
        }
}
