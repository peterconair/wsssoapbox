package org.wsssoapbox.bean.backing;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="preference")
@SessionScoped
public class PreferencesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean logRendered;
	public boolean isLogRendered() {
		return logRendered;
	}
	public void setLogRendered(boolean logRendered) {
		this.logRendered = logRendered;
	}
	public boolean isNavigatorRendered() {
		return navigatorRendered;
	}
	public void setNavigatorRendered(boolean navigatorRendered) {
		this.navigatorRendered = navigatorRendered;
	}
	public boolean isTopMenuRendered() {
		return topMenuRendered;
	}
	public void setTopMenuRendered(boolean topMenuRendered) {
		this.topMenuRendered = topMenuRendered;
	}
	private boolean navigatorRendered;
	private boolean topMenuRendered;
	

}
