package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.Date;

/**
 * Klasa modelu reprezentująca wartość instrumentu pochodnego.
 * 
 * @author Michał Gruszczyński
 * 
 */

public class JSONDerivativeValue implements Serializable {

	private Long id;

	private Double price;

	private Date dateOfAdd;

	private Date expiredDate;

	public JSONDerivativeValue() {
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

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

}
