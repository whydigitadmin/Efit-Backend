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
@Table(name = "t_itemisstoproddtls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_itemisstoproddtlsgen")
	@SequenceGenerator(name = "t_itemisstoproddtlsgen", sequenceName = "t_itemisstoproddtlsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemisstoproddtlsid")
	private Long id;
    
	@Column(name = "item")
	private String item;

	@Column(name = "itemdesc")
	private String itemDesc;

	@Column(name = "unit")
	private String unit;

	@Column(name = "holdqty")
	private int holdQty;
	
	@Column(name = "avgqty")
	private int AvgQty;

	@Column(name = "reqqty")
	private int reqQty;
	
	@Column(name = "issueqty")
	private int issueQty;

	@Column(name = "pendingqty")
	private int pendingQty;
	
	@Column(name = "pickqty")
	private int pickQty;

	
	@ManyToOne
	@JoinColumn(name = "itemisstoprodid")
	@JsonBackReference
	private ItemIssueToProductionVO itemIssueToProductionVO;
}
