package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionalInspectionReportDTO {
	private String characteristics;
	private String methodOfInspection;
	private String specification;
	private BigDecimal lsl;
	private BigDecimal usl;
	private String sample1;
	private String sample2;
	private String sample3;
	private String sample4;
	private String sample5;
	private String sample6;
	private String sample7;
	private String sample8;
	private String sample9;
	private String sample10;
	private String remarks;

}
