package org.vrnda.hrms.utils.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String exceptionMessage) {

		super(exceptionMessage);

	}
}