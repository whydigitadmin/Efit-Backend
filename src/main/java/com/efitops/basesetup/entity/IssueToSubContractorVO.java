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
@Table(name = "t_issuetosubcontractor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueToSubContractorVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_issuetosubcontractorgen")
	@SequenceGenerator(name = "t_issuetosubcontractorgen", sequenceName = "t_issuetosubcontractorseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "issuetosubcontractorid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "department")
	private String department;
	@Column(name = "status")
	private String status;
	@Column(name = "narration")
	private String narration;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "ITSC";
	@Column(name = "screenname", length = 30)
	private String screenName = "ISSUETOSUBCONTRACTOR";
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;

	@OneToMany(mappedBy = "issueToSubContractorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<IssueItemDetailsVO> issueItemDetailsVO;

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
