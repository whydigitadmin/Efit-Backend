package com.efitops.basesetup.entity;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchasequotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseQuotationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "t_purchasequotationgen")
	@SequenceGenerator(name = "t_purchasequotationgen", sequenceName = "t_purchasequotationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchasequotationid")
	private Long id;
	@Column(name="docid")
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name="customername")
	private String customerName;
	@Column(name="workorderno")
	private String workOrderNo;
	@Column(name="enquiryno")
	private String enquiryNo;
	@Column(name="supplierName")
	private String suppliername;
	@Column(name="supplierid")
	private Long supplierId;
	@Column(name="validtill")
	private LocalDate validTill;
	@Column(name="kindattention")
	private String kindAttention;
	@Column(name="taxcode")
	private String taxCode;
	@Column(name="contactperson")
	private String contactPerson;
	@Column(name="contactno")
	private String contactNo;
	@Column(name="qstatus")
	private String qStatus;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String updatedBy;
	@Column(name="finyear")
	private String finYear;
	@Column(name="orgid")
	private Long orgId;
	private boolean active;
	private boolean cancel;
	@Column(name="screencode")
	private String screenCode="PQ";
	@Column(name="screenname")
	private String screenName="PURCHASEQUATION";
	
	
	
	
	
	
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
