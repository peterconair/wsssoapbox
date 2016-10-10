package com.javatech.payment.mapping;

/**
 * A simple POJO containing a custom response. 
 * Castor will map the outgoing Java class response to an equivalent xml. 
 *
 */
public class PaymentResponse {

	private String code;
	private String description;
        private String referenceCode;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * @return the referenceCode
     */
    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     * @param referenceCode the referenceCode to set
     */
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

}
