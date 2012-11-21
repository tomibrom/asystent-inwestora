package com.agsupport.parser.index;

import java.util.Date;

import org.jsoup.nodes.Element;

import com.agsupport.core.jpa.model.StockIndex;

public class ForexprosIndexParser extends IndexParser {
	private String sectorSummaryPrefix = new String();

	public ForexprosIndexParser(String url) {
		super(url);
	}
	
	public ForexprosIndexParser(String url, String sectorSummaryPrefix) {
		super(url);
		this.sectorSummaryPrefix = sectorSummaryPrefix + " ";
	}

	@Override
	protected void getAllIndexesRows() {
		Element tbody = getDocument().getElementById("market_overview_default").child(1);
		
		for(Element tr : tbody.children()) {			
			indexes.add(tr);
			stockMarketNames.add(tr.child(0).text().trim());
		}

		tbody = getDocument().getElementById("market_overview_sectors").child(1);
		
		for(Element tr : tbody.children()) {			
			indexes.add(tr);
			stockMarketNames.add(sectorSummaryPrefix + tr.child(0).text().trim());
		}
	}

	@Override
	protected StockIndex getIndex(Element index) {
		StockIndex si = new StockIndex();
		
		Double price = Double.parseDouble(index.child(1).text());
		si.setPrice(price);
		
		si.setDateOfAdd(new Date());
		
		return si;
	}
}
