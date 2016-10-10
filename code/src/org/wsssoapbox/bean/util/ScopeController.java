package org.wsssoapbox.bean.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ScopeController {
	public static void setSessionMap(String name, Object object) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(name, object);
	}

	public static Object getSessionMap(String name) {
		Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
		return object;
	}
}
