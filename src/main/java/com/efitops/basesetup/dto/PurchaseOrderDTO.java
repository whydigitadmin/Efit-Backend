package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
	
	private Long id;
	private String poNo;
	private LocalDate poDate= LocalDate.now();
	private String customerName;
	private String customerCode;
	private String workOrderNo;
	private String basedOn;
	private String quotationNo;
	private String purchaseIndentNo; 
	private String supplierName;
	private long contactperson;
	private String mobileNo;
	private String eMail;
	private String city;
	private Date state;
	private String country; 
	private String taxCode; 
	private String address; 
	private BigDecimal grossAmount;
	private BigDecimal netAmount;
	private BigDecimal totalAmountTax;
	private String amtInWords;
	private String remarks;
	
	private Long orgId;
	private String createdBy;
	private boolean active;
	private boolean cancel;
	
	List<PurchaseOrderDetailsDTO> purchaseOrderDetailsDTO;

}