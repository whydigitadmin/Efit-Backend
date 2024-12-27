package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemWiseProcessMasterDTO {

	private Long id;
	private String processType;
	private String item;
	private String itemDesc;
	private String createdBy;
	private Long orgId;
	
	List<ItemWiseProcessDetailsDTO> itemWiseProcessDetailsDTO;
}
