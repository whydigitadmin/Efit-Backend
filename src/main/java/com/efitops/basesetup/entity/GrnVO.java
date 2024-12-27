package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.sql.Date;
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
@Table(name = "grn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grngen")
	@SequenceGenerator(name = "grngen", sequenceName = "grnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grnid")
	private Long id;
	@Column(name = "grnno", length = 150)
	private String grnNo;
	@Column(name="grndate")
	private LocalDate grnDate= LocalDate.now();
	@Column(name="location")
	private String location;
	@Column(name="inwardno")
	private String InwardNo;
	@Column(name="suppliername")
	private String supplierName;
	@Column(name="pono")
	private String poNo;
	@Column(name="gstno")
	private String gstNo;
	@Column(name="gsttype")
	private String gstType; 
	@Column(name="address")
	private String adress;
	@Column(name="currency")
	private String currency;
	@Column(name="exchanderate",precision = 10,scale = 2)
	private BigDecimal exchangeRate;
	@Column(name="grncleartime")
	private String grnClearTime;
	@Column(name="invdcno")
	private String invDcNo;
	@Column(name="invdcdate")
	private Date invDcDate;
	@Column(name="customer")
	private String customer; 
	@Column(name="grossamount",precision = 10,scale = 2)
	private BigDecimal grossAmount;
	@Column(name="netamount",precision = 10,scale = 2)
	private BigDecimal netAmount;
	@Column(name="totalamounttax",precision = 10,scale = 2)
	private BigDecimal totalAmountTax;
	@Column(name="totallandedamount",precision = 10,scale = 2)
	private BigDecimal totalLandedAmount;
	@Column(name="remarks")
	private String remarks;
	
	
	
	
	
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
	@Column(name = "screencode",length = 30)
	private String screenCode ="GRN";
	@Column(name = "screenname",length = 30)
	private String screenName="GRN";
	
	
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<GrnDetailsVO> grnDetailsVO;
	
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
