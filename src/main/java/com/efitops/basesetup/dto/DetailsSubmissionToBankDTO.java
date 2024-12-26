package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsSubmissionToBankDTO {
	private Long id;
	private String docId;
	private LocalDate docDate;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private String narration;

	List<DetailsSubmissionToBankDetailsDTO> detailsSubmissionToBankDetailsDTO;
}
