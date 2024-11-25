package com.efitops.basaesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOtherAccountDTO {

	private Long id;
	private String accountName;
	private String subLedgerName;
	private BigDecimal debitAmount;
	private String remarks;
}
