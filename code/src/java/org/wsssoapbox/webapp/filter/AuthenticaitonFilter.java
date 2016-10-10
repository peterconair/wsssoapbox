/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wsssoapbox.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.database.User;
import org.wsssoapbox.view.util.ScopeController;

public class AuthenticaitonFilter implements Filter {

   private static final Logger logger = LoggerFactory.getLogger(AuthenticaitonFilter.class);

   @Override
   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {


      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) resp;
      HttpSession session = request.getSession(false);
      String url = request.getContextPath() + "/main/login.xhtml";

      if (session != null && session.getAttribute("user") != null) {
         User user = (User) ScopeController.getSessionMap("user");
         logger.debug("*******************************");
         logger.debug("Current user loged in : " + user.getUsername());
         logger.debug("*******************************");
         chain.doFilter(req, resp); // Logged-in user found, so just continue request.
      } else {
         logger.debug("*******************************");
         logger.debug("You not log in. ");
         logger.debug("*******************************");
         logger.debug("URL to redirect : " + url);
         response.sendRedirect(url); // No logged-in user found, so redirect to login page.
         // context.getExternalContext().redirect(url);
      }


   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   @Override
   public void destroy() {
   }
}
