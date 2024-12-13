package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetailsDTO {
	
	private Long id;
	private String item;
	private String itemDesc;
	private String hsnSacCode;
	private String taxType;
	private String uom;
	private BigDecimal prevRate;
	private BigDecimal price; 
	private BigDecimal qty;
	private BigDecimal discount;
	private BigDecimal discountAmt;
	private BigDecimal amount;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;
	private BigDecimal taxValue;
	private BigDecimal landedValue;

}
