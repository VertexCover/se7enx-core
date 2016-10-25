package com.vertexcover.se7enx.core.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.core.constants.TransactionLogConstants;
import com.vertexcover.se7enx.pojo.units.TransactionLogUnit;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class TransactionLogConverter {
	
	public static TransactionLogs toTransactionLogs(HashMap<Long, HashMap<Long, Double>> transactionLogsMap) {
		List<TransactionLogUnit> transactionLogList = new ArrayList<TransactionLogUnit>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : transactionLogsMap.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
		    if(recievers == null)
		    	continue;
		    
		    for (Map.Entry<Long, Double> entryInner : recievers.entrySet()) {
		    	long toUserId = entryInner.getKey();
		    	double pendingAmount = entryInner.getValue();
		    	TransactionLogUnit transactionLogUnit = new TransactionLogUnit(fromUserId, toUserId, pendingAmount);
		    	transactionLogList.add(transactionLogUnit);
		    }
		}
		
		TransactionLogs transactionLogs = new TransactionLogs();
		transactionLogs.setEventMode(EventConstants.DEFAULT_MODE);
		transactionLogs.setTransactionLogs(transactionLogList);
		return transactionLogs;
	}
	
	
	private static TransactionLogs decompressTransactionLogs(HashMap<String, String> compressedTransactionLogs) {
		List<TransactionLogUnit> transactionLogList = new ArrayList<TransactionLogUnit>();
		for (Map.Entry<String, String> entry : compressedTransactionLogs.entrySet()) {
		    String value = entry.getValue();
		    String MSB = value.substring(0, 1);
	    	double pendingAmount = Double.parseDouble(value.substring(1));
	    	
		    String[] ids = entry.getKey().split(TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_SEPARATOR);
		    long fromUserId = 0;
		    long toUserId = 0;
		    
		    if(MSB.equals(TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_LE_MSB)) {
		    	fromUserId = Long.parseLong(ids[0]);
		    	toUserId = Long.parseLong(ids[1]);
		    } else {
		    	fromUserId = Long.parseLong(ids[1]);
		    	toUserId = Long.parseLong(ids[0]);
		    }

	    	TransactionLogUnit transactionLogUnit = new TransactionLogUnit(fromUserId, toUserId, pendingAmount);
	    	transactionLogList.add(transactionLogUnit);
		}
		
		TransactionLogs transactionLogs = new TransactionLogs();
		transactionLogs.setEventMode(EventConstants.DEFAULT_MODE);
		transactionLogs.setTransactionLogs(transactionLogList);
		return transactionLogs;
	}
	
	
	
	
	private static HashMap<String, String> compressTransactionLogsMap(HashMap<Long, HashMap<Long, Double>> transactionLogsMap) {
		HashMap<String, String> compressedTransactionLogsMap = new HashMap<String, String>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : transactionLogsMap.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
		    if(recievers == null)
		    	continue;
		    
		    for (Map.Entry<Long, Double> entryInner : recievers.entrySet()) {
		    	long toUserId = entryInner.getKey();
		    	double pendingAmount = entryInner.getValue();
		    	String id = getTransactionLogId(fromUserId, toUserId);
		    	
		    	if(fromUserId <= toUserId)
		    		compressedTransactionLogsMap.put(id, TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_LE_MSB
		    				+ pendingAmount);
		    	else
		    		compressedTransactionLogsMap.put(id, TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_GT_MSB
		    				+ pendingAmount);
		    }
		}
		
		return compressedTransactionLogsMap;
	}
	
	
	
	private static HashMap<String, String> compressTransactionLogsMap(TransactionLogs transactionLogs) {
		HashMap<String, String> compressedTransactionLogsMap = new HashMap<String, String>();
		for(TransactionLogUnit transactionLogUnit : transactionLogs.getTransactionLogs()) {
			String id = getTransactionLogId(transactionLogUnit.getFromUserId(), transactionLogUnit.getToUserId());
	    	if(transactionLogUnit.getFromUserId() <= transactionLogUnit.getToUserId())
	    		compressedTransactionLogsMap.put(id, TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_LE_MSB
	    				+ transactionLogUnit.getAmountToBePaid());
	    	else
	    		compressedTransactionLogsMap.put(id, TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_GT_MSB
	    				+ transactionLogUnit.getAmountToBePaid());
		}
		
		return compressedTransactionLogsMap;
	}
	
	
	
	public static TransactionLogs mergeTransactionLogs(TransactionLogs baseTransactionLogs, TransactionLogs patchTransactionLogs) {
		HashMap<String, String> compressedBaseTransactionLogs = compressTransactionLogsMap(baseTransactionLogs);
		HashMap<String, String> compressedPatchTransactionLogs = compressTransactionLogsMap(patchTransactionLogs);
		HashMap<String, String> compressedTransactionLogs = compressedPatchTransactionLogs;
		for (Map.Entry<String, String> entry : compressedBaseTransactionLogs.entrySet()) {
			if(compressedTransactionLogs.containsKey(entry.getKey()))
				continue;
			
			compressedTransactionLogs.put(entry.getKey(), entry.getValue().substring(0, 1) + 0);
		}
		
		return decompressTransactionLogs(compressedTransactionLogs);
	}
	
	
	public static TransactionLogs mergeTransactionLogs(HashMap<Long, HashMap<Long, Double>> baseTransactionLogsMap, TransactionLogs patchTransactionLogs) {
		return mergeTransactionLogs(toTransactionLogs(baseTransactionLogsMap), patchTransactionLogs);
	}
	
	
	public static TransactionLogs mergeTransactionLogs(TransactionLogs baseTransactionLogs, HashMap<Long, HashMap<Long, Double>> patchTransactionLogsMap) {
		return mergeTransactionLogs(baseTransactionLogs, toTransactionLogs(patchTransactionLogsMap));
	}
	
	
	public static TransactionLogs mergeTransactionLogs(HashMap<Long, HashMap<Long, Double>> baseTransactionLogsMap, HashMap<Long, HashMap<Long, Double>> patchTransactionLogsMap) {
		return mergeTransactionLogs(toTransactionLogs(baseTransactionLogsMap), toTransactionLogs(patchTransactionLogsMap));
	}
	
	
	private static String getTransactionLogId(long id1, long id2) {
		if(id1 <= id2)
			return Long.toString(id1) + TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_SEPARATOR + Long.toString(id2);
		else
			return Long.toString(id2) + TransactionLogConstants.COMPRESSED_TRANSACTION_LOG_ID_SEPARATOR + Long.toString(id1);
	}
	
	

}
