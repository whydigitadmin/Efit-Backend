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
@Table(name = "t_dcforsubcontractdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DcForSubContractDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_dcforsubcontractdetailsgen")
	@SequenceGenerator(name = "t_dcforsubcontractdetailsgen", sequenceName = "t_dcforsubcontractdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dcforsubcontractdetailsid")
	private Long id;
	
	@Column(name = "item")
	private String item;

	@Column(name = "itemdesc")
	private String itemDesc;
	
	@Column(name = "process")
	private String process;

	@Column(name = "unit")
	private String unit;
	
	@Column(name = "qty")
	private int qty;
	@Column(name = "weight")
	private String weight;
	@Column(name = "remarks")
	private String remarks;
	
	
	@ManyToOne
	@JoinColumn(name = "dcforsubcontractid")
	@JsonBackReference
	private DcForSubContractVO dcForSubContractVO;
	
	


}
