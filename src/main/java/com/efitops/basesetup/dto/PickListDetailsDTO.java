package com.efitops.basesetup.dto;

import javax.persistence.Column;

import com.efitops.basesetup.entity.PickListDetailsVO;
import com.efitops.basesetup.entity.PickListVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickListDetailsDTO {


	private String item;

	private String itemName;

	private String unit;

	private String rackNo;

	private int rackQty;

	private int issuedQty;

	private int pickedQty;

	private int remainingQty;
}
