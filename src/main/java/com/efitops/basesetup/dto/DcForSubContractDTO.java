package com.efitops.basesetup.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcForSubContractDTO {
	
	private Long id;
	private String deliveryChallanNo;
	private LocalDate deliveryChallanDate= LocalDate.now();
	private String scIssueNo;
	private String customerName;
	private String customerAddress;
	private String routeCardNo;
	private String gstNo;
	private String subContractorName; 
	private String subContractoraddress;
	private String subContractorId;
	private Long vehicleNo;
	private Date Duedate;
	private String disatchThrough;
	private Date ewayBillNo;
	private String narration;
	private boolean active;
	private Long orgId;
	private String createdBy;
	List<DcForSubContractDetailsDTO> dcForSubContractDetailsDTO;


}
