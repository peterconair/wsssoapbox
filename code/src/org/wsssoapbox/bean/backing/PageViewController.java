package org.wsssoapbox.bean.backing;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "pageViewController")
@SessionScoped
public class PageViewController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean wsdlImportPanel = true;
	private boolean mainPanel = true;
	private boolean includePanel = true;;
	private boolean mainDetailsPanel = true;
	private boolean operationCommandPanel = true;
	private boolean overviewPanel = true;
	private boolean navigatorCollapse = false;
	

	// disable Response Tab ,will enable after sent request message 
	private boolean responseTab = false;
	private int messageTabIndex = 0;

	// Outline Tab in Response Tab 
	private boolean responseOutlineTab = true;;
	
	public int getMessageTabIndex() {
		return messageTabIndex;
	}

	public void setMessageTabIndex(int messageTabIndex) {
		this.messageTabIndex = messageTabIndex;
	}

	public PageViewController() {
		wsdlImportPanel = true;
		mainPanel = true;
		overviewPanel = true;
	}

	public boolean isMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(boolean mainPanel) {
		this.mainPanel = mainPanel;
	}

	public boolean isIncludePanel() {
		return includePanel;
	}

	public void setIncludePanel(boolean includePanel) {
		this.includePanel = includePanel;
	}

	public boolean isOperationCommandPanel() {
		return operationCommandPanel;
	}

	public void setOperationCommandPanel(boolean operationCommandPanel) {
		this.operationCommandPanel = operationCommandPanel;
	}

	public boolean isOverviewPanel() {
		return overviewPanel;
	}

	public void setOverviewPanel(boolean overviewPanel) {
		this.overviewPanel = overviewPanel;
	}

	public boolean isWsdlImportPanel() {
		return wsdlImportPanel;
	}

	public void setWsdlImportPanel(boolean wsdlImportPanel) {
		this.wsdlImportPanel = wsdlImportPanel;
	}

	public boolean isMainDetailsPanel() {
		return mainDetailsPanel;
	}

	public void setMainDetailsPanel(boolean mainDetailsPanel) {
		this.mainDetailsPanel = mainDetailsPanel;
	}

	public boolean isNavigatorCollapse() {
		return navigatorCollapse;
	}

	public void setNavigatorCollapse(boolean navigatorCollapse) {
		this.navigatorCollapse = navigatorCollapse;
	}

	public boolean isResponseTab() {
		return responseTab;
	}

	public void setResponseTab(boolean responseTab) {
		this.responseTab = responseTab;
	}

	public boolean isResponseOutlineTab() {
		return responseOutlineTab;
	}

	public void setResponseOutlineTab(boolean responseOutlineTab) {
		this.responseOutlineTab = responseOutlineTab;
	}

}
