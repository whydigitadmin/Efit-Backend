package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_uommast")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UomVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_uommastgen")
	@SequenceGenerator(name = "m_uommastgen", sequenceName = "m_uommastseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "uommastid")
	private Long id;
	
	 @Column(name = "uomcode", length = 10)
	    private String uomCode;

	    @Column(name = "uomdesc", length = 40)
	    private String uomDesc;

	    @Column(name = "orgid")
		private Long orgId;
		@Column(name = "createdby")
		private String createdBy;
		@Column(name = "modifiedby")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel;
		@Column(name = "active")
		private boolean active;

}
