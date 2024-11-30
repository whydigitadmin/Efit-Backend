package com.efitops.basesetup.entity;

import java.time.LocalDate;

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
@Table(name = "t_grndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_grndetailsgen")
	@SequenceGenerator(name = "t_grndetailsgen", sequenceName = "t_grndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grndetailsid")
	private Long id;
	@Column(name = "itemcode", length = 150)
	private String itemCode;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="hsnsaccode")
	private String hsnSacCode;
	@Column(name="taxtype")
	private String taxType;
	@Column(name="primaryunit")
	private String primaryUnit;
	@Column(name="stock")
	private Long stock;
	@Column(name="inspectionable")
	private String inspectionable;
	@Column(name="porate")
	private Long poRate; 
	@Column(name="orderqty")
	private Long orderQty;
	@Column(name="challanqty")
	private Long challanQty;
	@Column(name="pendingqty")
	private Long pendingQty;
	@Column(name="recievedqty")
	private Long recievedQty;
	@Column(name="acceptqty")
	private Long acceptQty;
	@Column(name="rejectqty")
	private Long rejectQty;
	@Column(name="excessqty")
	private Long excessQty;
	@Column(name="amount")
	private Long amount;
	@Column(name="sgst")
	private Long sgst;
	@Column(name="cgst")
	private Long cgst;
	@Column(name="igst")
	private Long igst;
	@Column(name="taxvalue")
	private Long taxValue;
	@Column(name="landedvalue")
	private Long landedValue;
		
	@ManyToOne
	@JoinColumn(name = "grnid")
	@JsonBackReference
	private GrnVO grnVO;
}
