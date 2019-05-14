package com.infy.camelpoc.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infy.camelpoc.restpackage.Record;




@Component
public class RestPostProcessorClass implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Record r=(Record) exchange.getIn().getBody(Record.class);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(r);
		//System.out.println(json);
		exchange.getIn().setBody(json.toString());
		
	}
}
