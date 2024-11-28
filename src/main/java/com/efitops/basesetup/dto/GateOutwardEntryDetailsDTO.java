package com.efitops.basesetup.dto;

import javax.persistence.Column;

import com.efitops.basesetup.entity.GateOutwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateOutwardEntryDetailsDTO {

	private Long id;
	private String item;
	private String itemDesc;
	private String uom;
	private int qty;

}
