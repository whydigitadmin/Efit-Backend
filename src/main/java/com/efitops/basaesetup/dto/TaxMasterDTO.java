package com.efitops.basaesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxMasterDTO {
	
	private Long id;
	private Long orgId;
	private String finYear;
	private String serviceAccountCode;
	private String warehouse;
	private String gst;
	private Long gstSlab;
	private String createdBy;
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;

	private List<TaxMasterDetailsDTO> taxMasterDetailsDTO;
}
