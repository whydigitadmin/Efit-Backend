package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractQuotationDetailsDTO {
	private String part;
	private String partDescription;
	private String process;
	private BigDecimal qty;
	private BigDecimal rate;
	private BigDecimal amount;
	private BigDecimal discount;
	private BigDecimal discountAmount;
	private BigDecimal tax;
	private BigDecimal quotationAmount;
	private LocalDate deliveryDate;
}