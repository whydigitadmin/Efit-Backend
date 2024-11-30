package com.efitops.basesetup.entity;

import java.sql.Date;

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
@Table(name = "m_machinemaster1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineMasterVO1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "m_machinemaster1gen")
	@SequenceGenerator(name = "m_machinemaster1gen", sequenceName = "m_machinemaster1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machinemaster1id")
	private Long id;

	@Column(name = "installationdate")
	private Date installationDate;

	@Column(name = "powerconsumption")
	private Long powerConsumption;

	@Column(name = "consumption")
	private Long consumption;

	@Column(name = "powerproduced")
	private Long powerProduced;

	@Column(name = "capacity")
	private Long capacity;

	@Column(name = "unit")
	private String unit;

	@Column(name = "bedsize")
	private String bedSize;

	@Column(name = "currentinamps")
	private Long currentInAmps;

	@Column(name = "voltage")
	private Long voltage;

	@Column(name = "cushiontonnage")
	private String cushionTonnage;

	@Column(name = "machinetype")
	private String machineType;

	@Column(name = "hourlyrate")
	private Long hourlyRate;

	@Column(name = "instrumentwt")
	private Long instrumentWt;

	@Column(name = "uom")
	private String uom;

	@Column(name = "warrantystdate")
	private Date warrantyStDate;

	@Column(name = "warrantyenddate")
	private Date warrantyEndDate;

	@Column(name = "lastcalibrateddate")
	private Date lastCalibratedDate;

	@Column(name = "nextduedate")
	private Date nextDueDate;

	@Column(name = "lifecycle")
	private String lifeCycle;

	@Column(name = "rangeinfo")
	private String rangeInfo;

	@Column(name = "errorallowed")
	private String errorAllowed;

	@Column(name = "frequenceofcalibration")
	private String frequenceOfCalibration;

	@Column(name = "maintenancedate")
	private Date maintenanceDate;
	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="machinemasterid")
	private MachineMasterVO machineMasterVO;
}
