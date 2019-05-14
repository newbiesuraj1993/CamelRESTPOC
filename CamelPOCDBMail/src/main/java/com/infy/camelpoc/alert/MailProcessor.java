package com.infy.camelpoc.alert;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailProcessor implements Processor{

	@Autowired
	JavaMailSender emailSender;

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		Exception e=exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("nsgarafaite@gmail.com");
		message.setTo("nairsurajgangadharan@gmail.com");
		message.setSubject("Exception Mails");
		message.setText(e.getMessage());
		emailSender.send(message);
		
		
	}
	
	
}
