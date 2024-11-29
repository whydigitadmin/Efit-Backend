package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateOutwardEntryDTO {

	private Long id;
	private String customerNo;
	private String type;
	private String deliveryChallanNo;
	private LocalDate deliveryChallanDate;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String modeOfShipment;
	private String vehicleNo;
	private String narration;
	private String createdBy;
	private Long orgId;
	
	List<GateOutwardEntryDetailsDTO> gateOutwardEntryDetailsDTO;
}
