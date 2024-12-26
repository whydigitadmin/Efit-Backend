package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {

private String customerName;
	
	private String customerCode;
	
	private String currency;
	
	private Long exChangeRate;
	
	private String customerPoNo;
	
	private String workOrderNo;
	
	private String shippingAddress;
	
	private String billingAddress;
	
	private String contactPerson;
	
	private String customerMail;
	
	private String placeOfSupply;
	
	private String taxType;
	
	private String invoiceType;
	
	private LocalDate dueDate;
	
	private String terms;
	
	private String description;
	
	private String narration;
	
	private String createdBy;
	
	private Long orgId;
	
}
