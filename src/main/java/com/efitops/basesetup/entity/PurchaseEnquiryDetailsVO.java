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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_purchaseenquirydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_purchaseenquirygen")
	@SequenceGenerator(name = "t_purchaseenquirygen", sequenceName = "t_purchaseenquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseenquirydetailid")
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
	@JoinColumn(name="purchaseenquiryid")
	@JsonBackReference
	private PurchaseEnquiryVO purchaseEnquiryVO;

}
