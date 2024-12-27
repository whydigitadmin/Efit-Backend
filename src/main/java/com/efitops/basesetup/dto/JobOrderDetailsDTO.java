package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderDetailsDTO {
	private Long id;
	private String timeInHours;
	private String unit;
	private String hoursProduction;
	private String rework;	
	private String reject;
	private String idealTime;
	private String cumulativeTest;
	private String remarks;
}
