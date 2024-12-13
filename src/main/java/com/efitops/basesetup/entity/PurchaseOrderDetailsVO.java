package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "t_purchaseorderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_grndetailsgen")
	@SequenceGenerator(name = "t_grndetailsgen", sequenceName = "t_grndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grndetailsid")
	private Long id;
	@Column(name = "item", length = 150)
	private String item;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="hsnsaccode")
	private String hsnSacCode;
	@Column(name="taxtype")
	private String taxType;
	@Column(name="uom")
	private String uom;
	@Column(name="prevrate",precision = 10,scale = 2)
	private BigDecimal prevRate;
	@Column(name="price",precision = 10,scale = 2)
	private BigDecimal price; 
	@Column(name="qty",precision = 10,scale = 2)
	private BigDecimal qty;
	@Column(name="discount",precision = 10,scale = 2)
	private BigDecimal discount;
	@Column(name="discountamt",precision = 10,scale = 2)
	private BigDecimal discountAmt;
	@Column(name="amount",precision = 10,scale = 2)
	private BigDecimal amount;
	@Column(name="sgst",precision = 10,scale = 2)
	private BigDecimal sgst;
	@Column(name="cgst",precision = 10,scale = 2)
	private BigDecimal cgst;
	@Column(name="igst",precision = 10,scale = 2)
	private BigDecimal igst;
	@Column(name="taxvalue",precision = 10,scale = 2)
	private BigDecimal taxValue;
	@Column(name="landedvalue",precision = 10,scale = 2)
	private BigDecimal landedValue;
		
	@ManyToOne
	@JoinColumn(name = "purchaseorderid")
	@JsonBackReference
	private PurchaseOrderVO purchaseOrderVO;

}
