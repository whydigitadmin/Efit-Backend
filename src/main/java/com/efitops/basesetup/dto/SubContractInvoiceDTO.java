package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractInvoiceDTO {
	private Long id;
	private String jobWorkOrderNo;
	private String dcno;
	private String deliveryNoteDate;
	private String dispatchedThrough;
	private String routeCardNo;
	private String subContractorName;
	private String subContractorCode;
	private String subContractorAddress;

	private BigDecimal grossAmount;
	private BigDecimal netAmount;
	private BigDecimal totalTaxAmount;
	private String amountInWords;
	private String narration;

	private Long orgId;
	private String createdBy;
	private boolean active;
     List<SubContractTaxInvoiceDetailsDTO>subContractTaxInvoiceDetailsDTO;
      List<SubContractTermsAndConditionsDTO>subContractTermsAndConditionsDTO;
}
