package com.vertexcover.se7enx.core.helpers;

import java.util.HashMap;
import java.util.Map;

import com.vertexcover.se7enx.core.constants.TransactionLogConstants;
import com.vertexcover.se7enx.pojo.units.TransactionLogUnit;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class TransactionLogConverter {
	
	public static HashMap<String, Double> toTransactionLogsCompressedMap(HashMap<Long, HashMap<Long, Double>> transactionLogsMap) {
		HashMap<String, Double> transactionLogsCompressedMap = new HashMap<String, Double>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : transactionLogsMap.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
		    if(recievers == null)
		    	continue;
		    
		    for (Map.Entry<Long, Double> entryInner : recievers.entrySet()) {
		    	long toUserId = entryInner.getKey();
		    	double pendingAmount = entryInner.getValue();
		    	String id = getTransactionLogId(fromUserId, toUserId);
		    	transactionLogsCompressedMap.put(id, pendingAmount);
		    }
		}
		
		return transactionLogsCompressedMap;
	}
	
	
	public static HashMap<String, Double> toTransactionLogsCompressedMap(TransactionLogs transactionLogs) {
		HashMap<String, Double> transactionLogsCompressedMap = new HashMap<String, Double>();
		for(TransactionLogUnit transactionLogUnit : transactionLogs.getTransactionLogs()) {
			String id = getTransactionLogId(transactionLogUnit.getFromUserId(), transactionLogUnit.getToUserId());
			transactionLogsCompressedMap.put(id, transactionLogUnit.getAmountToBePaid());
		}
		
		return transactionLogsCompressedMap;
	}
	
	
	private static String getTransactionLogId(long id1, long id2) {
		if(id1 <= id2)
			return Long.toString(id1) + TransactionLogConstants.TRANSACTION_LOG_ID_SEPARATOR + Long.toString(id2);
		else
			return Long.toString(id2) + TransactionLogConstants.TRANSACTION_LOG_ID_SEPARATOR + Long.toString(id1);
	}
	
	

}
