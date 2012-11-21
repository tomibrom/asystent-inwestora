package zpi.asystent;

import com.agsupport.core.service.communication.*;

public interface ControllerInterface {
	void getStockMarketList();
	void getDerivativeList();
	void getStockIndexList(JSONStockMarket market, String from, String to);
	void getDerivativeExpiredDate(JSONDerivative derivative);
	void getDerivativeValuesList(JSONDerivative derivative, String from, String to, String expiredDate);
}
