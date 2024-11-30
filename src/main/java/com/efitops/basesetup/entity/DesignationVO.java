package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_designation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DesignationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_designationgen")
	@SequenceGenerator(name = "m_designationgen", sequenceName = "m_designationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "designationid")
	private Long id;
	
    @Column(name = "docid", length = 15)
    private String docid;

    @Column(name = "designation", length = 30)
    private String designation;

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
	
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
