package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecieveFromSubContractDetailsDTO {

	private String partName;
	private String partDesc;
	private String issueQty;
	private String recieveQty;
	private String pendingQty;
	private String remarks;

}
