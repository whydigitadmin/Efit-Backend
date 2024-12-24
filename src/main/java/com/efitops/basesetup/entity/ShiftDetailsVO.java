package com.efitops.basesetup.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_shiftdet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_shiftdetgen")
	@SequenceGenerator(name = "m_shiftdetgen", sequenceName = "m_shiftdetseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "shiftdetid")
	private Long id;
	
	@Column(name = "sno", length = 50)
    private String sno;

    @Column(name = "timeinhours", length = 20)
    private String timingInHours;
    
    @ManyToOne
	@JoinColumn(name = "shiftmastid")
	@JsonBackReference
	private ShiftVO shiftVO;
}