package com.efitops.basesetup.entity;

import java.time.LocalDate;
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

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnquiryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirygen")
	@SequenceGenerator(name = "enquirygen", sequenceName = "nquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "enquiryid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "enquirytype")
	private String enquiryType;
	@Column(name = "customer")
	private String customer;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "enquiryduedate")
	private LocalDate enquiryDueDate;
	@Column(name = "contactname")
	private String contactName;
	@Column(name = "contactno")
	private Long contactNo;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode", length = 30)
	private String screenCode = "ENY";
	@Column(name = "screenname", length = 30)
	private String screenName = "ENQUIRY";

	@OneToMany(mappedBy = "enquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<EnquiryDetailsVO> enquiryDetailsVO;

	@OneToMany(mappedBy = "enquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<EnquirySummaryVO> enquirySummaryVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
