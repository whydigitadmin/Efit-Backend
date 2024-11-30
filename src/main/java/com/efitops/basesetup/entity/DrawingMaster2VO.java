package com.efitops.basesetup.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_drawingmaster2")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingMaster2VO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "m_drawingmaster2gen")
	@SequenceGenerator(name = "m_drawingmaster2gen", sequenceName = "m_drawingmaster2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "drawingmaster2id")
	private Long id;
	
	@Column(name ="filename")
	private String fileName;
	
	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;
	
	@ManyToOne
	@JoinColumn(name="drawingmasterid")
	@JsonBackReference
	private DrawingMasterVO drawingMasterVO;
}


