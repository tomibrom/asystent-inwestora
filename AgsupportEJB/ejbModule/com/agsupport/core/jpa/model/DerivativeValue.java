package com.agsupport.core.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Klasa modelu reprezentująca wartość instrumentu pochodnego.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Entity
@Table(name = "derivative_value")
public class DerivativeValue implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "derivative_value_id")
	private Long id;

	@Column(columnDefinition = "Decimal(15,2)")
	private Double price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_add", nullable = false)
	private Date dateOfAdd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expired_date", nullable = false)
	private Date expiredDate;

	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name = "derivative_id")
	@JsonIgnore
	private Derivative derivative;

	public DerivativeValue() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDateOfAdd() {
		return dateOfAdd;
	}

	public void setDateOfAdd(Date dateOfAdd) {
		this.dateOfAdd = dateOfAdd;
	}

	public Derivative getDerivative() {
		return derivative;
	}

	public void setDerivative(Derivative derivative) {
		this.derivative = derivative;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

}
