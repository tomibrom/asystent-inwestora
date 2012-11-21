/**
 * 
 */
package com.agsupport.parser.index;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Element;

import com.agsupport.core.jpa.model.StockIndex;

public class WigHistoryParser extends IndexParser {
	private Date _createDate;
	
	private static final Set<String> IndexNamesArray = new HashSet<String>(Arrays.asList(
		     new String[] {"mWIG40", "sWIG80", "WIG", "WIG20", "WIG-CEE", "WIGdiv"}
		));
	
	public WigHistoryParser(String url, String Date) throws ParseException {
		super(url);
		this._createDate = new SimpleDateFormat("yyyyMMdd").parse(Date);
		setIsForHistory(true);
	}

	@Override
	protected void getAllIndexesRows() {
		Element tbody = this.getDocument().getElementsByClass("tab_fld").first().getElementsByTag("tbody").first();
		for(Element tr : tbody.children()) {
			if(IndexNamesArray.contains(tr.getElementsByClass("name").text())) {
				this.indexes.add(tr);
				this.stockMarketNames.add(tr.child(1).text().trim());
			}
		}
	}

	@Override
	protected StockIndex getIndex(Element index) {
			StockIndex stockindex = new StockIndex();
						
			/* value */
			stockindex.setPrice(parsePrice(index.child(2).text()));
			
			/* date */
			stockindex.setDateOfAdd(this._createDate);
			
			return stockindex;
	}

	private Double parsePrice(String str) {
		str = str.replaceAll(" ", "");
		str = str.replaceAll(",", ".");
		
		return Double.parseDouble(str);
	}
}
