package com.heptagon.userdetailstask.exception;

public class ExcelReaderException extends RuntimeException {

	private static final long	serialVersionUID	= 7032436818875287097L;

	/**
	 * @param message
	 */
	public ExcelReaderException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public ExcelReaderException(String message, Throwable throwable) {
		super(message, throwable);
	}

}

