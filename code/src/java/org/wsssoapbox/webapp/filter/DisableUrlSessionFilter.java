/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.webapp.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter
 */
//@WebFilter(filterName = "DisableUrlSessionFilter", urlPatterns = {"/*"})
public class DisableUrlSessionFilter implements Filter {

   private static final Logger logger = LoggerFactory.getLogger(DisableUrlSessionFilter.class);

   public DisableUrlSessionFilter() {
   }

   /**
    * Filters requests to disable URL-based session identifiers. 
    */
   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      // skip non-http requests
      if (!(request instanceof HttpServletRequest)) {
         chain.doFilter(request, response);
         return;
      }

      logger.debug("DisableUrlSessionFilter() ");
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;

      /*
      // clear session if session id in URL
      if (httpRequest.isRequestedSessionIdFromURL()) {
      HttpSession session = httpRequest.getSession();
      if (session != null) {
      logger.debug("DisableUrlSessionFilter() : Destroy session id : " + session.getId());
      session.invalidate();
      }
      }
       */
      // wrap response to remove URL encoding
      HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {

         @Override
         public String encodeRedirectUrl(String url) {
            return url;
         }

         @Override
         public String encodeRedirectURL(String url) {
            return url;
         }

         @Override
         public String encodeUrl(String url) {
            return url;
         }

         @Override
         public String encodeURL(String url) {
            return url;
         }
      };

      // process next request in chain
      chain.doFilter(request, response);
      // chain.doFilter(request, wrappedResponse);
   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   @Override
   public void destroy() {
   }
}
