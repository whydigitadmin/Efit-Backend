package com.efitops.basesetup.dto;

import java.util.List;

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

	List<ToolsIssueToCalibrationDetailsDTO> toolsIssueToCalibrationDetailsDTO;

}
