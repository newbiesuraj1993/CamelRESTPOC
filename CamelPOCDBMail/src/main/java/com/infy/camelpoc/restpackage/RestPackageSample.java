package com.infy.camelpoc.restpackage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infy.camelpoc.rest.InvokeCamelDataBaseRegistry;
import com.infy.camelpoc.rest.RestAggregatorV2;
import com.infy.camelpoc.rest.TestRouterTemplate;




@RestController
public class RestPackageSample {

	@Autowired
	TestRouterTemplate template;

	@Autowired
	InvokeCamelDataBaseRegistry registry;
	
	@Autowired
	RestAggregatorV2 aggregator;
	
	
	@GetMapping("/getRecords")
	public Record getRecords() {
		
		Record r=new Record("2");
		
		return r;
		
	}
	@GetMapping("/getNewRecords")
	public Record getNewRecords() {
		
		Record r=new Record("12");
		return r;
		
	}
	
	@PostMapping("/postRecords")
	public Record postRecords(@RequestBody Record record) {
		return record;
	}
	
	@GetMapping("/invoke")
	public void invoke() throws Exception {
		template.callmethod();
	}
	
	@GetMapping("/invokeDS")
	public void invokeds() throws Exception {
		registry.callmethod();
	}
	
	@GetMapping("/invokeMessage")
	public void invokemessage() throws Exception {
		aggregator.callmethod();
	}

}
