package com.sl.ms.ordermanagement.exception;

public class ItemNotFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemNotFound() {
		super("Item not available in Inventory");
	}

}
