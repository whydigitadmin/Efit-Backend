package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseQuotationDTO {

	private Long id;

	private String customerName;
	
	private String customerCode;

	private String workOrderNo;
	
	private String enquiryNo;
	
	private LocalDate enquiryDate;

	private String supplierName;
	
	private Long supplierId;
	
	private LocalDate validTill;
	
	private String kindAttention;
	
	private String taxCode;
	
	private String contactPerson;
	
	private String contactNo;
	
	private String qStatus;
	
	private String createdBy;
		
	private Long orgId;
	
	private boolean active;
	
//	private BigDecimal grossAmount;
//	private BigDecimal netAmount;
//	private BigDecimal totalDiscount;
	private String narration;
//	private String amountInWords;
	
	List<PurchaseQuotationDetailsDTO> purchaseQuotationDetailsDTO;
	
	List<PurchaseQuotationAttachmentDTO> purchaseQuotationAttachmentDTO;
	
}
