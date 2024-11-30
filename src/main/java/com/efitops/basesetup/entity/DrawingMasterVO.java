package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_drawingmaster")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "m_drawingmastergen")
	@SequenceGenerator(name = "m_drawingmastergen", sequenceName = "m_drawingmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "drawingmasterid")
	private Long id;
	@Column(name ="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name ="partno")
	private String partNo;
	@Column(name ="drawingno")
	private String drawingNo;
	@Column(name ="drawingrevno")
	private String drawingRevNo;
	@Column(name ="effdate")
	private LocalDate effDate;
	@Column(name ="fgpartno")
	private String fgPartNo;
	@Column(name ="fgpartname")
	private String fgPartName;
	@Column(name ="createdby")
	private String createdBy;
	@Column(name ="modifiedby")
	private String updatedBy;
	@Column(name ="cancelremarks")
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;
	@Column(name ="orgid")
	private Long orgId;
	
	
	@OneToMany(mappedBy ="drawingMasterVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<DrawingMaster1VO> drawingMaster1VO;
	
	@OneToMany(mappedBy ="drawingMasterVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<DrawingMaster2VO> drawingMaster2VO;
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
}

