package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.List;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_purchaseenquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tpurchaseenquirygen")
	@SequenceGenerator(name = "tpurchaseenquirygen", sequenceName = "tpurchaseenquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tpurchaseenquirydetailid")
	private Long id;
	
	@Column(name = "item", length = 20)
    private String item;

    @Column(name = "itemdesc", length = 20)
    private String itemDesc;

    @Column(name = "unit", length = 150)
    private String Unit;

    @Column(name = "qtyrequired", length = 30)
    private BigDecimal qtyRequired;

    @Column(name = "remarks", length = 30)
    private String remarks;

	@ManyToOne
	@JoinColumn(name="tpurchaseenquiryid")
	@JsonBackReference
	private PurchaseEnquiryVO purchaseEnquiryVO;

}