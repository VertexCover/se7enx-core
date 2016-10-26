package com.vertexcover.se7enx.core.api;

import java.util.HashMap;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.core.constants.TransactionLogConstants;
import com.vertexcover.se7enx.core.graph.Graph;
import com.vertexcover.se7enx.core.helpers.EventConvertor;
import com.vertexcover.se7enx.core.helpers.JsonObjectMapper;
import com.vertexcover.se7enx.core.helpers.TransactionCalculator;
import com.vertexcover.se7enx.core.helpers.TransactionLogConverter;
import com.vertexcover.se7enx.core.response.ResponseBuilder;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class CoreAPI {
	
	public static String getOptimalTransactionLog(final String eventInfoJsonString, final String eventMode, final boolean prettify) {
		Event event = EventConvertor.toEvents(eventInfoJsonString, eventMode);
		TransactionLogs optimalTransactionLogs = TransactionCalculator.getTransactionLogs(event, eventMode);
		return ResponseBuilder.respond(optimalTransactionLogs, TransactionLogs.class, TransactionLogConstants.OPTIMAL_TRANSACTION_LOGS_RESPONSE_TYPE,  prettify);
	}
	
	
	public static String reoptimizeTransactionLogs(final String transactionLogsJsonString, final boolean prettify) {
		TransactionLogs transactionLogs = (TransactionLogs) JsonObjectMapper.toObject(transactionLogsJsonString, TransactionLogs.class);
		Event event = EventConvertor.toEvents(transactionLogs);
		TransactionLogs optimalTransactionLogs = TransactionCalculator.getTransactionLogs(event, EventConstants.DEFAULT_MODE);
		TransactionLogs mergedOptimalTransactionLogs = TransactionLogConverter.mergeTransactionLogs(transactionLogs, optimalTransactionLogs);
		return ResponseBuilder.respond(mergedOptimalTransactionLogs, TransactionLogs.class, TransactionLogConstants.OPTIMAL_TRANSACTION_LOGS_RESPONSE_TYPE,  prettify);
	} 
	
	
	public static String reoptimizeTransactionLogs(final HashMap<Long, HashMap<Long, Double>> transactionLogsMap,  final boolean prettify) {
		Event event = EventConvertor.toEvents(transactionLogsMap);
		TransactionLogs optimalTransactionLogs = TransactionCalculator.getTransactionLogs(event, EventConstants.DEFAULT_MODE);
		TransactionLogs mergedOptimalTransactionLogs = TransactionLogConverter.mergeTransactionLogs(transactionLogsMap, optimalTransactionLogs);
		return ResponseBuilder.respond(mergedOptimalTransactionLogs, TransactionLogs.class, TransactionLogConstants.OPTIMAL_TRANSACTION_LOGS_RESPONSE_TYPE,  prettify);
	}
	
	
	public static String doesCycleExist(final HashMap<Long, HashMap<Long, Double>> adjacencyMap,  final boolean prettify) {
		return ResponseBuilder.respond(Graph.doesCycleExist(adjacencyMap), Boolean.class ,"doesCycleExists", prettify);
	}
	
}
