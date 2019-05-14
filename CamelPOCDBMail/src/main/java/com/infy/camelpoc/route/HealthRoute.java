package com.infy.camelpoc.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.alert.MailProcessor;

@Component
public class HealthRoute extends RouteBuilder{

	@Autowired
	HealthCheckProcessor healthProcessor;
	
	@Autowired
	MailProcessor mailprocessor;
	
	@Override
	public void configure() throws Exception {
		
		from("timer:routeHealth?period=10s").routeId("routeHealth")
		.pollEnrich("http://localhost:8080/health")
		.process(healthProcessor)
		.choice()
		.when(header("ERROR").isEqualTo("true"))
		.process(mailprocessor);
	}

}
