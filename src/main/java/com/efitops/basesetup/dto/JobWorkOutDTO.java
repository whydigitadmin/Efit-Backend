package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobWorkOutDTO {
	
	private Long id;
	private String jobWorkOrderNo;
	private LocalDate jobWorkOrderDate;
	private String dcNo;
	private String routeCardNo;
	private String quotationNo;
	private String poNo;
	private String contractorName;
	
	private String gstType; 
	
	private String destination;
	
	private String contractorCode;
	
	private Long dispatchedThrough;
	
	private String durationOfrocess;
	
	private String customer; 
	
	private String termsOfPay;
	
	private String amtInWords;
	
	private BigDecimal totalAmt;
	
	private BigDecimal totalGrossAmt;
	
	private BigDecimal totalTax; 
	
	private Long orgId;
	
	private String createdBy;

}
