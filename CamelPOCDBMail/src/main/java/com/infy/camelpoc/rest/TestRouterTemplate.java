package com.infy.camelpoc.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.restpackage.Record;

@Component
public class TestRouterTemplate {
	@Autowired
	Record record;
	
	@Autowired
	RestPostProcessorClass postProcessor;
	
	JacksonDataFormat format = new ListJacksonDataFormat(Record.class);
	
	JacksonDataFormat listFormat = new JacksonDataFormat(Record.class);
	public void callmethod() throws Exception {
		CamelContext context=new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				// TODO Auto-generated method stub
				/*
				 * from("direct:start") .to("seda:end");
				 */ 
				from("direct:start").routeId("postcaller") 
		         .process(
		                 exchange -> exchange.getIn().setBody(
		                         new Record("34")))
		         .marshal(format) //convert it to JSON
		         .setHeader(Exchange.HTTP_METHOD, constant("POST"))
		         .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		         .to("http://localhost:8080/postRecords")
		         .unmarshal(listFormat)
		         .process(postProcessor)
		         .log("${body} from invoke POST");
			//	 .to("seda:end");
				
			}
			
		});
		
		context.start();
		Endpoint endpoint = context.getEndpoint("direct:start");
		
		ProducerTemplate template=context.createProducerTemplate();
		template.setDefaultEndpoint(endpoint);
		template.sendBody("direct:start");
		
		/*
		 * ConsumerTemplate conTemplate=context.createConsumerTemplate(); String
		 * message=conTemplate.receiveBody("seda:end",String.class);
		 * 
		 * System.out.println(message);
		 */
	
		context.stop();
	}
}
