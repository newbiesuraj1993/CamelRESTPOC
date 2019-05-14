package com.infy.camelpoc.restpackage;

import org.springframework.stereotype.Component;

@Component
public class Record {

	private String recordId;

	public Record(String recordId) {
		this.recordId = recordId;
	}

	public Record() {
		super();
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
}
