package com.infy.camelpoc.rest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.restpackage.Record;

@Component
public class RestPOSTCaller extends RouteBuilder{

	
	@Autowired
	Record record;
	
	@Autowired
	RestPostProcessorClass postProcessor;
	
	JacksonDataFormat format = new ListJacksonDataFormat(Record.class);
	
	JacksonDataFormat listFormat = new JacksonDataFormat(Record.class);
	
	@Override
	public void configure() throws Exception {
		 from("timer:restPOSTRouter?period=5s").routeId("postcaller") //called every 20 mins
         .process(
                 exchange -> exchange.getIn().setBody(
                         new Record("34")))
         .marshal(format) //convert it to JSON
         .setHeader(Exchange.HTTP_METHOD, constant("POST"))
         .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
         .to("http://localhost:8080/postRecords")
         .unmarshal(listFormat)
         .process(postProcessor)
         .log("${body} from POST");
		
	}

}
