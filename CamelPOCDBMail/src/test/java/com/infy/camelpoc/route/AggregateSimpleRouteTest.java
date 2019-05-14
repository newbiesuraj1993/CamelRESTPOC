package com.infy.camelpoc.route;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class AggregateSimpleRouteTest extends CamelTestSupport{
	
	@Override
	public RoutesBuilder createRouteBuilder() {
		return (RoutesBuilder) new AggregatorSimpleRoute();
	}

	@Test
	public void aggregateSimpleTest() throws InterruptedException {
		
		MockEndpoint mock=getMockEndpoint("mock:output");
		
		mock.expectedBodiesReceived("123");
		
		
		template.sendBodyAndHeader("direct:simpleAggregator", "1", "aggregatorId", 1);
		template.sendBodyAndHeader("direct:simpleAggregator", "2", "aggregatorId", 1);
		template.sendBodyAndHeader("direct:simpleAggregator", "3", "aggregatorId", 1);
		
		assertMockEndpointsSatisfied();
	}
}
