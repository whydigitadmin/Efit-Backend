package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractTaxInvoiceDetailsDTO {
	private String partNo;
	private String partDes;
	private String process;
	private BigDecimal quantityNos;
	private BigDecimal rate;
	private BigDecimal amount;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private BigDecimal landedAmount;
	private BigDecimal quotationAmount;
}
