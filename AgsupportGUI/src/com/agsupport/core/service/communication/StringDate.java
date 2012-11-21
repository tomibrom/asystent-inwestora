package com.agsupport.core.service.communication;

import java.util.Date;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class StringDate extends Date {
	
	public StringDate(Date d) {
		this.setTime(d.getTime());
	}

	public String toString() {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return format.format(this.getTime());
	}

}
