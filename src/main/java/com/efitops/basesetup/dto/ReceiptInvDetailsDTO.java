package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptInvDetailsDTO {
	private Long id;
	private String invNo;
	private LocalDate invDate;
	private String refNo;
	LocalDate refDate;
	private String masterRef;
	private String houseRef;
	private String currency;
	private BigDecimal exRate;
	private BigDecimal amount;
	private BigDecimal chargeAmt;
	private BigDecimal outstanding;
	private BigDecimal settled;
	private BigDecimal recExRate;
	private BigDecimal txnSettled;
	private BigDecimal gainAmt;
}
