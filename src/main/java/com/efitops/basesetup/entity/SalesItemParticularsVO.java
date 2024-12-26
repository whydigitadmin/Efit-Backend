package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salesitemparticulars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesItemParticularsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "salesitemparticularsgen")
	@SequenceGenerator(name = "salesitemparticularsgen", sequenceName = "salesitemparticularsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesitemparticularsid")
	private Long id;
	
	@Column(name ="partno")
	private String partNo;
	@Column(name ="partdesc")
	private String partDesc;
	@Column(name ="workorderno")
    private String workOrderNo;
	@Column(name ="duedate")
    private LocalDate dueDate;
	@Column(name ="unitprice")
    private Long unitPrice;
	@Column(name ="qtyofferd")
    private Long qtyOfferd;
	@Column(name ="exrate")
    private Long exRate;
	@Column(name ="basicamount")
    private Long basicAmount;
    private String discount;
    @Column(name ="taxableamount")
    private Long taxableAmount;
    @Column(name ="taxamount")
    private Long taxAmount;
    private Long amount;
	
}
