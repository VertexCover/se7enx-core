package com.vertexcover.se7enx.core.api;

import java.util.HashMap;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.core.graph.Graph;
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
		return ResponseBuilder.respond(transactionLogs, TransactionLogs.class, "transactionLogs",  prettify);
	}
	
	
	public static String getCyclelessTransactionLogs(final String transactionLogsJsonString, final boolean prettify) {
		TransactionLogs transactionLogs = (TransactionLogs) JsonObjectMapper.toObject(transactionLogsJsonString, TransactionLogs.class);
		Event event = EventConvertor.toEvents(transactionLogs);
		TransactionLogs cyclelessTransactionLogs = TransactionCalculator.getTransactionLogs(event, EventConstants.DEFAULT_MODE);
		return ResponseBuilder.respond(cyclelessTransactionLogs, TransactionLogs.class, "transactionLogs",  prettify);
	} 
	
	
	public static String getCyclelessTransactionLogs(final HashMap<Long, HashMap<Long, Double>> transactionLogsMap,  final boolean prettify) {
		Event event = EventConvertor.toEvents(transactionLogsMap);
		TransactionLogs cyclelessTransactionLogs = TransactionCalculator.getTransactionLogs(event, EventConstants.DEFAULT_MODE);
		return ResponseBuilder.respond(cyclelessTransactionLogs, TransactionLogs.class, "transactionLogs",  prettify);
	}
	
	
	public static String doesCycleExist(final HashMap<Long, HashMap<Long, Double>> adjacencyMap,  final boolean prettify) {
		return ResponseBuilder.respond(Graph.doesCycleExist(adjacencyMap), Boolean.class ,"doesCycleExists", prettify);
	}
	
	public static String mergeTransactionLogs(final HashMap<Long, HashMap<Long, Double>> baseTransactionLogsMap, 
			final HashMap<Long, HashMap<Long, Double>> pathTransactionLogsMap) {
		return "";
	}
}
