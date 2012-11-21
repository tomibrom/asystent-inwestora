package com.agsupport.parser.factories;

import com.agsupport.parser.derivative.CommodityOnlineParser;

public class CommodityOnlineFactory {
	private static CommodityOnlineParser[] parsers = new CommodityOnlineParser[] {
		new CommodityOnlineParser("http://www.commodityonline.com/commodities/bullion/gold.php", "Gold"),
		new CommodityOnlineParser("http://www.commodityonline.com/commodities/metals/aluminum.php", "Aluminium"), /* To je amelinium! */
		new CommodityOnlineParser("http://www.commodityonline.com/commodities/oil-oilseeds/casteroil.php", "Caster oil")
	};
	
	public static CommodityOnlineParser[] getParsers() {
		return parsers; 
	}
}
