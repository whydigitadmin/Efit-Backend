package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutawayDetailsDTO {

	private String item;
	private String itemDesc;
	private String unit;
	private int recQty;
	private int putawayQty;
	private String rackNo;
}
