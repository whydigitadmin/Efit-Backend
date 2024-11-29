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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_gateoutwardentry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateOutwardEntryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_gateoutwardentrygen")
	@SequenceGenerator(name = "t_gateoutwardentrygen", sequenceName = "t_gateoutwardentryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gateoutwardentryid")
	private Long id;
	

	@Column(name = "customerno")
	private String customerNo;

	@Column(name = "type")
	private String type;

	@Column(name = "deliverychallanno")
	private String deliveryChallanNo;
	
	@Column(name = "deliverychallandate")
	private LocalDate deliveryChallanDate;
	
	@Column(name = "invoiceno")
	private String invoiceNo;
	
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	
	@Column(name = "modeofshipment")
	private String modeOfShipment;
	
	@Column(name = "vehicleno")
	private String vehicleNo;
	
	@Column(name = "narration")
	private String narration;
	
	@Column(name = "screencode", length = 5)
	private String screenCode ="GOE";

	@Column(name = "screenname", length = 25)
	private String screenName ="GateOutWardEntry";
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active ;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	
	@OneToMany(mappedBy = "gateOutwardEntryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<GateOutwardEntryDetailsVO> gateOutwardEntryDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
