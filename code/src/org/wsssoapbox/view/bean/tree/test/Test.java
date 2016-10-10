package org.wsssoapbox.view.bean.tree.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.wsssoapbox.bean.model.wsdl.OperationBean;

import javax.wsdl.WSDLException;

import org.ow2.easywsdl.schema.api.XmlException;


public class Test {

	/**
	 * @param args
	 * @throws XmlException 
	 * @throws WSDLException
	 */
	public static void main(String[] args) throws XmlException {

		List<OperationBean> operationList;

		/*
		 * MessagesBean m1 = new MessagesBean(new Date(), new
		 * Date().toString()); MessagesBean m2 = new MessagesBean(new Date(),
		 * new Date().toString()); List<MessagesBean> messages = new
		 * ArrayList<MessagesBean>(); messages.add(m1); messages.add(m2);
		 * 
		 * OperationBean o1 = new OperationBean("Operation1", messages);
		 * OperationBean o2 = new OperationBean("Operation2");
		 * 
		 * List<OperationBean> operation = new ArrayList<OperationBean>();
		 * operation.add(o1); operation.add(o2);
		 * 
		 * PortBean p1 = new PortBean("Port1", operation); PortBean p2 = new
		 * PortBean("Port2", operation);
		 * 
		 * List<PortBean> service1 = new ArrayList<PortBean>();
		 * service1.add(p1); service1.add(p2);
		 * 
		 * ServiceBean s2 = new ServiceBean("Service1", service1); ServiceBean
		 * s1 = new ServiceBean("Service1", service1);
		 */
		 String url = "http://www.pttplc.com/pttinfo.asmx?WSDL";
		//String url = "http://localhost:8888/WebServiceTutorial/ws/doc/hello?wsdl";
		//PharseWSDL ph = new PharseWSDL();
		//ph.getWSDLInfo(url);
		
		//ParseWSDLCommand ph = new ParseWSDLCommand(url);
		//ph.getOperaters();
		/*		
		operationList = ph.getOperaters();

		for (int i = 0; i < operationList.size(); i++) {
			OperationBean opt = operationList.get(i);
			System.out.println("Opration : " + i +"  >> " + opt.getName());
		}
*/
		/*
		 * 
		 * 
		 * for (PortBean p : s1.getService()) { System.out.println(p.getName());
		 * for (OperationBean o : p.getOperation()) { System.out.println(">>>>>"
		 * + o.getName()); for (int i = 0; i < messages.size(); i++) {
		 * System.out.println(">>>>>>>>>>>>>" + messages.get(i).getName()); } }
		 * }
		 */
	}
}
