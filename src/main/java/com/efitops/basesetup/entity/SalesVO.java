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
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "salesgen")
	@SequenceGenerator(name = "salesgen", sequenceName = "salesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	private String currency;
	@Column(name = "exchangrate")
	private Long exChangeRate;
	@Column(name = "customerpono")
	private String customerPoNo;
	@Column(name = "workordeno")
	private String workOrderNo;
	@Column(name = "shippingaddress")
	private String shippingAddress;
	@Column(name = "billingaddress")
	private String billingAddress;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "customermail")
	private String customerMail;
	@Column(name = "placeofsupply")
	private String placeOfSupply;
	@Column(name = "taxtype")
	private String taxType;
	@Column(name = "invoicetype")
	private String invoiceType;
	@Column(name = "duedate")
	private LocalDate dueDate;
	private String terms;
	private String description;
	private String narration;
	@Column(name = "screencode")
	private String screenCode = "SA'";
	@Column(name = "screenname")
	private String screenName = "SALESORDER";
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
