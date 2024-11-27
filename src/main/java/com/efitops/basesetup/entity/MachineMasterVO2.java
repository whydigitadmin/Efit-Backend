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
@Table(name = "machinemaster2")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineMasterVO2 {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "machinemaster2gen")
	@SequenceGenerator(name = "machinemaster2gen", sequenceName = "machinemaster2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machinemaster2id")
	private Long id;
	
	@Column(name = "itemid")
    private String itemId;

    @Column(name = "itemdescription")
    private String itemDescription;

    @Column(name = "cycletime")
    private String cycleTime;

    @Column(name = "prodqtyhr")
    private Long prodQtyHr;

    @Column(name = "operationname")
    private String operationName;

    @Column(name = "remarks")
    private String remarks;
	
    @ManyToOne
	@JsonBackReference
	@JoinColumn(name ="machinemasterid")
	private MachineMasterVO machineMasterVO;
	
}

