package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitChargesDTO {

	private Long id;

	private String jobType;

	private String jobNo;

	private String subJobNo;

	private String houseNo;

	private String chargeCode;

	private String gChargeCode;

	private String gSAC;

	private String chargeName;

	private String applyOn;

	private String currency;

	private BigDecimal exRate;
	
	private BigDecimal rate;

	private int taxPercentage;
	
	private int qty;
	
	private String ledger;

	private int gstPercentage;
	
	private boolean tax;
	
	private boolean exampted;
	
}
