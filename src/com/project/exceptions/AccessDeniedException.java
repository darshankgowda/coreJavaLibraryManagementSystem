package com.project.exceptions;

public class AccessDeniedException extends Exception {
	
	public AccessDeniedException(String errMsg) {
		super(errMsg);
	}
}
