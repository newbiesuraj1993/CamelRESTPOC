package com.infy.camelpoc.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.infy.camelpoc.domain.Item;


@Component
public class BuildSQLProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Item item=	(Item) exchange.getIn().getBody();
		System.out.println(item);
		
		if(ObjectUtils.isEmpty(item.getSkuNumber())) {
			throw new DataException("Sku Number is null for this "+item.getDescription());
		}
		
		StringBuilder query=new StringBuilder();
		if(item.getTransactionType().equals("ADD")) {
			query.append("INSERT INTO items(sku,description,price) VALUES ('");
			query.append(item.getSkuNumber()+"','"+item.getDescription()+"',"+item.getPrice()+")");
		}
		
		exchange.getIn().setBody(query.toString());
	}

}
