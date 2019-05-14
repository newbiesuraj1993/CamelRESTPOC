package com.infy.camelpoc.rest;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.route.InsertProcessor;

@Component
public class RESTRoute extends RouteBuilder {

	@Qualifier("dataSource")
	@Autowired
	DataSource dataSource;
	
	@Autowired
	InsertProcessor insertProcessor;
	
	@Override
	public void configure() throws Exception {
		from("timer:restRouter?period=10s")
		.setHeader(Exchange.HTTP_METHOD,constant("GET"))
		.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/getRecords"))
		.to("http://localhost:8080/getRecords").convertBodyTo(String.class)
		.log("Rest Message is ${body}")
		.process(insertProcessor)
		.to("jdbc:dataSource");
		
		
		
		
	}

}
