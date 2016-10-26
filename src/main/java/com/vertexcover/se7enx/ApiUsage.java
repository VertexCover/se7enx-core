package com.vertexcover.se7enx;

import java.util.HashMap;

import com.vertexcover.se7enx.core.api.CoreAPI;
import com.vertexcover.se7enx.core.helpers.JsonObjectMapper;
import com.vertexcover.se7enx.core.response.Response;

/**
 * Hello world!
 *
 */
public class ApiUsage 
{
    public static void main( String[] args ) {
    	
    	/*
    	String transactionLogsJsonString = "{\"eventMode\":\"equalSharingMode\",\"transactionLogs\":[{\"fromUserId\":1,\"toUserId\":2,\"amountToBePaid\":10.0},{\"fromUserId\":2,\"toUserId\":3,\"amountToBePaid\":10.0},{\"fromUserId\":3,\"toUserId\":1,\"amountToBePaid\":10.0}]}";
    	String response = CoreAPI.getCyclelessTransactionLogs(transactionLogsJsonString, false);
    	System.out.println(response);
    	
    	Response responseObj = (Response)JsonObjectMapper.toObject(response, Response.class);
    	System.out.println(responseObj.getResponse());
    	*/
    	

    	
    	HashMap<Long, HashMap<Long, Double>> adjacencyMap = new HashMap<Long, HashMap<Long,Double>>();
    	HashMap<Long, Double> oneFriends = new HashMap<Long, Double>();
    	oneFriends.put((long) 2, 10.0);
    	adjacencyMap.put((long) 1, oneFriends);
    	
    	HashMap<Long, Double> twoFriends = new HashMap<Long, Double>();
    	twoFriends.put((long) 3, 10.0);
    	adjacencyMap.put((long) 2, twoFriends);
    	
    	HashMap<Long, Double> threeFriends = new HashMap<Long, Double>();
    	threeFriends.put((long) 1, 10.0);
    	adjacencyMap.put((long) 3, threeFriends);
    	
    	String response = CoreAPI.doesCycleExist(adjacencyMap, true);
    	System.out.println(response);
    	
    	String responseTransactions = CoreAPI.reoptimizeTransactionLogs(adjacencyMap, false);
    	Response responseObj = (Response)JsonObjectMapper.toObject(responseTransactions, Response.class);
    	System.out.println(responseObj.getResponse());
    	
    	
    	
    	
    }
}
