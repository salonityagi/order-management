package com.sl.ms.ordermanagement.exception;

public class ServiceNotAvailable extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ServiceNotAvailable(String error) {
		super(error);
	}
}
