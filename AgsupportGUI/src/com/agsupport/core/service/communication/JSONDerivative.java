package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.Date;

/**
 * Klasa modelu reprezentująca instrument pochodny giełdy.
 * 
 * @author Michał Gruszczyński
 * 
 */

public class JSONDerivative implements Serializable {

	private Long id;

	private String name;

	private String description;

	private Date dateOfAdd;

	public JSONDerivative() {
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
	
	public String toString() {
		return name;
	}

}
