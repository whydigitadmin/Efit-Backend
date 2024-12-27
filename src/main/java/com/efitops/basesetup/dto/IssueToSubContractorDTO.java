package com.efitops.basesetup.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueToSubContractorDTO {
	private Long id;
	private String routeCardNo;
	private String customerName;
	private String department;
	private String status;
	private String narration;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private List<IssueToSubContractorDetailsDTO> issueToSubContractorDetailsDTO;
}
