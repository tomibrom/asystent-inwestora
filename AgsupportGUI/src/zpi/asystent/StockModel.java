package zpi.asystent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.agsupport.core.service.communication.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class StockModel {
	
	ListOfStockMarket markets;
	ArrayList<Observer> observers;
	WebResource wr1;
	
	void registerObserver(Observer o)
	{
		observers.add(o);
	}
	
	void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	void notifyObservers(ListOfStockMarket markets) {
		for(Observer o : observers) {
			o.setStockList(markets);
		}
	}
	
	void notifyObservers(ListOfStockIndex indexes) {
		for(Observer o : observers) {
			o.updateStockIndexes(indexes);
		}
	}
	
	private void notifyObservers(ListOfDerivative derivatives) {
		for(Observer o : observers) {
			o.updateDerivativeList(derivatives);
		}		
	}
	
	private void notifyObservers(ListOfDate expiredDates) {
		for(Observer o : observers) {
			o.updateExpiredDateList(expiredDates);
		}		
	}
	
	private void notifyObservers(ListOfDerivativeValue DerivativeValues) {
		for(Observer o : observers) {
			o.updateDerivativeValues(DerivativeValues);
		}
	}
	
	public void getStockIndexList(JSONStockMarket market, Date from, Date to) {
		
		ListOfStockIndex resp1 = wr1.path("getAllStockIndex").queryParam("stockMarketId", market.getId().toString())
				.queryParam("from", new Long(from.getTime()).toString() )
				.queryParam("to", new Long(to.getTime()).toString() )
				.accept("application/json").get(ListOfStockIndex.class);
			
		notifyObservers(resp1);
	}

	public StockModel() {
		
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client c = Client.create(cc);
		wr1 = c.resource("http://ec2-46-51-161-102.eu-west-1.compute.amazonaws.com/AgsupportWeb/service");
		
		observers = new ArrayList<Observer>();
	}

	public void getStockMarketList() {
		ListOfStockMarket resp1 = wr1.path("getStockMarketList")
				.accept("application/json").get(ListOfStockMarket.class);
		
		notifyObservers(resp1);		
	}

	public void getDerivativeList() {
		ListOfDerivative resp1 = wr1.path("getDerivativeList")
				.accept("application/json").get(ListOfDerivative.class);
		
		notifyObservers(resp1);
	}

	

	public void getDerivativeExpiredDate(JSONDerivative derivative) {
		ListOfDate resp1 = wr1.path("getExpiredDateList")
				.queryParam("derivativeId", derivative.getId().toString())
				.accept("application/json").get(ListOfDate.class);
		
		List<Date> strDateList = new ArrayList<Date>();
		
		for( Date d : resp1.getDates() ) {
			StringDate sd = new StringDate(d);
			strDateList.add(sd);
		}
		
		resp1.setDates(strDateList);
		
		notifyObservers(resp1);
	}

	public void getDerivativeValuesList(JSONDerivative derivative,
			Date fromDate, Date toDate, Date expiredDate) {
		
		ListOfDerivativeValue resp1 = wr1.path("getDerivativeValuesForRangeAndExpireDate")
				.queryParam("from", new Long(fromDate.getTime()).toString())
				.queryParam("to", new Long(toDate.getTime()).toString())
				.queryParam("expierdDate", new Long(expiredDate.getTime()).toString())
				.queryParam("derivativeId", derivative.getId().toString())
				.accept("application/json").get(ListOfDerivativeValue.class);
		
		notifyObservers(resp1);
		
	}	
}
