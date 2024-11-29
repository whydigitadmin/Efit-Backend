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
@Table(name = "t_putawaydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutawayDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_putawaydetailsgen")
	@SequenceGenerator(name = "t_putawaydetailsgen", sequenceName = "t_putawaydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawaydetailsid")
	private Long id;

	@Column(name = "item")
	private String item;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "unit")
	private String unit;
	@Column(name = "recqty")
	private int recQty;
	@Column(name = "putawayqty")
	private int putawayQty;
	@Column(name = "rackno")
	private String rackNo;
	

	@ManyToOne
	@JoinColumn(name = "putawayid")
	@JsonBackReference
	private PutawayVO putawayVO;
}
