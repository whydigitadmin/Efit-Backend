package com.efitops.basaesetup.entity;

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

import com.efitops.basaesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="responsibility")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsibilityVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "responsibilitygen")
	@SequenceGenerator(name = "responsibilitygen", sequenceName = "responsibilityseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "responsibilityid")
	private Long id;
	
	@Column(name="responsibility")
	private String responsibility;
	
	@Column(name="createdby")
	private String createdBy;
	
	@Column(name="modifiedby")
	private String updatedBy;
	
	@Column(name="orgid")
	private Long orgId;
	
	private boolean active;

	@JsonManagedReference
	@OneToMany(mappedBy = "responsibilityVO", cascade = CascadeType.ALL)
	private List<ScreensVO>screensVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }
	

}
