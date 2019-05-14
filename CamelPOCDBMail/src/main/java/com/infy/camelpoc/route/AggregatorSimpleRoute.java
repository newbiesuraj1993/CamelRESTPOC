package com.infy.camelpoc.route;

import org.apache.camel.builder.RouteBuilder;

public class AggregatorSimpleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:simpleAggregator")
		.log("Received Message ${body} and key ${header.aggregatorId}")
		.aggregate(header("aggregatorId"),new AggregatorStrategyClass())
		.completionSize(3)
		.log("Aggregated Message is ${body}")
		.to("mock:output");
	}

}
