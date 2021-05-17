package com.heptagon.userdetailstask.exception;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
public class EmailNotUniqueException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailNotUniqueException(String email) {
        super(String.format("Email Already Registered with: "+email));
	}
}
