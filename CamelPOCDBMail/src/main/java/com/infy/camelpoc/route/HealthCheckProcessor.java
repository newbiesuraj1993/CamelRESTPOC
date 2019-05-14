package com.infy.camelpoc.route;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HealthCheckProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String healthCheck=(String) exchange.getIn().getBody(String.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		Map<String, Object> map=objectMapper.readValue(healthCheck, new TypeReference<Map<String,Object>>(){});
		
		StringBuilder exception=null;
		System.out.println(map.keySet());
		for(String key:map.keySet()) {
			if(map.get(key).toString().equals("DOWN")) {
				
				if(exception==null) {
					exception=new StringBuilder();
				}
			exception.append(key);
				
			}
		}
		if(exception!=null) {
		exchange.getIn().setHeader("ERROR", "true");
		exchange.getIn().setBody(exception.toString());
		exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception.toString());
	}
	}
}
