package com.efitops.basesetup.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_jobworkout")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobwWorkOutVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_jobworkoutgen")
	@SequenceGenerator(name = "t_jobworkoutgen", sequenceName = "t_jobworkoutseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "jobworkoutid")
	private Long id;
	@Column(name = "jobworkorderno", length = 150)
	private String jobWorkOrderNo;
	@Column(name="jobworkordedate")
	private LocalDate jobWorkOrderDate= LocalDate.now();
	@Column(name="dcno")
	private String dcNo;
	@Column(name="routecardno")
	private String routeCardNo;
	@Column(name="quoteno")
	private String quotationNo;
	@Column(name="pono")
	private String poNo;
	@Column(name="contractorname")
	private String contractorName;
	@Column(name="gsttype")
	private String gstType; 
	@Column(name="destination")
	private String destination;
	@Column(name="contractorcode")
	private String contractorCode;
	@Column(name="dispatchedthrough")
	private Long dispatchedThrough;
	@Column(name="durationofprocess")
	private String durationOfrocess;
	@Column(name="customer")
	private String customer; 
	
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode",length = 30)
	private String screenCode ="JWO";
	@Column(name = "screenname",length = 30)
	private String screenName="JOBWORKORDER";
	
	
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<GrnDetailsVO> grnDetailsVO;
	

}
