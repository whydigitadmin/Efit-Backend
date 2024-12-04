package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDTO {
	
	private Long id;
	private String shiftName;
	private String shiftType;
	private String shiftCode;
	private String fromHour;
	private String toHour;
	private String timing;
	private Long orgId;
	private String createdBy;
	private boolean active;
	
	 
	private List<ShiftDetailsDTO> ShiftDetailsDTO; 
}