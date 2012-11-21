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
 * Klasa modelu reprezentująca indeks danej giełdy.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Entity
@Table(name = "stock_index")
public class StockIndex implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "stock_index_id")
	private Long id;

	@Column(columnDefinition = "Decimal(15,2)")
	private Double price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_add", nullable = false)
	private Date dateOfAdd;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_market_id")
	@JsonIgnore
	private StockMarket stockMarket;

	public StockIndex() {
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

	public StockMarket getStockMarket() {
		return stockMarket;
	}

	public void setStockMarket(StockMarket stockMarket) {
		this.stockMarket = stockMarket;
	}

}
