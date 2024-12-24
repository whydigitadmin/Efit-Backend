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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_appearanceinspectionreport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppearanceInspectionReportVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_appearanceinspectionreportgen")
	@SequenceGenerator(name = "t_appearanceinspectionreportgen", sequenceName = "t_appearanceinspectionreportseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "appearanceinspectionreportid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "specification")
	private String specification;
	@Column(name = "lsl", precision = 10, scale = 2)
	private BigDecimal lsl;
	@Column(name = "usl", precision = 10, scale = 2)
	private BigDecimal usl;
	@Column(name = "observation")
	private String observation;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "finalinspectionreportid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	FinalInspectionReportVO finalInspectionReportVO;

}
