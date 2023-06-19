package com.homemaker.Accounts.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "subcategory")
@NamedQuery(name = "SubCategory.findAll", query = "SELECT sc FROM SubCategory sc")
public class SubCategory extends BaseDomain<Long> implements Serializable{
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

	@JsonIgnoreProperties("subCategorySet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cat_id")//(cascade = CascadeType.ALL)
	private Category category;

	@JsonIgnoreProperties("subCategorySet")
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY,  mappedBy = "subCategory")
	private Set<Product> productSet;


	//Sample Code for setting new Date for CreateTime.
	@PrePersist protected void onCreatedtime() {
		Date date=new Date();
		createdTime = date;
		updatedTime = date;
	}




}