package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salesorderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "salesorderdetailsgen")
	@SequenceGenerator(name = "salesorderdetailsgen", sequenceName = "salesorderdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesorderdetailsid")
	private Long id;
	@Column(name = "totaltaxamount")
	private Long totalTaxAmount;
	@Column(name = "grossamount")
	private Long grossAmount;
	@Column(name = "netamount")
	private Long netAmount;
	@Column(name = "amountinwords")
	private String amountInWords;

}
