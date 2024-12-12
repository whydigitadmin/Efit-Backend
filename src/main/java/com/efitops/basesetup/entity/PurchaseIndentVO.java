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
@Table(name = "t_purchaseindent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseIndentVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchaseindentgen")
	@SequenceGenerator(name = "purchaseindentgen", sequenceName = "purchaseindentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseindentid")

	private Long id;
	@Column(name="docid")
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name="indenttype")
	private String indentType;
	@Column(name="customername")
	private String customerName;
	@Column(name="customercode")
	private String customerCode;
	@Column(name="workorderno")
	private String workOrderNo;
	@Column(name="department")
	private String department;
	@Column(name="fgpart")
	private String fgPart;
	@Column(name="fgpartdesc")
	private String fgPartDesc;
	@Column(name="fgqty")
	private Long fgQty;
	@Column(name="requestedby")
	private String requestedBy;
	@Column(name="customerpono")
	private String customerPoNo;
	@Column(name="orgid")
	private Long orgId;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String updatedBy;
	private boolean active;
	private boolean cancel;
	@Column(name="cancelremarks")
	private String cancelRemarks;
    private String	status;
    @Column(name="screencode")
    private String screenCode="PI";
    @Column(name="screenname")
    private String screenName="PURCHASEINDENT";
//    @Column(name="finyear")
//    private String finYear;
    
    @OneToMany(mappedBy ="purchaseIndentVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseIndentVO1> purchaseIndentVO1;
    
    @OneToMany(mappedBy ="purchaseIndentVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseIndentVO2> purchaseIndentVO2;
    
    
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
 
	
}
