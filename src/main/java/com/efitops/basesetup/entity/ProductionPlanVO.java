package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_productionplan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_productionplangen")
	@SequenceGenerator(name = "m_productionplangen", sequenceName = "m_productionplanseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "productionplanid")
	private Long id;
}
