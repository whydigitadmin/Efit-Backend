package com.efitops.basesetup.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDTO {
	
	private Long id;
	private String grnNo;
	private String location;
	private String InwardNo;
	private String supplierName;
	private String poNo;
	private String gstNo;
	private String gstType; 
	private String adress;
	private String currency;
	private Long exchangeRate;
	private String grnClearTime;
	private String invDcNo;
	private Date invDcDate;
	private String customer; 
	private Long orgId;
	private String createdBy;
	List<GrnDetailsDTO> grnDetailsDTO;

}
