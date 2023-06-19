package com.homemaker.Accounts.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Table(name = "business_unit")
@NamedQuery(name = "BusinessUnit.findAll", query = "SELECT bu FROM BusinessUnit bu")
public class BusinessUnit extends BaseDomain<Long> implements Serializable{
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

	@JsonIgnoreProperties("businessUnit")
	@OneToMany(cascade=CascadeType.ALL, fetch =FetchType.LAZY, mappedBy="businessUnit")
	private Set<Category> categorySet;

	//Sample Code for setting new Date for CreateTime.
	@PrePersist protected void onCreatedtime() {
		Date date=new Date();
		createdTime = date;
		updatedTime = date;
	}




}