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
@Table(name = "machinemaster3")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineMasterVO3 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "machinemaster3gen")
	@SequenceGenerator(name = "machinemaster3gen", sequenceName = "machinemaster3seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machinemaster3id")
	private Long id;
	
	@Column(name="instrumentname")
	private String instrumentName;
	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;
	@Column(name="filepath")
	private String filePath;
	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="machinemasterid")
	private MachineMasterVO machineMasterVO;
}