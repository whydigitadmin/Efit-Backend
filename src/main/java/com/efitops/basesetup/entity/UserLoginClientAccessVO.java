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
@Table(name = "userclientaccess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginClientAccessVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userloginclientaccessgen")
	@SequenceGenerator(name = "userloginclientaccessgen",sequenceName = "userloginclientaccessseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="userloginclientaccessid")
	private long id;
	
	@Column(name="client",length =150)
	private String client;
	@Column(name="customer",length =150)
	private String customer;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "usersid")
    private UserVO userVO;
	
//	@Embedded
//	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}