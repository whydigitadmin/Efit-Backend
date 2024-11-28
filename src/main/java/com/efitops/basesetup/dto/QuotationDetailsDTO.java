package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDetailsDTO {
	private String partCode;
	private String partDescription;
	private Long drawingNo;
	private Long revisionNo;
	private String unit;
	private BigDecimal unitPrice;
	private BigDecimal qtyOffered;
	private BigDecimal basicPrice;
	private BigDecimal discount;
	private BigDecimal discountAmount;
	private BigDecimal quoteAmount;
	private LocalDate deliveryDate;
}