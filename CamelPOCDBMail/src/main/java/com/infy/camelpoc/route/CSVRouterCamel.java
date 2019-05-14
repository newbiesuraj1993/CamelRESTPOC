package com.infy.camelpoc.route;

import javax.sql.DataSource;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infy.camelpoc.alert.MailProcessor;
import com.infy.camelpoc.domain.Item;

@Component
public class CSVRouterCamel extends RouteBuilder {
	
	@Qualifier("dataSource")
	@Autowired
	DataSource dataSource;
	
	@Autowired
	BuildSQLProcessor sqlProcessor;
	
	@Autowired
	MailProcessor mailer;
	
	@Override
	public void configure() throws Exception {
		
		Thread.sleep(3000);
		
		//Item class given to bindy 
		DataFormat bindy=new BindyCsvDataFormat(Item.class);
		
		//errorHandler(deadLetterChannel("file:data/logs").maximumRedeliveries(3).redeliveryDelay(3000));
		
		onException(DataException.class).log(LoggingLevel.ERROR,"Exception in the route ${body}").process(mailer);
		
		onException(PSQLException.class).log(LoggingLevel.ERROR,"Exception in the route ${body}")
		.maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);
		
		from("file:data/input?delete=true&readLock=none").routeId("mainRouter")
		.to("file:data/output")
		.unmarshal(bindy)
		.split(body())
		.log("Message is ${body}")
		.process(sqlProcessor)
		.to("jdbc:dataSource")
		.end();
	}

}
