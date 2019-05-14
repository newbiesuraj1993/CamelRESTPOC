package com.infy.camelpoc.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestProcessorClass implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		//System.out.println(exchange.getIn().getBody());


		ObjectMapper mapper = new ObjectMapper();
		List<String> listObjects=new ArrayList<String>();
		for(Exchange e:(List<Exchange>)exchange.getIn().getBody()) {
			StringBuilder record=new StringBuilder(((String) e.getIn().getBody(String.class)));
			JsonNode actualObj = mapper.readTree(record.toString());
			listObjects.add(actualObj.toString());
		}

		exchange.getIn().setBody(listObjects.toString());
	}

}
