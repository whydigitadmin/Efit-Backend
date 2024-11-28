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
@Table(name = "t_termsandconditions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermsAndConditionsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_termsandconditionsgen")
	@SequenceGenerator(name = "t_termsandconditionsgen", sequenceName = "t_termsandconditionsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "t_termsandconditionsid")
	private Long id;
	@Column(name = "template")
	private String template;
	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "t_workorderid")
	@JsonBackReference
	WorkOrderVO workOrderVO;

}
