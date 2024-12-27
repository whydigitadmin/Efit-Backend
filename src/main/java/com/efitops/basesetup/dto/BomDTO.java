package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomDTO {
	
	private Long id;
	private String productType;
	
	private String productCode;
	
	private String productName;
	
	private String uom;
	
	private BigDecimal qty;
	
	private boolean active; 
	private boolean revision; 
	
	private boolean current;
	
	
	
	private Long orgId;
	
	private String createdBy;

	List<BomDetailsDTO> bomDetailsDTO;
}
