package com.homemaker.Accounts.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Table(name = "test_business_unit")
@NamedQuery(name = "BusinessUnit.findAll", query = "SELECT bu FROM BusinessUnit bu")
public class BusinessUnit extends BaseDomain<Long>
implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", nullable = false,updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time", nullable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	private Date updatedTime;

	//Sample Code for setting new Date for CreateTime.
	 //@PrePersist protected void onCreatedtime() { createdTime = new Date(); }





}