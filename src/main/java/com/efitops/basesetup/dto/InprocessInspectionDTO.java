package com.efitops.basesetup.dto;

import java.util.List;

import com.efitops.basesetup.entity.InprocessInspectionAppearanceVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionDTO {
	private Long id;
	private String routeCardNo;
	private String workOrderNo;
	private String partNo;
	private String partName;
	private String materialDrawingNo;
	private String customer;
	private int lotQty;
	private String drawingNo;
	private int receivedQty;
	private int sampleQty;

	// Summary

	private String checkedBy;
	private String approvedBy;
	private String naration;

	private Long orgId;
	private String createdBy;
	private boolean active;

	private List<InprocessInspectionDetailsDTO> inprocessInspectionDetailsDTO;
	private List<InprocessInspectionAppearanceDTO> inprocessInspectionAppearanceDTO;
}
