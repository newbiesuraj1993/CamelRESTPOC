package com.infy.camelpoc.rest;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class RESTAggregator extends RouteBuilder{
	
	@Autowired
	RestProcessorClass processor;
	
	/*
	 * private String type;
	 * 
	 * public RESTAggregator(String type){ this.type = type; }
	 */

	
	

	@Override
	public void configure() throws Exception {
		from("direct:serviceFacade")
		.multicast(new GroupedExchangeAggregationStrategy()).parallelProcessing()
		.enrich("http://localhost:8080/getRecords")
		.enrich("http://localhost:8080/getNewRecords")
		.end();
		
		//JacksonDataFormat format = new ListJacksonDataFormat(Record.class);
		
		from("timer:restRouterAggregator?period=10s")
		.enrich("direct:serviceFacade")
		.setBody(property(Exchange.GROUPED_EXCHANGE))
		.process(processor)
		.log("Aggregated Message is ${body}");
		
		
		
		
	}
}
