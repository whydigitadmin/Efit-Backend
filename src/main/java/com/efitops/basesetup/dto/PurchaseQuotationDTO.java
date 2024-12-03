package com.efitops.basesetup.dto;

import java.time.LocalDate;

import com.efitops.basesetup.entity.PurchaseQuotationVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseQuotationDTO {

	private String customerName;
	
	private String workOrderNo;
	
	private String enquiryNo;
	
	private String supplierName;
	
	private Long supplierId;
	
	private LocalDate validTill;
	
	private String kindAttention;
	
	private String taxCode;
	
	private String contactPerson;
	
	private String contactNo;
	
	private String qStatus;
	
	private String createdBy;
	
	private String finYear;
	
	private Long orgId;
	
	private boolean active;
	
	
}
