package org.wsssoapbox.bean.backing.soap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name = "soapResponseTab")
@RequestScoped
public class SoapResponseTab {
	private static final Log logger = LogFactory.getLog(SoapResponseTab.class);

	public SoapResponseTab() {
		logger.debug("start public SoapResponseTab() ");

		logger.debug("end public SoapResponseTab() ");
	}

	public String onTabChangeUpdate() {
		return "";
	}

	public void onTabChange(TabChangeEvent event) {
		logger.info("Tab Changed Active Tab: " + event.getTab().getTitle());
		String tabSelected = event.getTab().getTitle();

		if (tabSelected.equals("Raw")) {

		} else if (tabSelected.equals("Outline")) {

		} else if (tabSelected.equals("XML")) {

		}
	}
}
