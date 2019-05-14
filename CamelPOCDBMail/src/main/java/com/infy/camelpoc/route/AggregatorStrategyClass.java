package com.infy.camelpoc.route;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class AggregatorStrategyClass implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		List<String> exchangeList=new ArrayList<String>();
		if(oldExchange==null) {
			return newExchange;
		}else {
			String oldExchangeBody=(String) oldExchange.getIn().getBody();
			String newExchangeBody=(String) newExchange.getIn().getBody();
			exchangeList.add(oldExchangeBody);
			exchangeList.add(newExchangeBody);
			newExchange.getIn().setBody(exchangeList);
		}
		return newExchange;
	}

}
