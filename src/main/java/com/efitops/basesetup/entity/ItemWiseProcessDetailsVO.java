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
@Table(name = "m_itemwiseprocessdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemWiseProcessDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_itemwiseprocessdetailsgen")
	@SequenceGenerator(name = "m_itemwiseprocessdetailsgen", sequenceName = "m_itemwiseprocessdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemwiseprocessdetailsid")
	private Long id;
	
	@Column(name = "processname")
	private String processName;
	
	@ManyToOne
	@JoinColumn(name = "itemwiseprocessid")
	@JsonBackReference
	private ItemWiseProcessMasterVO itemWiseProcessMasterVO;
	
}
