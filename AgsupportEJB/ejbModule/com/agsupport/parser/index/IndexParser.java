package com.agsupport.parser.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.agsupport.core.jpa.model.StockIndex;

public abstract class IndexParser {
	private Document _document = null;
	private String _url = null;
	private LinkedList<StockIndex> _stockIndexesList = new LinkedList<StockIndex>();
	private Map<String, StockIndex> _map = new HashMap<String, StockIndex>();
	protected Boolean isForHistory = false;
	
	protected LinkedList<Element> indexes = new LinkedList<Element>();
	protected LinkedList<String> stockMarketNames = new LinkedList<String>(); 
	
	public IndexParser(String url) {
		this._url = url;
	}
	
	public LinkedList<StockIndex> getResults() {
		try {
			this.getAllIndexesRows();
			this.parse();
		}
		catch(Exception e) {
			System.out.println("nie ma danych");
		}
		
		return this._stockIndexesList;
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
			StockIndex indexToAdd = getIndex(el);
			this._stockIndexesList.add(indexToAdd);
		}
	}
	
	public Map<String, StockIndex> getStockIndexList() {
		/* don't blame me, it's all because of Radoslaw's ridiculous fantasy ;-) */
		if(_stockIndexesList.isEmpty()) {
			getResults();
		}
		
		int i = 0;
		for(StockIndex si : _stockIndexesList) {
			_map.put(stockMarketNames.get(i), si);
			i++;
		}
		
		return _map;
	}
	
	public Boolean getIsForHistory() {
		return isForHistory;
	}

	public void setIsForHistory(Boolean isForHistory) {
		this.isForHistory = isForHistory;
	}
	
	protected abstract void getAllIndexesRows();
	protected abstract StockIndex getIndex(Element index);
}
