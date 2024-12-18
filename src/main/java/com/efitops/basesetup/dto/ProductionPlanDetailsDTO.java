package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanDetailsDTO {
	private Long id;
	private String process;
	private int qty;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String timeTakenInSec;
	private String totalTimeTaken;
	private String timeTakenInHours;
	private String qtyPerHr;
	private String expMinProd;
	private String expMaxProd;
	private String status;
}