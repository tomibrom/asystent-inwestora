package zpi.asystent;

import com.agsupport.core.service.communication.*;


public interface Observer {
	void setStockList(ListOfStockMarket stockMarkets);
	void updateStockIndexes(ListOfStockIndex indexes);
	void updateDerivativeList(ListOfDerivative derivatives);
	void updateExpiredDateList(ListOfDate expiredDates);
	void updateDerivativeValues(ListOfDerivativeValue derivativeValues);
}
