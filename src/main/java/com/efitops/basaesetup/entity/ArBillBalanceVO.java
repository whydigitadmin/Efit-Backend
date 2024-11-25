package com.efitops.basaesetup.entity;

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

import com.efitops.basaesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arbillbalance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArBillBalanceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arbillbalancegen")
	@SequenceGenerator(name = "arbillbalancegen", sequenceName = "arbillbalanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "arbillbalanceid")
	private Long id;

	@Column(name = "docid", length = 50)
	private String docId;

	@Column(name = "docdate")
	@Builder.Default
	private LocalDate docDate = LocalDate.now();

	@Column(name = "accname", length = 50)
	private String accName;

	@Column(name = "partyname", length = 50)
	private String partyName;

	@Column(name = "partycode", length = 10)
	private String partyCode;

	@Column(name = "creditdays", precision = 3)
	private int creditDays;

	@Column(name = "doctype", length = 20)
	private String docType;

	@Column(name = "currency", length = 5)
	private String currency;

	@Column(name = "yearendexrate", precision = 10, scale = 5)
	private BigDecimal yearEndExRate;

	@Column(name = "billexrate", precision = 10, scale = 5)
	private BigDecimal billExRate;

	@Column(name = "postbillexrate")
	private boolean postBillExRate;

	@Column(name = "billno", precision = 10)
	private Long billNo;

	@Column(name = "billdate")
	private LocalDate billDate;

	@Column(name = "supprefno", length = 25)
	private String suppRefNo;

	@Column(name = "supprefdate")
	private LocalDate suppRefDate;

	@Column(name = "duedate")
	private LocalDate dueDate;

	@Column(name = "debitamt", precision = 10, scale = 2)
	private BigDecimal debitAmt;

	@Column(name = "creditamt", precision = 10, scale = 2)
	private BigDecimal creditAmt;

	@Column(name = "voucherno", length = 25)
	private String voucherNo;

	@Column(name = "adjustmentdone")
	private boolean adjustmentDone;

//	Common Fields

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String updatedBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@Column(name = "screencode", length = 5)
	@Builder.Default
	private String screenCode = "ARB";

	@Column(name = "screenname", length = 25)
	@Builder.Default
	private String screenName = "AR BILL BALANCE";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
