package com.infy.camelpoc.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.route.AggregatorStrategyClass;

@Component
public class RestAggregatorV2{
	public void callmethod() throws Exception {
		CamelContext context=new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:simpleRouter1")
				.setHeader(Exchange.HTTP_METHOD,constant("GET"))
				.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/getNewRecords"))
				.to("http://localhost:8080/getNewRecords").convertBodyTo(String.class)
				//.log("Rest Message is router 1 ${body}")
				.to("seda:end1");

			}
		});


		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:simpleRouter2")
				.setHeader(Exchange.HTTP_METHOD,constant("GET"))
				.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/getRecords"))
				.to("http://localhost:8080/getRecords").convertBodyTo(String.class)
				//.log("Rest Message is router 2 ${body}")
				.to("seda:end2");

			}
		});
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:simpleAggregator")
				//.log("Received Message ${body} and key ${header.aggregatorId}")
				.aggregate(header("aggregatorId"),new AggregatorStrategyClass())
				.log("Aggregated Message is ${body}")
				.completionSize(2)
				.to("seda:end3");
				
			
			}
		});





		context.start();

		Endpoint endpoint = context.getEndpoint("direct:simpleRouter1");	
		ProducerTemplate template=context.createProducerTemplate();
		template.setDefaultEndpoint(endpoint);

		Endpoint endpointnew = context.getEndpoint("direct:simpleRouter2");
		ProducerTemplate templatenew=context.createProducerTemplate();
		templatenew.setDefaultEndpoint(endpointnew);

		template.sendBody("direct:simpleRouter1");
		templatenew.sendBody("direct:simpleRouter2");



		ConsumerTemplate conTemplate=context.createConsumerTemplate(); String
		message=conTemplate.receiveBody("seda:end1",String.class);

		System.out.println(message);

		ConsumerTemplate conTemplatenew=context.createConsumerTemplate(); String
		messagenew=conTemplatenew.receiveBody("seda:end2",String.class);

		System.out.println(messagenew);

		Endpoint endpointagg = context.getEndpoint("direct:simpleAggregator");
		ProducerTemplate templateagg=context.createProducerTemplate();
		templateagg.setDefaultEndpoint(endpointagg);
	

		templateagg.sendBodyAndHeader("direct:simpleAggregator", message, "aggregatorId", 1);
		templateagg.sendBodyAndHeader("direct:simpleAggregator", messagenew, "aggregatorId", 1);
		
		context.stop();




	}
}