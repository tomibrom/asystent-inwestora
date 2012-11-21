package com.agsupport.parser.tests;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.agsupport.parser.factories.WigHistoryFactory;
import com.agsupport.parser.index.IndexParser;
import com.agsupport.parser.index.NasdaqParser;
import com.agsupport.parser.index.WigParser;

public class Test {
	static public void main(String args[]) {
		/*
		// Przyk³ad u¿ycia dla wartoœci bie¿¹cych
		WigDerivativeParser p3 = new WigDerivativeParser();
		
		System.out.println(p3.getStockIndexList().toString());
		
		// Przyk³ad u¿ycia dla danych historycznych - u¿ycie factory
		
		LinkedList<IndexParser> p = WigHistoryFactory.getParsers("20121102");
		for(IndexParser el : p){			
			System.out.println(el.getStockIndexList().toString());
		}
	
		LinkedList<DerivativeParser> p2 = WigDerivativeHistoryFactory.getParsers("20121102");
		for(DerivativeParser el : p2){			
			System.out.println(el.getStockIndexList().toString());
		}
		*/
		
		//CommodityOnlineParser p = new CommodityOnlineParser("http://www.commodityonline.com/commodities/bullion/gold.php", "Gold");
		
		//System.out.println(p.getResults().toString());
		
//		CommodityOnlineParser[] ps = CommodityOnlineFactory.getParsers();
//		
//		for(CommodityOnlineParser p : ps) {
//			System.out.println(p.getDerivativeValueList().toString());
//		}
		
//		ForexprosParser p = new ForexprosParser("http://www.forexpros.com/indices/us-30-futures-contracts", "US-30");
//		
//		System.out.println(p.getResults().toString());
		
//		ForexprosParser[] ps = ForexprosFactory.getParsers();
//		
//		for(ForexprosParser p : ps) {
//			System.out.println(p.getDerivativeValueList().toString());
//		}
		
//		List<DerivativeParser> parserList = new LinkedList<DerivativeParser>();
//		
//		parserList.addAll(Arrays.asList(CommodityOnlineFactory.getParsers()));
//		parserList.addAll(Arrays.asList(ForexprosFactory.getParsers()));
//		parserList.addAll(WigDerivativeHistoryFactory.getParsers("20121102"));
//		
//		for (DerivativeParser p : parserList) {
//			System.out.println(p.getDerivativeValueList().toString());
//		}
		
		List<IndexParser> parserList = new LinkedList<IndexParser>();
		
		/* tu wpisaæ datê */
		parserList.addAll(WigHistoryFactory.getParsers("20121102"));
		
		parserList.add(new NasdaqParser());
		parserList.add(new WigParser());
		
		for (IndexParser p : parserList) {
			System.out.println(p.getStockIndexList().toString());
		}
	}
}
