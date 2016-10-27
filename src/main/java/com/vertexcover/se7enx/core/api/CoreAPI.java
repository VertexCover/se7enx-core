package com.vertexcover.se7enx.core.api;

import java.util.HashMap;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.core.constants.TransactionConstants;
import com.vertexcover.se7enx.core.graph.Graph;
import com.vertexcover.se7enx.core.helpers.EventConvertor;
import com.vertexcover.se7enx.core.helpers.JsonObjectMapper;
import com.vertexcover.se7enx.core.helpers.TransactionCalculator;
import com.vertexcover.se7enx.core.helpers.TransactionConverter;
import com.vertexcover.se7enx.core.response.ResponseBuilder;
import com.vertexcover.se7enx.pojo.wrappers.Event;
import com.vertexcover.se7enx.pojo.wrappers.Transactions;

public class CoreAPI {
	
	public static String getOptimalTransactions(final String eventInfoJson, final String eventMode, final boolean prettify) {
		Event event = EventConvertor.convert(eventInfoJson, eventMode);
		Transactions optimalTransactions = TransactionCalculator.getTransactions(event, eventMode);
		return ResponseBuilder.respond(optimalTransactions, Transactions.class, TransactionConstants.OPTIMAL_TRANSACTION_RESPONSE_TYPE,  prettify);
	}
	
	
	public static String reoptimizeTransactions(final String transactionsJson, final boolean prettify) {
		Transactions transactions = (Transactions) JsonObjectMapper.toObject(transactionsJson, Transactions.class);
		Event event = EventConvertor.convert(transactions);
		Transactions optimalTransactions = TransactionCalculator.getTransactions(event, EventConstants.DEFAULT_MODE);
		Transactions mergedOptimalTransactions = TransactionConverter.merge(transactions, optimalTransactions);
		return ResponseBuilder.respond(mergedOptimalTransactions, Transactions.class, TransactionConstants.OPTIMAL_TRANSACTION_RESPONSE_TYPE,  prettify);
	} 
	
	
	public static String reoptimizeTransactions(final HashMap<Long, HashMap<Long, Double>> transactions,  final boolean prettify) {
		Event event = EventConvertor.convert(transactions);
		Transactions optimalTransactions = TransactionCalculator.getTransactions(event, EventConstants.DEFAULT_MODE);
		Transactions mergedOptimalTransactions = TransactionConverter.merge(transactions, optimalTransactions);
		return ResponseBuilder.respond(mergedOptimalTransactions, Transactions.class, TransactionConstants.OPTIMAL_TRANSACTION_RESPONSE_TYPE,  prettify);
	}
	
	
	public static String doesCycleExist(final HashMap<Long, HashMap<Long, Double>> adjacencyMap,  final boolean prettify) {
		return ResponseBuilder.respond(Graph.doesCycleExist(adjacencyMap), Boolean.class ,"doesCycleExists", prettify);
	}
	
}
