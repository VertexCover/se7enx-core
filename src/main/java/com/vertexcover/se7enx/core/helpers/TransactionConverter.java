package com.vertexcover.se7enx.core.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vertexcover.se7enx.core.constants.EventConstants;
import com.vertexcover.se7enx.core.constants.TransactionConstants;
import com.vertexcover.se7enx.pojo.units.Transaction;
import com.vertexcover.se7enx.pojo.wrappers.Transactions;

public class TransactionConverter {
	
	public static Transactions convert(HashMap<Long, HashMap<Long, Double>> transactions) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : transactions.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
		    if(recievers == null)
		    	continue;
		    
		    for (Map.Entry<Long, Double> entryInner : recievers.entrySet()) {
		    	long toUserId = entryInner.getKey();
		    	double pendingAmount = entryInner.getValue();
		    	Transaction transaction = new Transaction(fromUserId, toUserId, pendingAmount);
		    	transactionList.add(transaction);
		    }
		}
		
		Transactions transactionLogs = new Transactions();
		transactionLogs.setEventMode(EventConstants.DEFAULT_MODE);
		transactionLogs.setTransactions(transactionList);
		return transactionLogs;
	}
	
	
	private static Transactions decompress(HashMap<String, String> compressedTransactions) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		for (Map.Entry<String, String> entry : compressedTransactions.entrySet()) {
		    String value = entry.getValue();
		    String MSB = value.substring(0, 1);
	    	double pendingAmount = Double.parseDouble(value.substring(1));
		    String[] ids = entry.getKey().split(TransactionConstants.COMPRESSED_TRANSACTION_ID_SEPARATOR);
		    long fromUserId = 0;
		    long toUserId = 0;
		    
		    if(MSB.equals(TransactionConstants.COMPRESSED_TRANSACTION_ID_LE_MSB)) {
		    	fromUserId = Long.parseLong(ids[0]);
		    	toUserId = Long.parseLong(ids[1]);
		    } else {
		    	fromUserId = Long.parseLong(ids[1]);
		    	toUserId = Long.parseLong(ids[0]);
		    }

	    	Transaction transaction = new Transaction(fromUserId, toUserId, pendingAmount);
	    	transactionList.add(transaction);
		}
		
		Transactions transactions = new Transactions();
		transactions.setEventMode(EventConstants.DEFAULT_MODE);
		transactions.setTransactions(transactionList);
		return transactions;
	}
	
	
	
	
	private static HashMap<String, String> compress(HashMap<Long, HashMap<Long, Double>> transactions) {
		HashMap<String, String> compressedTransactions = new HashMap<String, String>();
		for (Map.Entry<Long, HashMap<Long, Double>> entryOuter : transactions.entrySet()) {
		    long fromUserId = entryOuter.getKey();
		    HashMap<Long, Double> recievers = entryOuter.getValue();
		    if(recievers == null)
		    	continue;
		    
		    for (Map.Entry<Long, Double> entryInner : recievers.entrySet()) {
		    	long toUserId = entryInner.getKey();
		    	double pendingAmount = entryInner.getValue();
		    	String id = getTransactionLogId(fromUserId, toUserId);
		    	
		    	if(fromUserId <= toUserId)
		    		compressedTransactions.put(id, TransactionConstants.COMPRESSED_TRANSACTION_ID_LE_MSB
		    				+ pendingAmount);
		    	else
		    		compressedTransactions.put(id, TransactionConstants.COMPRESSED_TRANSACTION_ID_GT_MSB
		    				+ pendingAmount);
		    }
		}
		
		return compressedTransactions;
	}
	
	
	
	private static HashMap<String, String> compress(Transactions transactions) {
		HashMap<String, String> compressedTransactions = new HashMap<String, String>();
		for(Transaction transaction : transactions.getTransactions()) {
			String id = getTransactionLogId(transaction.getFromUserId(), transaction.getToUserId());
	    	if(transaction.getFromUserId() <= transaction.getToUserId())
	    		compressedTransactions.put(id, TransactionConstants.COMPRESSED_TRANSACTION_ID_LE_MSB
	    				+ transaction.getAmountToBePaid());
	    	else
	    		compressedTransactions.put(id, TransactionConstants.COMPRESSED_TRANSACTION_ID_GT_MSB
	    				+ transaction.getAmountToBePaid());
		}
		
		return compressedTransactions;
	}
	
	
	
	public static Transactions merge(Transactions baseTransactions, Transactions patchTransactions) {
		HashMap<String, String> compressedBaseTransactions = compress(baseTransactions);
		HashMap<String, String> compressedPatchTransactions = compress(patchTransactions);
		HashMap<String, String> compressedTransactions = compressedPatchTransactions;
		for (Map.Entry<String, String> entry : compressedBaseTransactions.entrySet()) {
			if(compressedTransactions.containsKey(entry.getKey()))
				continue;
			
			compressedTransactions.put(entry.getKey(), entry.getValue().substring(0, 1) + 0);
		}
		
		return decompress(compressedTransactions);
	}
	
	
	public static Transactions merge(HashMap<Long, HashMap<Long, Double>> baseTransactions, Transactions patchTransactions) {
		return merge(convert(baseTransactions), patchTransactions);
	}
	
	
	public static Transactions merge(Transactions baseTransactions, HashMap<Long, HashMap<Long, Double>> patchTransactions) {
		return merge(baseTransactions, convert(patchTransactions));
	}
	
	
	public static Transactions merge(HashMap<Long, HashMap<Long, Double>> baseTransactions, HashMap<Long, HashMap<Long, Double>> patchTransactions) {
		return merge(convert(baseTransactions), convert(patchTransactions));
	}
	
	
	private static String getTransactionLogId(long id1, long id2) {
		if(id1 <= id2)
			return Long.toString(id1) + TransactionConstants.COMPRESSED_TRANSACTION_ID_SEPARATOR + Long.toString(id2);
		else
			return Long.toString(id2) + TransactionConstants.COMPRESSED_TRANSACTION_ID_SEPARATOR + Long.toString(id1);
	}
	
	

}
