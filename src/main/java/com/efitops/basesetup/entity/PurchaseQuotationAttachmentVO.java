package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_purchasequotationattachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseQuotationAttachmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "t_purchasequotationattachmentgen")
	@SequenceGenerator(name = "t_purchasequotationattachmentgen", sequenceName = "t_purchasequotationattachmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchasequotationattachmentid")
	private Long id;
	
	@Column(name = "filename")
	private String fileName;
	
	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="purchaseQuotationid")
    private PurchaseQuotationVO purchaseQuotationVO; 
}
