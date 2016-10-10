package com.javatech.payment.endpoint;

import com.javatech.payment.mapping.PaymentRequest;
import com.javatech.payment.mapping.PaymentResponse;
import com.javatech.payment.service.PaymentService;
import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Handles subscription requests. 
 * <p>
 *<pre>
 * @Endpoint: similar to Spring MVC's @Controller. 
 * @PayloadRoot: similar to Spring MVC's @RequestMapping</pre>
 */
@Endpoint
public class PaymentEndpoint {

    protected static Logger logger = Logger.getLogger("endpoint");
    private PaymentService paymentService;
    
    // The namespace of both request and response as declared in the XSD file
    public static final String NAMESPACE_URI = "http://payment.javatech.com/ws/schema/oss";
    // The local name of the expected request.
    public static final String REQUEST_LOCAL_NAME = "paymentRequest1";

    /**
     * Processes subscriptions. Actual processing is delegated to a service
     * <p>
     * In order for this method to be triggered, the localPart and namespace must
     * match from the incoming XML request. This is exactly similar to Spring MVC.
     * <p>
     * In Spring MVC, we declare:
     * <pre>@RequestMapping(value = "/admin", method = RequestMethod.GET)</pre>
     * <p>
     * In Spring WS, we declare:
     * <pre>@PayloadRoot(localPart = "myRequest", namespace = "http://my.domain.com...")</pre>
     */
    @PayloadRoot(localPart = REQUEST_LOCAL_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public PaymentResponse processPayment(@RequestPayload PaymentRequest paymentRequest) {

        try {
            logger.debug("Received subscription request");

            try {
                logger.debug("Delegate to service");

                getPaymentService().doPayment(paymentRequest);

            } catch (Exception e) {
                logger.error("The payment rejected.");
                logger.error("Error : " + e.getMessage());
                // Return response
                PaymentResponse response = new PaymentResponse();
                response.setCode("REJECTED");
                response.setDescription("The payment rejected.");
                response.setReferenceCode(paymentRequest.getPaymentID());

                return response;
            }

        } catch (Exception e) {
            logger.error("Problem with payment request");
            logger.error("Error : " + e.getMessage());
            // Return response
            PaymentResponse response = new PaymentResponse();
            response.setCode("REJECTED");
            response.setDescription("The payment rejected.");
            response.setDescription(paymentRequest.getPaymentID());
            return response;
        }
        logger.debug("The payment is Successfully. ");

        PaymentResponse response = new PaymentResponse();
        response.setCode("COMPLEATED");
        response.setDescription("The payment is Successfully.");
        response.setReferenceCode(paymentRequest.getPaymentID());
        // Return response
        return response;
    }

    /**
     * @return the paymentService
     */
    public PaymentService getPaymentService() {
        return paymentService;
    }

    /**
     * @param paymentService the paymentService to set
     */
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
