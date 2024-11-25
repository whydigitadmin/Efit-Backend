package com.efitops.basaesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeTypeRequestDTO {
	private Long id;
	private String chargeType;
	private String chargeCode;
	private String chargeDescription;
	private String localChargeDescripition;
	private String serviceAccountCode;
	private String sacDescripition;
	private String salesAccount;
	private String purchaseAccount;
	private String taxable;
	private String taxType;
	private String ccFeeApplicable;
	private int taxablePercentage;
	private String ccJob;
	private String govtSac;
	private String excempted;
	private float gstTax;
	private boolean active;
	private String createdBy;
	private Long orgId;
}
