package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_thirdpartyinspectiondetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdPartyInspectionDetailsVO {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_thirdpartyinspectiondetailsgen")
	@SequenceGenerator(name = "t_thirdpartyinspectiondetailsgen", sequenceName = "t_thirdpartyinspectiondetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "t_thirdpartyinspectiondetailsid")
	private Long id;
	@Column(name = "itemid")
	private String itemId;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "inspectiontype")
	private String inspectionType;
	@Column(name = "certificateno")
	private String certificateNo;
	@Column(name = "status")
	private String status;
	@Column(name = "remarks")
	private String remarks;

	     @ManyToOne
		@JoinColumn(name = "thirdpartyinspectionid")
		@JsonBackReference
		private ThirdPartyInspectionVO thirdPartyInspectionVO;


}
