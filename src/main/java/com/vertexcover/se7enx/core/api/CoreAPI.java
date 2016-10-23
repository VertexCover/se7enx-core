package com.vertexcover.se7enx.core.api;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.core.helpers.EventConvertor;
import com.vertexcover.se7enx.core.helpers.JsonObjectMapper;
import com.vertexcover.se7enx.core.helpers.TransactionCalculator;
import com.vertexcover.se7enx.core.response.ResponseBuilder;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class CoreAPI {
	
	public static String getTransactionLog(final String eventInfoJsonString, final String eventMode, final boolean prettify) {
		Event event = EventConvertor.toEvents(eventInfoJsonString, eventMode);
		TransactionLogs transactionLogs = TransactionCalculator.getTransactionLogs(event, eventMode);
		return ResponseBuilder.respond(transactionLogs, prettify);
	}
	
	public static String getCyclelessTransactionLogs(final String transactionLogsJsonString, final boolean prettify) {
		TransactionLogs transactionLogs = (TransactionLogs) JsonObjectMapper.toObject(transactionLogsJsonString, TransactionLogs.class);
		Event events = EventConvertor.toEvents(transactionLogs);
		TransactionLogs cyclelessTransactionLogs = TransactionCalculator.getTransactionLogs(events, EventConstants.DEFAULT_MODE);
		return ResponseBuilder.respond(cyclelessTransactionLogs, prettify);
	} 
}
