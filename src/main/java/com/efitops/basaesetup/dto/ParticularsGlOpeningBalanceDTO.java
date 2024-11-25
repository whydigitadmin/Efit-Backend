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
public class ParticularsGlOpeningBalanceDTO {
	private Long id;
	private String accountName;
	private BigDecimal subLedgerCode;
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal debitBase;
	private BigDecimal creditBase;
}
