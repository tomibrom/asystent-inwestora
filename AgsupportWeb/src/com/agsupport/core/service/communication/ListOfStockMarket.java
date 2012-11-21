package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.List;

import com.agsupport.core.jpa.model.StockMarket;

public class ListOfStockMarket implements Serializable {

	private List<JSONStockMarket> stockMarkets;

	public ListOfStockMarket() {
	}

	public ListOfStockMarket(List<JSONStockMarket> stockMarkets) {
		this.stockMarkets = stockMarkets;
	}

	public List<JSONStockMarket> getStockMarkets() {
		return stockMarkets;
	}

	public void setStockMarkets(List<JSONStockMarket> stockMarkets) {
		this.stockMarkets = stockMarkets;
	}

}
