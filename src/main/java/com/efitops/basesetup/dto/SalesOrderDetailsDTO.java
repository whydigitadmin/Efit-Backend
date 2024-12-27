package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesOrderDetailsDTO {

	private Long id;

	private Long totalTaxAmount;

	
	private Long grossAmount;

	private Long netAmount;

	private String amountInWords;

}
