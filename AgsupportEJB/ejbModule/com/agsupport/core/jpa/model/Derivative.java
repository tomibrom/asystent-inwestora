package com.agsupport.core.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Klasa modelu reprezentująca instrument pochodny giełdy.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Entity
@Table(name = "derivative")
public class Derivative implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "derivative_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_of_add", nullable = false)
	private Date dateOfAdd;

	@OneToMany(mappedBy = "derivative", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DerivativeValue> derivativeValues = new LinkedList<DerivativeValue>();

	public Derivative() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfAdd() {
		return dateOfAdd;
	}

	public void setDateOfAdd(Date dateOfAdd) {
		this.dateOfAdd = dateOfAdd;
	}

	public List<DerivativeValue> getDerivativeValues() {
		return derivativeValues;
	}

	public void setDerivativeValues(List<DerivativeValue> derivativeValues) {
		this.derivativeValues = derivativeValues;
	}

}
