package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderDTO {
	private Long id;

//	Main Fields
	private String docId;
	private LocalDate docDate;
	private String shift;
	private String routeCardNo;
	private String workOrderNo;
	private String customerName;
	private String customerPoNo;
	private String partName;
	private String partNo;
	private String operationName;
	private String cycleTimeInSecs;
	private String normsHr;
	private String status;
	private String operatorName;
//	Defaul Fields
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active = true;
//	Summary Fields
	private String narration;

	List<JobOrderDetailsDTO> jobOrderDetailsDTO;
}