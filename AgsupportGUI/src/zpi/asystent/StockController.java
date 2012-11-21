package zpi.asystent;

import java.awt.EventQueue;
import java.text.ParseException;
import java.util.Date;

import com.agsupport.core.service.communication.JSONDerivative;
import com.agsupport.core.service.communication.JSONStockMarket;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class StockController implements ControllerInterface {
	
	MainFrame view;
	StockModel model;

	@Override
	public void getStockMarketList() {
		model.getStockMarketList();
	}

	public StockController(StockModel model) {
		this.model = model;
				
		view = new MainFrame(this);
		view.setVisible(true);
		
		model.registerObserver(view);
	}

	@Override
	public void getStockIndexList(JSONStockMarket market, String from, String to) {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			Date fromDate = format.parse(from);
			Date toDate = format.parse(to);
			model.getStockIndexList(market, fromDate, toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void getDerivativeList() {
		model.getDerivativeList();		
	}

	@Override
	public void getDerivativeExpiredDate(JSONDerivative derivative) {
		model.getDerivativeExpiredDate(derivative);
		
	}

	@Override
	public void getDerivativeValuesList(JSONDerivative derivative, String from,
			String to, String expired) {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			Date fromDate = format.parse(from);
			Date toDate = format.parse(to);
			Date expiredDate = format.parse(expired);
			model.getDerivativeValuesList(derivative, fromDate, toDate, expiredDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
