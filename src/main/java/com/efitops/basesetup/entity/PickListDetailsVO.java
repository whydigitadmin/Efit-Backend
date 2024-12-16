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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_picklistdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickListDetailsVO {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_picklistdetailsgen")
	@SequenceGenerator(name = "m_picklistdetailsgen", sequenceName = "m_picklistdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "picklistdetailsid")
	private Long id;
	
	@Column(name = "item")
	private String item;

	@Column(name = "itemname")
	private String itemName;

	@Column(name = "unit")
	private String unit;

	@Column(name = "rackno")
	private String rackNo;
	
	@Column(name = "rackqty")
	private int rackQty;

	@Column(name = "issuedqty")
	private int issuedQty;

	@Column(name = "pickedqty")
	private int pickedQty;

	@Column(name = "remainingqty")
	private int remainingQty  ;
	
	@Column(name = "actualqty")
	private int actualQty  ;

	@Column(name = "flag")
	private boolean flag;

	@ManyToOne
	@JoinColumn(name = "picklistid")
	@JsonBackReference
	private PickListVO pickListVO;
	
}
