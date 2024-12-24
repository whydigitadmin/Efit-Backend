package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "m_measuringinstruments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasuringInstrumentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_measuringinstrumentsgen")
	@SequenceGenerator(name = "m_measuringinstrumentsgen", sequenceName = "m_measuringinstrumentsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "measuringinstrumentsid")
	private Long id;

	@Column(name = "docid")
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "instrumentname")
	private String instrumentName;

	@Column(name = "instrumentcode")
	private String instrumentCode;

	@Column(name = "ranges")
	private BigDecimal ranges;

	@Column(name = "leastcount")
	private BigDecimal leastCount;

	@Column(name = "colourcode")
	private String colourCode;

	@Column(name = "calibrationfrequence")
	private String calibrationFrequence;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 5)
	private String screenCode = "MI";
	@Column(name = "screenname", length = 30)
	private String screenName = " MEASURINGINSTRUMENT";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
