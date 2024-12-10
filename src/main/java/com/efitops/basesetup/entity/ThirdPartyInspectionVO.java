package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_thirdpartyinspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdPartyInspectionVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_thirdpartyinspectiongen")
	@SequenceGenerator(name = "t_thirdpartyinspectiongen", sequenceName = "t_thirdpartyinspectionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "thirdpartyinspectionid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate= LocalDate.now();
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "workoutno")
	private LocalDate workOutNo;
	@Column(name = "pono")
	private String poNo;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "thirdpartydetails")
	private String thirdPartyDetails;
	@Column(name = "thirdpartyaddress")
	private String thirdPartyAddress;
	
	
	@Column(name = "narration")
	private String narration;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode",length = 30)
	private String screenCode ="TPI";
	@Column(name = "screenname",length = 30)
	private String screenName="THIRDPARTYINSPECTION";
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	
	@OneToMany(mappedBy = "thirdPartyInspectionVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ThirdPartyInspectionDetailsVO> thirdPartyInspectionDetailsVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

}
