package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDTO1 {

	
	private String item;
	private String itemDescription;
	private String uom;
	private Long reqQty;
	private Long avlStock;
//	private Long indentQty;
	
}
