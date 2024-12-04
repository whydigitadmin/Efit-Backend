package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_jobworkourdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobWorkOutDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_jobworkourdetailsgen")
	@SequenceGenerator(name = "t_jobworkourdetailsgen", sequenceName = "t_jobworkourdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "jobworkourdetailsid")
	private Long id;
	@Column(name = "part", length = 150)
	private String part;
	@Column(name="partdesc")
	private String partDesc;
	@Column(name="process")
	private String process;
	@Column(name="taxcode")
	private String taxCode;
	@Column(name="quantity")
	private String quantity;
	@Column(name="dueon")
	private Date dueOn;
	@Column(name="inspectionable")
	private String inspectionable;
	@Column(name="rate")
	private BigDecimal rate; 
	@Column(name="grossamt")
	private BigDecimal grossAmt;
	@Column(name="discount")
	private BigDecimal discount;
	@Column(name="discountamt")
	private BigDecimal discountAmt;
	@Column(name="netamount")
	private BigDecimal netAmount;
	@Column(name="sgst")
	private BigDecimal sgst;
	@Column(name="cgst")
	private BigDecimal cgst;
	@Column(name="igst")
	private BigDecimal igst;
	@Column(name="taxvalue")
	private BigDecimal taxValue;
	@Column(name="landedvalue")
	private BigDecimal landedValue;
		
	@ManyToOne
	@JoinColumn(name = "grnid")
	@JsonBackReference
	private GrnVO grnVO;

}
