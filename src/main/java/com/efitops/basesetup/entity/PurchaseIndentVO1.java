package com.efitops.basesetup.entity;

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
@Table(name = "t_purchaseindent1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseIndentVO1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchaseindent1gen")
	@SequenceGenerator(name = "purchaseindent1gen", sequenceName = "purchaseindent1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseindent1id")
	
	private Long id;
	
	private String item;
	@Column(name ="itemdesc")
	private String itemDescription;
	private String uom;
	@Column(name ="reqqty")
	private Long reqQty;
	@Column(name ="avlstock")
	private Long avlStock;
	@Column(name ="indentqty")
	private Long indentQty;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="purchaseindentid")
    private PurchaseIndentVO purchaseIndentVO; 
	
}