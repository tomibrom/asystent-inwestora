package com.agsupport.parser.derivative;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.agsupport.core.jpa.model.DerivativeValue;

public abstract class DerivativeParser {
	private Document _document = null;
	private String _url = null;
	private LinkedList<DerivativeValue> _stockValuesList = new LinkedList<DerivativeValue>();
	
	protected LinkedList<Element> indexes = new LinkedList<Element>();
	protected String stockMarketName; 
	protected Boolean isForHistory = false;
	
	public DerivativeParser(String url) {
		this._url = url;
	}
	
	public LinkedList<DerivativeValue> getResults() {
		try {
			this.getAllValuesRows();
			this.parse();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("nie ma danych");
		}
		
		return this._stockValuesList;
	}
	
	protected Element getDataContainerById(String Id){
		return  getDocument().getElementById(Id);
	}
	
	protected Document getDocument() {
		if(_document != null) {
			return _document;
		}
		
		Connection connection = Jsoup.connect(_url);
		
		try {
			_document = connection.post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return _document;
	}
	
	private void parse() {
		for(Element el : this.indexes) {
			DerivativeValue indexToAdd = getValue(el);
			this._stockValuesList.add(indexToAdd);
		}
	}
	
	public String getStockMarketName() {
		return stockMarketName;
	}

	public void setStockMarketName(String stockMarketName) {
		this.stockMarketName = stockMarketName;
	}
	
	public Map<String, LinkedList<DerivativeValue>> getDerivativeValueList() {	
		Map<String, LinkedList<DerivativeValue>> map = new HashMap<String, LinkedList<DerivativeValue>>();
		
		if(_stockValuesList.isEmpty()) {
			getResults();
		}
		
		map.put(getStockMarketName(), _stockValuesList);
		
		return map;
	}
	
	public Boolean getIsForHistory() {
		return isForHistory;
	}

	public void setIsForHistory(Boolean isForHistory) {
		this.isForHistory = isForHistory;
	}
	
	protected abstract void getAllValuesRows();
	protected abstract DerivativeValue getValue(Element index);
}
