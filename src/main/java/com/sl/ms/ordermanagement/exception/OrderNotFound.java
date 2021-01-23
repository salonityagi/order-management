package com.sl.ms.ordermanagement.exception;

public class OrderNotFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderNotFound() {
		super("Order not available.");
	}

}
