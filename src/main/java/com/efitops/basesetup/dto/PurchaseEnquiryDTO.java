package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryDTO {
	
	private Long id;
    private String purchaseEnquiryNo;
    private String purchaseEnquiryDate;
    private String customerName;
	private String customerCode;
    private String workOrderNo;
    private String purchaseIndentNo;
    private String customerPoNo;
    private String fgPartName;
    private String fgPartDesc;
    private String supplierName;
	private String supplierCode;
    private String contactPerson;
    private String contactNo;
    private String enquiryType;
    private String enquiryDueDate;
    private String expectedDeliveryDate;
    private String summary;

 	private String branch;
 	private String branchCode;
 	private String createdBy;
 	private String cancelRemarks;
 	private String finYear;
 	private Long orgId;
    
 	private List<PurchaseEnquiryDetailsDTO> purchaseEnquiryDetailsDTO;
}
