package com.infy.camelpoc.route;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CamelRouterTest {

	@Autowired
	ProducerTemplate template;
	
	@Test
	public void testMoveFile() throws InterruptedException {
		
		String message="type,sku,description,price\n" + 
				"ADD,100,Samsung TV,500\n" + 
				"ADD,101,IPhone,200\n";
		template.sendBodyAndHeader("file:data/input?delete=true&readLock=none",message
				,Exchange.FILE_NAME,"fileTest.txt");
		
		
		Thread.sleep(3000);
		
		File outFile=new File("data/output/fileTest.txt");
		assertTrue(outFile.exists());
		
	}
	
}
