package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.List;

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
