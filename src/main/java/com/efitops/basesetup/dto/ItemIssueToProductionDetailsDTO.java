package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionDetailsDTO {

	private String item;
	private String itemDesc;
	private String unit;
	private int holdQty;
	private int AvgQty;
	private int reqQty;
	private int issueQty;
	private int pendingQty;
}
