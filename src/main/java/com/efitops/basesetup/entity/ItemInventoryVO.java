package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "m_iteminventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ItemInventoryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_iteminventorygen")
	@SequenceGenerator(name = "m_iteminventorygen", sequenceName = "m_iteminventoryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "iteminventoryid")
	private Long id;

	@Column(name = "importlocal")
	private String importLocal;

	@Column(name = "stocklocation")
	private String stockLocation;

	@Column(name = "minorderquantity")
	private int minOrderQuantity;

	@Column(name = "reorderlevel")
	private int reOrderLevel;

	@ManyToOne
	@JoinColumn(name = "itemid")
	@JsonBackReference
	private ItemVO itemVO;

}
