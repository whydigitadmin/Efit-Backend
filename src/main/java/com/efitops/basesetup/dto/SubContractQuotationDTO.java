package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractQuotationDTO {
	private Long id;
	private String enquiryNo;
	private LocalDate enquiryDate;
	private String subContractorName;
	private String subContractorId;
	private LocalDate VaildTill;
	private String taxCode;
	private String routeCardNo;
	private String contactPerson;
	private Long contactNo;
	private String scIssueNo;
	private String narration;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private List<SubContractQuotationDetailsDTO>subContractQuotationDetailsDTO;
}
