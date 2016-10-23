package com.vertexcover.se7enx.core.helpers;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonObjectMapper {

	public static String toJsonString(Object object, boolean prettify) {
		try {
			if(prettify)
				return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
			else
				return new ObjectMapper().writeValueAsString(object);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static Object toObject(String jsonString, Class cls) {
		try {
			return new ObjectMapper().readValue(jsonString, cls);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
