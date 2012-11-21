package com.agsupport.parser.factories;

import java.text.ParseException;
import java.util.LinkedList;

import com.agsupport.parser.derivative.DerivativeParser;
import com.agsupport.parser.derivative.WigDerivativeParser;

public class WigDerivativeHistoryFactory extends HistoryFactory {
	public static LinkedList<DerivativeParser> getParsers(String dataStart) {
		setPattern("yyyyMMdd");
		LinkedList<DerivativeParser> parsers = new LinkedList<DerivativeParser>();

		String beforDateUrl = "http://gielda.wp.pl/date,";
		String afterDateUrl = ",max,20121109,sort,a0,typ,kontraktyiji_kontrakty_indeksowe,notowania.html?ticaid=1f826&_ticrsn=5";
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
						parsers.add(new WigDerivativeParser(beforDateUrl + dayBefore +afterDateUrl, dayBefore));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				startDay = dayBefore;
			}
		

		return parsers;
	}
}
