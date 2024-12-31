package com.efitops.basesetup.dto;

import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolsIssueToCalibrationDTO {

	private Long id;
	private String issuePartyName;
	private String issuePartyAddress;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private Long totalQty;
	private String issueCreatedBy;
	private String remarks;
	private String narration;

	List<ToolsIssueToCalibrationDetailsDTO> toolsIssueToCalibrationDetailsDTO;

}
