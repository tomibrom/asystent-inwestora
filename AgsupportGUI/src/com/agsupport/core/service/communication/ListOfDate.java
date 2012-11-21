package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ListOfDate implements Serializable {

	private List<Date> dates;

	public ListOfDate() {
	}

	public ListOfDate(List<Date> dates) {
		this.dates = dates;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

}
