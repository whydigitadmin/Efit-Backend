package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import com.efitops.basesetup.entity.PickListDetailsVO;
import com.efitops.basesetup.entity.PickListVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionDTO {

	private Long id;
	private String routeCardNo;
	private String workorder;
	private String fgItemId;
	private String fgItemDesc;
	private int fgQty;
	private String fromLocation;
	private String remarks;
	private String preparedBy;
	private String createdBy;
	private Long orgId;
	
	List<ItemIssueToProductionDetailsDTO> itemIssueToProductionDetailsDTO;
}
