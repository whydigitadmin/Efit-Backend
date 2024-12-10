package com.efitops.basesetup.dto;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingMasterDTO {

private Long id;
	
	private String partNo;
	
	private String drawingNo;
	
	private String drawingRevNo;
	
	private LocalDate effDate;
	
	private String fgPartNo;
	
	private String fgPartName;
	
	private String createdBy;
	
	private String cancelRemarks;
	
	private Long orgId;
	
	private boolean active;

	
	private List<DrawingMaster1DTO> drawingMaster1DTO;
	
	private List<DrawingMaster2DTO> drawingMaster2DTO;
}


