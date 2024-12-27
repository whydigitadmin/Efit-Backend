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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dailypatrolinspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPatrolInspectionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dailypatrolinspectiongen")
	@SequenceGenerator(name = "dailypatrolinspectiongen", sequenceName = "dailypatrolinspectionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailypatrolinspectionid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partname")
	private String partName;
	@Column(name = "drgno")
	private String drgNo;
	private String shift;
	@Column(name = "machineno")
	private String machineNo;
	@Column(name = "machinename")
	private String machineName;
	private String time;
	@Column(name = "joborderno")
	private String jobOrderNo;
	@Column(name = "documentformatno")
	private String documentFormatNo;
	private String narration;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "screencode")
	private String screenCode="DPI";
	@Column(name = "screnname")
	private String screnName="DAILYPATROLLINSPECTION";
	private boolean cancel;
	private boolean active;

	@OneToMany(mappedBy = "dailyPatrolInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
		 	 private List<DailyPatrolInspectionDetails1VO> dailyPatrolInspectionDetails1VO;

	@OneToMany(mappedBy = "dailyPatrolInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DailyPatrolInspectionFinalVO> dailyPatrolInspectionFinalVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
