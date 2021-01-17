package com.sl.ms.ordermanagement.exception;

public class ItemNotfound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemNotfound() {
		super("Item not available in Inventory");
	}

}
