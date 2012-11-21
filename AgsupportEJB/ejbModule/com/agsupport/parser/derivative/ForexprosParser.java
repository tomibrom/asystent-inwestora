package com.agsupport.parser.derivative;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.nodes.Element;

import com.agsupport.core.jpa.model.DerivativeValue;

public class ForexprosParser extends DerivativeParser {

	public ForexprosParser(String url, String name) {
		super(url);
		setStockMarketName(name);
	}

	@Override
	protected void getAllValuesRows() {
		Element thead = getDocument().getElementById("BarchartDataTable").child(1);
		
		for(Element tr : thead.children()) {
			if(tr.child(1).text().contains("Cash")) {
				continue;
			}
			
			indexes.add(tr);
		}
	}

	@Override
	protected DerivativeValue getValue(Element index) {
		DerivativeValue derValue = new DerivativeValue();
		
		derValue.setDateOfAdd(new Date());
		derValue.setPrice(parsePrice(index.child(2).text()));
		
		try {
			derValue.setExpiredDate(parseDate(index.child(1).text()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return derValue;
	}
	
	private Date parseDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM yy", Locale.ENGLISH);
		
		date = date.replaceAll("\u00a0", " ");
		date = date.replaceAll("\\'", "");
		
		return sdf.parse(date.trim());
	}

	private Double parsePrice(String price) {		
		price = price.replaceAll("[^0-9\\.]*", "");
		
		return Double.parseDouble(price);
	}
}
