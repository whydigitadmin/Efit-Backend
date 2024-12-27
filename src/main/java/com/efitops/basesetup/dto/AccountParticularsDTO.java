package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.efitops.basesetup.entity.AccountParticularsVO;
import com.efitops.basesetup.entity.AdjustmentJournalVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountParticularsDTO {
	
	private String accountsName;
	private String subledgerName;
	private String subLedgerCode;
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal debitBase;
	private BigDecimal creditBase;
	
}
