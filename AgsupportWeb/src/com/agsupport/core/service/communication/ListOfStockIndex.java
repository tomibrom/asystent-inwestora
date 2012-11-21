package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.List;

import com.agsupport.core.jpa.model.StockIndex;

public class ListOfStockIndex implements Serializable {

	private List<JSONStockIndex> stockIndexes;

	public ListOfStockIndex() {
	}

	public ListOfStockIndex(List<JSONStockIndex> stockIndexes) {
		this.stockIndexes = stockIndexes;
	}

	public List<JSONStockIndex> getStockIndexes() {
		return stockIndexes;
	}

	public void setStockIndexes(List<JSONStockIndex> stockIndexes) {
		this.stockIndexes = stockIndexes;
	}

}
