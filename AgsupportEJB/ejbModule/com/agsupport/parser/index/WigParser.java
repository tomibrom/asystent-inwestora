package com.agsupport.parser.index;

import java.util.Date;

import org.jsoup.nodes.Element;
import com.agsupport.core.jpa.model.StockIndex;

public class WigParser extends IndexParser {
	public WigParser() {
		super("http://gielda.wp.pl/indeksy.html");
	}
	
	/*public WigParser(String url) {
		super(url);
		setStockMarketName("WIG");
	}*/

	@Override
	protected void getAllIndexesRows() {
		Element tbody = this.getDataContainerById("gpwRyn").getElementsByTag("tbody").first();
		int counter = 0;
		
		for(Element tr : tbody.children()) {
			if(counter > 1 && counter < 11 && tr.text().contains("WIG")){
				this.indexes.add(tr);
				this.stockMarketNames.add(tr.child(0).text().trim());
			
			}
			
			counter++;
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
		str = str.replaceAll(" ", "");
		str = str.replaceAll(",", ".");
		
		return Double.parseDouble(str);
	}
}
