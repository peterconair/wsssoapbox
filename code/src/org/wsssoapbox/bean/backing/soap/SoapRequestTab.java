package org.wsssoapbox.bean.backing.soap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name = "soapRequestTab")
@RequestScoped
public class SoapRequestTab {
	private static final Log logger = LogFactory.getLog(SoapRequestTab.class);

	public SoapRequestTab() {
		logger.debug("start public SoapRequestTab() ");

		logger.debug("end public SoapRequestTab() ");
	}

	public String onTabChangeUpdate() {
		return "";
	}

	public void onTabChange(TabChangeEvent event) {
		logger.info("Tab Changed Active Tab: " + event.getTab().getTitle());
		String tabSelected = event.getTab().getTitle();

		if (tabSelected.equals("Raw")) {

		} else if (tabSelected.equals("Form")) {

		} else if (tabSelected.equals("Outline")) {

		} else if (tabSelected.equals("XML")) {

		}
	}
}
