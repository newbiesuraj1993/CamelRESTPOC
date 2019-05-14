package com.infy.camelpoc.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:timerperiod?period=10s")
		.log("Timer Invoked with body ${body}")
		.pollEnrich("file:data/input?delete=true&readLock=none")
		.to("file:data/output");
	}

}
