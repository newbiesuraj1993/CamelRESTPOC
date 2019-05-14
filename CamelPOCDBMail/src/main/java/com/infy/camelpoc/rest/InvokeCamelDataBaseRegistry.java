package com.infy.camelpoc.rest;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.route.InsertProcessor;

@Component
public class InvokeCamelDataBaseRegistry {
	@Qualifier("dataSource")
	@Autowired
	DataSource dataSource;
	
	@Autowired
	InsertProcessor insertProcessor;
	
	public void callmethod() throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("dataSource", dataSource);

		CamelContext context = new DefaultCamelContext(registry);
		
		
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start")
				.setHeader(Exchange.HTTP_METHOD,constant("GET"))
				.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/getRecords"))
				.to("http://localhost:8080/getRecords").convertBodyTo(String.class)
				.log("Rest Message INVOKED DS is ${body}")
				.process(insertProcessor)
				.to("jdbc:dataSource");
				
			}});
		
		context.start();
		Endpoint endpoint = context.getEndpoint("direct:start");
		
		ProducerTemplate template=context.createProducerTemplate();
		template.setDefaultEndpoint(endpoint);
		template.sendBody("direct:start");
		
		context.stop();
		
	}
}
