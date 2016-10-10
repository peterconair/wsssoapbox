package org.wsssoapbox.soap;

import java.util.ArrayList;
import java.util.List;

public class SoapConstants {
	public final static String PARA_DEFAULT_VALUE = "?";
	public final static String PREFIX_DEFAULT_VALUE = "tns";
	public final static List<String> WELL_FORM_SCHEMAS = new ArrayList<String>();

	static {
		WELL_FORM_SCHEMAS.add("http://schemas.xmlsoap.org/soap/envelope/");
		WELL_FORM_SCHEMAS.add("http://schemas.xmlsoap.org/soap/encoding/");
		WELL_FORM_SCHEMAS.add("http://www.w3.org/2001/XMLSchema-instance");
		WELL_FORM_SCHEMAS.add("http://www.w3.org/2001/XMLSchema");
	}

}
