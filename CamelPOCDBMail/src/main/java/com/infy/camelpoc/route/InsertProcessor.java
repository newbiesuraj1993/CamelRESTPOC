package com.infy.camelpoc.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class InsertProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String input=(String) exchange.getIn().getBody();
		
		JSONParser parser=new JSONParser();
		
		Object obj=parser.parse(input);
		
		JSONObject object=(JSONObject) obj;
		
		String insertQuery="INSERT INTO records values(".concat((String) object.get("recordId")).concat(")");
		
		exchange.getIn().setBody(insertQuery);
	
	
	}

}
