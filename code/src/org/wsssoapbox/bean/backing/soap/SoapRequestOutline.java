package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.wsssoapbox.bean.model.soap.SoapBodyBean;
import org.wsssoapbox.bean.model.soap.SoapEnvelopeBean;
import org.wsssoapbox.bean.model.soap.SoapFaultBean;
import org.wsssoapbox.bean.model.soap.SoapHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.bean.model.soap.SoapMessageBean;
import org.wsssoapbox.bean.util.ScopeController;
import org.wsssoapbox.view.bean.TreeTableRequestBean;

//import org.primefaces.event.ColumnResizeEvent;

@ManagedBean(name = "soapRequestOutline")
@RequestScoped
public class SoapRequestOutline implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TreeTableRequestBean treeTableBean;

	private static final Log logger = LogFactory.getLog(SoapRequestOutline.class);

	public SoapRequestOutline() {
		logger.debug("start public SoapRequestOutline()");
		
		
		logger.debug("end public SoapRequestOutline()");

	}

	public TreeTableRequestBean getTreeTableBean() {
		return treeTableBean;
	}

	public void setTreeTableBean(TreeTableRequestBean treeTableBean) {
		this.treeTableBean = treeTableBean;
	}
}
