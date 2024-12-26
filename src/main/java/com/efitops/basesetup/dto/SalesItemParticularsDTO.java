package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesItemParticularsDTO {

private String partNo;
	
	private String partdesc;
	
    private String workOrderNo;
    
    private LocalDate dueDate;
    
    private Long unitPrice;
    
    private Long qtyOfferd;
    
    private Long exRate;
    
    private Long basicAmount;
    
    private String discount;
    
    private Long taxableAmount;
    
    private Long taxAmount;
    
    private Long amount;
	
}
