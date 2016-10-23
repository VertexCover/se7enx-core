package com.vertexcover.se7enx;

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
    	
    	String transactionLogsJsonString = "{\"eventMode\":\"equalSharingMode\",\"transactionLogs\":[{\"fromUserId\":1,\"toUserId\":2,\"amountToBePaid\":10.0},{\"fromUserId\":2,\"toUserId\":3,\"amountToBePaid\":10.0},{\"fromUserId\":3,\"toUserId\":1,\"amountToBePaid\":10.0}]}";
    	String response = CoreAPI.getCyclelessTransactionLogs(transactionLogsJsonString, false);
    	System.out.println(response);
    	
    	Response responseObj = (Response)JsonObjectMapper.toObject(response, Response.class);
    	System.out.println(responseObj.getResponse());
    }
}
