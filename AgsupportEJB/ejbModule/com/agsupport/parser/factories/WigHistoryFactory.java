package com.agsupport.parser.factories;

import java.text.ParseException;
import java.util.LinkedList;

import com.agsupport.parser.index.IndexParser;
import com.agsupport.parser.index.WigHistoryParser;


public class WigHistoryFactory extends HistoryFactory{
	
	public static LinkedList<IndexParser> getParsers(String dataStart) {
		setPattern("yyyyMMdd");
		LinkedList<IndexParser> parsers = new LinkedList<IndexParser>();

		String beforDateUrl = "http://gielda.wp.pl/date,";
		String afterDateUrl = ",max,20121109,sort,a0,typ,indeksy,notowania.html?ticaid=1f811&_ticrsn=5";
		String startDay;
		String dayBefore;
		String today = _getToday();
		
		startDay = today;
		
		while(_isWeekend( _getDayBefore(startDay))){
			startDay =  _getDayBefore(startDay);
		}

		while (! _getDayBefore(startDay).equals(dataStart)){
			
				dayBefore =  _getDayBefore(startDay);
				
				if(!_isWeekend(dayBefore))
					try {
						parsers.add(new WigHistoryParser(beforDateUrl + dayBefore +afterDateUrl, dayBefore));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				startDay = dayBefore;
			}
		

		return parsers;
	}
	

}