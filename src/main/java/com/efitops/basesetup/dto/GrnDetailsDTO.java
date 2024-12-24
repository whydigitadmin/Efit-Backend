package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class GrnDetailsDTO {
		
		private String itemCode;
		private String itemDesc;
		private String hsnSacCode;
		private String taxType;
		private String primaryUnit;
		private Long stock;
		private String inspectionable;
		private Long poRate; 
		private Long orderQty;
		private Long challanQty;
		private Long pendingQty;
		private Long recievedQty;
		private Long acceptQty;
		private Long rejectQty;
		private Long excessQty;
		private Long amount;
		private Long sgst;
		private Long cgst;
		private Long igst;
		private Long taxValue;
		private Long landedValue;

	}


