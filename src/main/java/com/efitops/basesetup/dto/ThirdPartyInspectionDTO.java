package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyInspectionDTO {
	
	private Long id;
	private String docId;
	private LocalDate docDate;
	private String grnNo;
	private LocalDate workOutNo;
	private String poNo;
	private String customerName;
	private String supplierName;
	private String thirdPartyDetails;
	private String thirdPartyAddress;
	private String narration;
	
	private Long orgId;
	private String createdBy;
	private boolean cancel;
	List<ThirdPartyInspectionDetailsDTO> thirdPartyInspectionDetailsDTO;

}
