package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcForSubContractDetailsDTO {
	
	private String item;
	private String itemDesc;
	private String process;
	private String unit;
	private int qty;
	private String weight;
	private String remarks;

}
