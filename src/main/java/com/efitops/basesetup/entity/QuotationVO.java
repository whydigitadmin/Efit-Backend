package com.efitops.basesetup.entity;
import java.math.BigDecimal;
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
@Table(name = "t_quotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_quotationgen")
	@SequenceGenerator(name = "t_quotationgen", sequenceName = "t_quotationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quotationid")
	private Long id;
	@Column(name = "quoteno", length = 150)
	private String quoteNo;
	@Column(name="date")
	private LocalDate date= LocalDate.now();
	@Column(name="customername")
    private String customerName;
	@Column(name="customerid")
	private String customerId;
	@Column(name="enquiryno")
	private String enquiryNo;
	@Column(name="enquirydate")
	private LocalDate enquiryDate;
	@Column(name="vaidtill")
	private LocalDate vaidTill;
	@Column(name="kindattention")
	private String kindAttention;
	@Column(name="contactno")
	private Long contactNo;
	@Column(name="taxcode")
	private String taxCode;
	@Column(name="productionmanager")
	private String productionManager;
	@Column(name="currency")
	private String currency;
	
	@Column(name="grossamount",precision = 10,scale = 2)
	private BigDecimal grossAmount;
	@Column(name="netamount",precision = 10,scale = 2)
	private BigDecimal netAmount;
	@Column(name = "amountinwords",length = 150)
	private String amountInWords;
	
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
	private String screenCode ="QOT";
	@Column(name = "screenname",length = 30)
	private String screenName="QUOTATION";
	
	@OneToMany(mappedBy = "quotationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<QuotationDetailsVO> quotationDetailsVO;
	
	
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
