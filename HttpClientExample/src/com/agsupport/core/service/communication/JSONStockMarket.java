package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.Date;

/**
 * Klasa modelu reprezentująca giełdę.
 * 
 * @author Michał Gruszczyński
 * 
 */

public class JSONStockMarket implements Serializable {

	private Long id;

	private String name;

	private String abbreviatedName;

	private String description;

	private Date dateOfAdd;

	public JSONStockMarket() {
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

	public String getAbbreviatedName() {
		return abbreviatedName;
	}

	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
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

}
