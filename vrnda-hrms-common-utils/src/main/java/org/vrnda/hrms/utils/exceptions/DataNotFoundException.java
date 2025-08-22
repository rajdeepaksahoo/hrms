package org.vrnda.hrms.utils.exceptions;

/**
 * @author siddhaarth
 *
 */
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String exceptionMessage) {

		super(exceptionMessage);
	}
}
