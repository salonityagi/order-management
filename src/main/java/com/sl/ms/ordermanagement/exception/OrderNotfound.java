package com.sl.ms.ordermanagement.exception;

public class OrderNotfound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderNotfound() {
		super("Item not available in Inventory");
	}

}
