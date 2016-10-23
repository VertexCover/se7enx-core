package com.vertexcover.se7enx.core.response;

import com.vertexcover.se7enx.core.helpers.JsonObjectMapper;
import com.vertexcover.se7enx.pojo.wrappers.TransactionLogs;

public class ResponseBuilder {

	public static String respond(final TransactionLogs transactionLogs, final boolean prettify) {
		Response response = new Response();
		response.setResponseType("transactionLogs");
		response.setResponsePrettify(prettify);
		if(transactionLogs == null)
			response.setStatus(false);
		else
			response.setStatus(true);
		response.setResponse(JsonObjectMapper.toJsonString(transactionLogs, prettify));
		return JsonObjectMapper.toJsonString(response, prettify);
	}
}
