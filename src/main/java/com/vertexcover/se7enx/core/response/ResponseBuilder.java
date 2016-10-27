package com.vertexcover.se7enx.core.response;

import com.vertexcover.se7enx.core.helpers.JsonObjectMapper;
import com.vertexcover.se7enx.pojo.wrappers.Transactions;

public class ResponseBuilder {

	public static String respond(final Object responseObject, final Class cls, final String responseType, final boolean prettify) {
		Response response = new Response();
		response.setResponseType(responseType);
		response.setResponsePrettify(prettify);
		if(responseObject == null)
			response.setStatus(false);
		else
			response.setStatus(true);
		response.setResponse(JsonObjectMapper.toJsonString(responseObject, prettify));
		return JsonObjectMapper.toJsonString(response, prettify);
	}
	
	
	
}
