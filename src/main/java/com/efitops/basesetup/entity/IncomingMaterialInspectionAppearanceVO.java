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
@Table(name = "t_incomingmaterialinspectionappearance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingMaterialInspectionAppearanceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_incomingmaterialinspectionappearancegen")
	@SequenceGenerator(name = "t_incomingmaterialinspectionappearancegen", sequenceName = "t_incomingmaterialinspectionappearanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "incomingmaterialinspectionappearanceid")
	private Long id;
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "specifications")
	private String specifications;

	@ManyToOne
	@JoinColumn(name = "incomingmaterialinspectionid")
	@JsonBackReference
	IncomingMaterialInspectionVO incomingMaterialInspectionVO;

}
