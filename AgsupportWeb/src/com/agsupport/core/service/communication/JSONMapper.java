package com.agsupport.core.service.communication;

import java.util.LinkedList;
import java.util.List;

import com.agsupport.core.jpa.model.Derivative;
import com.agsupport.core.jpa.model.DerivativeValue;
import com.agsupport.core.jpa.model.StockIndex;
import com.agsupport.core.jpa.model.StockMarket;

public class JSONMapper {

	public JSONDerivative mapJSONDerivative(Derivative derviative) {
		JSONDerivative jD = new JSONDerivative();
		jD.setDateOfAdd(derviative.getDateOfAdd());
		jD.setId(derviative.getId());
		jD.setDescription(derviative.getDescription());
		jD.setName(derviative.getName());
		return jD;
	}

	public JSONDerivativeValue mapJSONDerivativeValue(
			DerivativeValue derviativeValue) {
		JSONDerivativeValue jDV = new JSONDerivativeValue();
		jDV.setDateOfAdd(derviativeValue.getDateOfAdd());
		jDV.setId(derviativeValue.getId());
		jDV.setPrice(derviativeValue.getPrice());
		jDV.setExpiredDate(derviativeValue.getExpiredDate());
		return jDV;
	}

	public JSONStockMarket mapJSONStockMarket(StockMarket stockMarket) {
		JSONStockMarket jSM = new JSONStockMarket();
		jSM.setAbbreviatedName(stockMarket.getAbbreviatedName());
		jSM.setName(stockMarket.getName());
		jSM.setId(stockMarket.getId());
		jSM.setDateOfAdd(stockMarket.getDateOfAdd());
		jSM.setDescription(stockMarket.getDescription());
		return jSM;
	}

	public JSONStockIndex mapJSONStockIndex(StockIndex stockIndex) {
		JSONStockIndex jSI = new JSONStockIndex();
		jSI.setId(stockIndex.getId());
		jSI.setDateOfAdd(stockIndex.getDateOfAdd());
		jSI.setPrice(stockIndex.getPrice());
		return jSI;
	}

	public ListOfStockIndex mapJSONStockIndexList(
			List<StockIndex> stockIndexList) {
		ListOfStockIndex list = new ListOfStockIndex();
		List<JSONStockIndex> result = new LinkedList<JSONStockIndex>();
		for (StockIndex si : stockIndexList) {
			result.add(mapJSONStockIndex(si));
		}
		list.setStockIndexes(result);
		return list;
	}

	public ListOfDerivative mapJSONDerivativeList(
			List<Derivative> derivativeList) {
		ListOfDerivative list = new ListOfDerivative();
		List<JSONDerivative> result = new LinkedList<JSONDerivative>();
		for (Derivative d : derivativeList) {
			result.add(mapJSONDerivative(d));
		}
		list.setDerivatives(result);
		return list;
	}

	public ListOfDerivativeValue mapJSONStockDerivativeValueList(
			List<DerivativeValue> derivativeValueList) {
		ListOfDerivativeValue list = new ListOfDerivativeValue();
		List<JSONDerivativeValue> result = new LinkedList<JSONDerivativeValue>();
		for (DerivativeValue dV : derivativeValueList) {
			result.add(mapJSONDerivativeValue(dV));
		}
		list.setDerivativeValues(result);
		return list;
	}

	public ListOfStockMarket mapJSONStockMarketList(
			List<StockMarket> stockMarketList) {
		ListOfStockMarket list = new ListOfStockMarket();
		List<JSONStockMarket> result = new LinkedList<JSONStockMarket>();
		for (StockMarket sm : stockMarketList) {
			result.add(mapJSONStockMarket(sm));
		}
		list.setStockMarkets(result);
		return list;
	}
}
