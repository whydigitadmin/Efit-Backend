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
@Table(name = "t_recieveformsubcontractdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecieveFromSubContractDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_recieveformsubcontractdetailsgen")
	@SequenceGenerator(name = "t_recieveformsubcontractdetailsgen", sequenceName = "t_recieveformsubcontractdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "recieveformsubcontractdetailsid")
	private Long id;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "issueqty")
	private String issueQty;
	@Column(name = "recieveqty")
	private String recieveQty;
	@Column(name = "pendingqty")
	private String pendingQty;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "recieveformsubcontractid")
	@JsonBackReference
	private RecieveFromSubcontractVO recieveFromSubcontractVO;

}
