package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "t_purchasequotationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseQuotationVO1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "t_purchasequotationdetailsgen")
	@SequenceGenerator(name = "t_purchasequotationdetailsgen", sequenceName = "t_purchasequotationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchasequotationdetailsid")
	private Long id;
	
	@Column(name="item")
	private String item;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="unit")
	private String unit;
	@Column(name="qty")
	private BigDecimal qty;
	@Column(name="unitprice")
	private BigDecimal unitPrice;
	@Column(name="basicprice")
	private BigDecimal basicPrice;
	@Column(name="discount")
	private BigDecimal discount;
	@Column(name="discountamount")
	private BigDecimal discountAmount;
	@Column(name="quoteamount")
	private BigDecimal quoteAmount;

	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="purchaseQuotationVO1")
    private PurchaseQuotationVO purchaseQuotationVO; 
}
      