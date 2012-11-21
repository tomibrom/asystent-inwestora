package com.agsupport.parser.index;

import java.util.Date;

import org.jsoup.nodes.Element;

import com.agsupport.core.jpa.model.StockIndex;

public class NasdaqParser extends IndexParser {
	public NasdaqParser() {
		super("http://www.nasdaq.com/markets/indices/sector-indices.aspx");
	}
	
	/*public NasdaqParser(String url) {
		super(url);
		setStockMarketName("NASDAQ");
	}*/
	
	@Override
	protected void getAllIndexesRows() {	
		Element tbody = getDataContainerById("OtherIndicesTable").child(1);
		for(Element tr : tbody.children()) {
			this.indexes.add(tr);
			this.stockMarketNames.add(tr.child(0).getElementsByTag("h3").first().text().trim());
		}
	}

	@Override
	protected StockIndex getIndex(Element index) {
		StockIndex stockindex = new StockIndex();
			
		stockindex.setPrice(parsePrice(index.child(2).text()));
		stockindex.setDateOfAdd(new Date());
		
		return stockindex;
	}

	private Double parsePrice(String str) {
		str = str.replaceAll(",", "");
		
		return Double.parseDouble(str);
	}
}
