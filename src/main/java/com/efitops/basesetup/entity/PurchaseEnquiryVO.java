package com.efitops.basesetup.entity;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_purchaseenquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "t_purchaseenquirygen")
	@SequenceGenerator(name = "t_purchaseenquirygen", sequenceName = "t_purchaseenquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseenquiryid")
	private Long id;

	@Column(name = "purchaseenquiryno", length = 20)
    private String purchaseEnquiryNo;

    @Column(name = "purchaseenquirydate", length = 20)
    private String purchaseEnquiryDate;

    @Column(name = "customername", length = 150)
    private String customerName;

    @Column(name = "workorderno", length = 30)
    private String workOrderNo;

    @Column(name = "purchaseindentno", length = 30)
    private String purchaseIndentNo;

    @Column(name = "customerpono", length = 30)
    private String customerPoNo;

    @Column(name = "fgpartname", length = 60)
    private String fgPartName;

    @Column(name = "fgpartdesc", length = 65)
    private String fgPartDesc;

    @Column(name = "suppliername", length = 65)
    private String supplierName;

    @Column(name = "contactperson", length = 35)
    private String contactPerson;

    @Column(name = "contactno", length = 10)
    private String contactNo;

    @Column(name = "enquirytype", length = 25)
    private String enquiryType;

    @Column(name = "enquiryduedate", length = 10)
    private String enquiryDueDate;
	
    @Column(name = "expecteddeliverydate", length = 10)
    private String expectedDeliveryDate;
    
    // Additional fields with column mappings
    @Column(name = "branch", length = 25)
    private String branch;

    @Column(name = "branchcode", length = 20)
    private String branchCode;

    @Column(name = "createdby", length = 25)
    private String createdBy;

    @Column(name = "modifyby", length = 25)
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel= false;

    @Column(name = "cancelremarks", length = 50)
    private String cancelRemarks;

    @Column(name = "finyear", length = 10)
    private String finYear;

    @Column(name = "screencode", length = 5)
    private String screenCode = "PE";

    @Column(name = "screenname", length = 25)
    private String screenName = "PURCHASEENQUIRY";

    @Column(name = "orgid")
    private Long orgId;
    
    private String summary;
    
    @Column(name = "docid")
    private String docId;
    
    @OneToMany(mappedBy ="purchaseEnquiryVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseEnquiryDetailsVO> purchaseEnquiryDetailsVO;
    
}
