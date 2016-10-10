package org.wsssoapbox.bean.backing;

import org.wsssoapbox.bean.model.domain.User;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.slf4j.Logger;
import org.primefaces.event.FlowEvent;
import org.slf4j.LoggerFactory;


public class UserWizard implements Serializable{

	
	private static final long serialVersionUID = -6218870590265539381L;

	private User user = new User();

	private boolean skip;

	private static Logger logger = LoggerFactory.getLogger(UserWizard.class);
	//private static final Log log = LogFactory.getLog(LoggingPhaseListener.class);

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void save(ActionEvent actionEvent) {
		// Persist user

		FacesMessage msg = new FacesMessage("Successful", "Welcome :"
				+ user.getFirstname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());
		System.out.println("Current wizard step:" + event.getOldStep());
		System.out.println("Next step:" + event.getNewStep());

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}
}
