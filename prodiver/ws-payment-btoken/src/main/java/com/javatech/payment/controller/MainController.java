package com.javatech.payment.controller;

import com.javatech.payment.service.PaymentService;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * the adminpage, however.
 */
@Controller
@RequestMapping("/main")
public class MainController {

    protected static Logger logger = Logger.getLogger("controller");
    @Resource(name = "paymentService")
    private PaymentService paymentService;

    /**
     * Handles and retrieves payment users and
     * display it in a JSP page
     * 
     * @return the name of the JSP page
     */
    /*
    @RequestMapping(method = RequestMethod.GET)
    public String getPaymentPage(Model model) {
    logger.debug("Received request to show payment page");
    
    // Attach list of subscriptions to the Model
    model.addAttribute("payments", paymentService.getAll());
    
    // This will resolve to /WEB-INF/jsp/payment.jsp
    return "paymentpage";
    }
     */
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String getPaymentPage(Model model) {
        logger.debug("Received request to show payment page");

        // Attach list of subscriptions to the Model
        model.addAttribute("payments", paymentService.getAll());
        model.addAttribute("total", paymentService.getTotalAmount());
        // This will resolve to /WEB-INF/jsp/payment.jsp
        return "paymentpage";
    }
}
