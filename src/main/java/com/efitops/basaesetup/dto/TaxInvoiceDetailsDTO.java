package com.efitops.basaesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxInvoiceDetailsDTO {
	
	private String chargeType;
	private String chargeCode;
	private String govChargeCode;
	private String ledger;
	private String chargeName;
	private String taxable;
	private int qty;
	private BigDecimal rate;
	private String currency;
	private BigDecimal exRate;
	private String exempted;
	private String sac;
	private int GSTPercent;

}
