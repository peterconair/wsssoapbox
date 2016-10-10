package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.backing.TreeBean;

//import org.primefaces.event.ColumnResizeEvent;

@ManagedBean(name = "soapRequestOutline")
@RequestScoped
public class SoapRequestOutlineTab implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TreeTableRequestBean treeTableBean;

	 private static final Logger logger=LoggerFactory.getLogger(TreeBean.class);

	public SoapRequestOutlineTab() {
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
