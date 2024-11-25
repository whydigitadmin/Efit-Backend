package com.efitops.basaesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basaesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documenttype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypegen")
	@SequenceGenerator(name = "documenttypegen", sequenceName = "documenttypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypeid")
	private Long id;
	
	@Column(name = "screencode",length =10)
	private String screenCode;
	
	@Column(name = "screenname",length =150)
	private String screenName;
	
	@Column(name = "description",length =150)
	private String description;
	
	@Column(name = "doccode",length =25)
	private String docCode;
	
	@Column(name = "createdby",length =25)
	private String createdBy;
	
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	
	@Column(name="orgid")
	private Long orgId;
		
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
