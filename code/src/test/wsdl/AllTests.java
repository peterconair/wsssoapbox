package wsdl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BindingDAOTestCase.class, WSDLDAOTestCase.class, ServiceDAOTestCase.class,
		InterfaceDAOTestCase.class, EndpointDAOTestCase.class })
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}
