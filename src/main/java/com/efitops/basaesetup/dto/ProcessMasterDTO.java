package com.efitops.basaesetup.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessMasterDTO {
	private Long id;
	private String processName;
	private Long orgId;
	private String createdBy;
	private boolean active;
}
