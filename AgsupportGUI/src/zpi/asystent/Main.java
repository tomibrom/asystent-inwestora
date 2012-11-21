package zpi.asystent;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		StockModel stockModel = new StockModel();
		new StockController(stockModel);		
	}
}
