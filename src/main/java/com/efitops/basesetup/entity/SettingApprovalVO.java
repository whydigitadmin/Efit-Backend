package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "t_settingapproval")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingApprovalVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_settingapprovalgen")
	@SequenceGenerator(name = "t_settingapprovalgen", sequenceName = "t_settingapprovalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "settingapprovalid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partname")
	private String partName;
	@Column(name = "drgno")
	private String drgNo;
	@Column(name = "operation")
	private String operation;
	@Column(name = "cycletime")
	private int cycleTime;
	@Column(name = "machineno")
	private String machineNo;
	@Column(name = "machinename")
	private String machineName;
	@Column(name = "sampleqty")
	private int sampleQty;
	@Column(name = "grncleartime")
	private LocalTime grnClearTime;
	@Column(name = "docformatno")
	private String docFormatNo;

	//summary
	
	@Column(name = "generalremarks")
	private String generalRemarks;
	@Column(name = "operatorname")
	private String operatorName;
	@Column(name = "settername")
	private String setterName;
	@Column(name = "shiftincharge")
	private String shiftInCharge;
	@Column(name = "qualityname")
	private String qualityName;
	@Column(name = "narration")
	private String narration;
	
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "screencode")
	private String screenCode = "SA";
	@Column(name = "screenname")
	private String screenName = "SETTING APPROVAL";

	@OneToMany(mappedBy = "settingApprovalVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SettingApprovalDetailsVO> settingApprovalDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
