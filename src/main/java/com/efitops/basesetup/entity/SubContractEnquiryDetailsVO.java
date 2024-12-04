package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_subcontractenquirydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractEnquiryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_subcontractenquirydetailsgen")
	@SequenceGenerator(name = "t_subcontractenquirydetailsgen", sequenceName = "t_subcontractenquirydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractenquirydetailsid")
	private Long id;
	@Column(name = "part")
	private String part;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "process")
	private String process;
	@Column(name="qty",precision = 10,scale = 2)
	private BigDecimal qty;
	@Column(name="deliverydate")
	private LocalDate deliveryDate;
	@Column(name = "remarks")
	private String remarks;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="subcontractenquiryid")
	private SubContractEnquiryVO subContractEnquiryVO;

}