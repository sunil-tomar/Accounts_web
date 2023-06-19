package com.homemaker.Accounts.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product extends BaseDomain<Long> implements Serializable{
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

	@JsonIgnoreProperties("productSet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subcat_id")
	private SubCategory subCategory;


	//Sample Code for setting new Date for CreateTime.
	@PrePersist protected void onCreatedtime() {
		Date date=new Date();
		createdTime = date;
		updatedTime = date;
	}




}