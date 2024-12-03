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
@Table(name = "t_issueitemdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueItemDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "t_issueitemdetailsgen")
	@SequenceGenerator(name = "t_issueitemdetailsgen", sequenceName = "t_issueitemdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "issueitemdetailsid")
	private Long id;
	@Column(name = "item")
	private String item;
	@Column(name = "itemdescription")
	private String itemDescription;
	@Column(name = "process")
	private String process;
	@Column(name="quantity",precision = 10,scale = 2)
	private BigDecimal quantity;
	@Column(name = "remarks")
	private String remarks;

	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="issuetosubcontractorid")
	private IssueToSubContractorVO issueToSubContractorVO;
	
}
