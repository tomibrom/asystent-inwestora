package com.agsupport.parser.factories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class HistoryFactory {
	protected static String pattern;
	
	protected static void  setPattern(String _pattern) {
		pattern = _pattern;
	} 
	
	protected static String _getYesterday() {
		Calendar cal = Calendar.getInstance();
		
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		cal.add(Calendar.DATE, -1);
		
		return dateFormat.format(cal.getTime());
	}
	
	protected static String _getToday() {
		Calendar cal = Calendar.getInstance();
		
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		
		return 	dateFormat.format(cal.getTime());
	}
	
	protected static String _getDayBefore(String Date) {
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(Date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat(pattern);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		
		return dateFormat.format(cal.getTime());
	}
	
	protected static Boolean _isWeekend(String Date) {
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(Date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calc = Calendar.getInstance();
		
		calc.setTime(date);
		
		int dayOfWeek =  calc.get(Calendar.DAY_OF_WEEK);
		
		return (dayOfWeek == 7 || dayOfWeek == 1);
		
	}
}
