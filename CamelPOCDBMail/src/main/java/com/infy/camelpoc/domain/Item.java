package com.infy.camelpoc.domain;

import java.math.BigDecimal;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator=",",skipFirstLine=true)
public class Item {

	@DataField(pos=1)
	private String transactionType;
	@DataField(pos=2)	
	private String skuNumber;
	@DataField(pos=3)	
	private String description;
	@DataField(pos=4)	
	private BigDecimal price;
	@Override
	public String toString() {
		return "Item [transactionType=" + transactionType + ", skuNumber=" + skuNumber + ", description=" + description
				+ ", price=" + price + ", getTransactionType()=" + getTransactionType() + ", getSkuNumber()="
				+ getSkuNumber() + ", getDescription()=" + getDescription() + ", getPrice()=" + getPrice()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getSkuNumber() {
		return skuNumber;
	}
	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
