package com.agsupport.parser.factories;

import com.agsupport.parser.index.ForexprosIndexParser;

public class ForexprosIndexFactory {
	private static ForexprosIndexParser[] parsers = new ForexprosIndexParser[] {
		/* Europe */
		new ForexprosIndexParser("http://www.forexpros.com/markets/denmark"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/russia"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/turkey"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/belgium"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/iceland"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/united-kingdom"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/france"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/portugal"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/sweden"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/germany"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/netherlands"),
		/* Americas */
		new ForexprosIndexParser("http://www.forexpros.com/markets/mexico"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/brazil"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/united-states"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/canada"),
		/* Asia/Pacific */
		new ForexprosIndexParser("http://www.forexpros.com/markets/australia"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/india"),
		/* Middle East */
		new ForexprosIndexParser("http://www.forexpros.com/markets/bahrain", "Bahrain"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/kuwait", "Kuwait"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/qatar", "Qatar"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/saudi-arabia", "Saudi-Arabia"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/israel"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/oman"),
		new ForexprosIndexParser("http://www.forexpros.com/markets/jordan", "Jordan"),		
	};
	
	public static ForexprosIndexParser[] getParsers() {
		return parsers; 
	}
}
