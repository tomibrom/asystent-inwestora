package com.agsupport.parser.factories;

import com.agsupport.parser.derivative.ForexprosParser;

public class ForexprosFactory {
	private static ForexprosParser[] parsers = new ForexprosParser[] {
		new ForexprosParser("http://www.forexpros.com/indices/us-30-futures-contracts", "US 30"),
		new ForexprosParser("http://www.forexpros.com/indices/us-spx-500-futures-contracts", "S&P 500"),
		new ForexprosParser("http://www.forexpros.com/indices/nq-100-futures-contracts", "NQ 100"),
		new ForexprosParser("http://www.forexpros.com/indices/smallcap-2000-futures-contracts", "US Small Cap 2000"),
		new ForexprosParser("http://www.forexpros.com/indices/us-spx-vix-futures-contracts", "US SPX VIX"),
		new ForexprosParser("http://www.forexpros.com/indices/germany-30-futures-contracts", "DAX"),
		new ForexprosParser("http://www.forexpros.com/indices/uk-100-futures-contracts", "FTSE 100"),
		new ForexprosParser("http://www.forexpros.com/indices/eu-stocks-50-futures-contracts", "EU Stoxx 50"),
		new ForexprosParser("http://www.forexpros.com/indices/switzerland-20-futures-contracts", "Switzerland 20"),
		new ForexprosParser("http://www.forexpros.com/indices/germany-mid-cap-50-futures-contracts", "Germany Mid-Cap 50"),
		new ForexprosParser("http://www.forexpros.com/indices/germany-tech-30-contracts", "Germany Tech 30"),
		new ForexprosParser("http://www.forexpros.com/indices/e-mini-s-p-midcap-futures", "E-Mini S&P Midcap"),
		new ForexprosParser("http://www.forexpros.com/indices/s-p-500-index-futures", "S&P 500 Index"),
		new ForexprosParser("http://www.forexpros.com/indices/nasdaq-100-futures", "Nasdaq 100"),
		new ForexprosParser("http://www.forexpros.com/indices/djia-futures", "DJIA"),
		new ForexprosParser("http://www.forexpros.com/indices/djia-%28e%29-futures", "DJIA (E)"),
		new ForexprosParser("http://www.forexpros.com/indices/e-mini-s-p-smallcap-futures", "E-Mini S&P Smallcap"),
		new ForexprosParser("http://www.forexpros.com/indices/crb-cci-index-futures", "CRB CCI Index"),
		new ForexprosParser("http://www.forexpros.com/indices/us-spx-vix-futures-contracts", "CBOE S&P 500 VIX"),
		new ForexprosParser("http://www.forexpros.com/indices/global-titans-50-futures", "Global Titans 50"),
		new ForexprosParser("http://www.forexpros.com/indices/swiss-midcap-index-futures", "Swiss Midcap Index"),
		new ForexprosParser("http://www.forexpros.com/indices/swiss-leader-index-futures", "Swiss Leader Index"),
		new ForexprosParser("http://www.forexpros.com/indices/finnish-25-index-futures", "Finnish 25 Index"),
		new ForexprosParser("http://www.forexpros.com/indices/stoxx-banks-600-futures", "Stoxx Banks 600"),
		new ForexprosParser("http://www.forexpros.com/indices/euro-stoxx-banks-600-futures", "Euro Stoxx Banks 600"),
		new ForexprosParser("http://www.forexpros.com/indices/stoxx-mid-200-futures", "Stoxx Mid 200"),
		new ForexprosParser("http://www.forexpros.com/indices/banks-titans-30-futures", "Banks Titans 30"),
		new ForexprosParser("http://www.forexpros.com/indices/oil---gas-titans-30-futures", "Oil & Gas Titans 30"),
		new ForexprosParser("http://www.forexpros.com/indices/insurance-titans-30-futures", "Insurance Titans 30"),
		new ForexprosParser("http://www.forexpros.com/indices/telecomm-titans-30-futures", "Telecomm Titans 30")
	};
	
	public static ForexprosParser[] getParsers() {
		return parsers; 
	}
}
