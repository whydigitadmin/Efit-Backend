package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDTO {
	private Long id;
	 private String customerName;
		private String customerId;
		private String enquiryNo;
		private LocalDate enquiryDate;
		private LocalDate vaidTill;
		private String kindAttention;
		private Long contactNo;
		private String taxCode;
		private String productionManager;
		private String currency;
		private Long orgId;
		private String createdBy;
		
   List<QuotationDetailsDTO>quotationDetailsDTO;
}
