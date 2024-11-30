package com.efitops.basesetup.dto;
import java.time.LocalDate;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDTO {
	private Long id;
	private String  customerName; 
	private String customerPoNo;
	private String quotationNo;
	private String currency;
	private LocalDate customerDueDate;
	private LocalDate vapDueDate;
	private String productionMgr;
	private String customerSpecialRequirement;
	private Long orgId;
	private boolean active;
	private String createdBy;
	List<ItemParticularsDTO> itemParticularsDTO;
	List<TermsAndConditionsDTO> termsAndConditionsDTO;

}
