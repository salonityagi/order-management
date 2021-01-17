package com.sl.ms.ordermanagement.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class OrderMgmtLogger  extends StringMapMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER= LogManager.getLogger("sl.ms.order.logger");
	private static final String TRANS_ID="TRANS_ID";
	private static final String REQUEST="REQUEST";
	private static final String START_TIME="START_TIME";
	private static final String END_TIME="END_TIME";
	
	private void logMessage(String key, String value) {
		if (key!=null && value!=null) {
			put(key,value);
		}
	}
	
	public void addLogs(String message) {
		LOGGER.info(message);
	}
	
	public void addOrderLogs(String startTime, String endTime,Object request) {
		ObjectMapper mapper=new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		logMessage(START_TIME,startTime);
		logMessage(END_TIME,endTime);
		try {
			logMessage(REQUEST,mapper.writeValueAsString(request));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		LOGGER.info(getData());
	}
	
	public void errorLogs(Object errorObject,String statusCode) {
		ObjectMapper mapper=new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		logMessage("HttpStatus",statusCode);
		try {
			logMessage("ERROR_MESSAGE",mapper.writeValueAsString(errorObject));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		LOGGER.info(getData());
	}
}

