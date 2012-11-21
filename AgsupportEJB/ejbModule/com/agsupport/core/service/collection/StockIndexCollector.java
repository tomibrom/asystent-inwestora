package com.agsupport.core.service.collection;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timeout;

import org.jboss.logging.Logger;

import com.agsupport.core.jpa.facade.StockIndexFacade;
import com.agsupport.core.jpa.facade.StockMarketFacade;
import com.agsupport.core.jpa.model.StockIndex;
import com.agsupport.core.jpa.model.StockMarket;
import com.agsupport.parser.index.IndexParser;
import com.agsupport.parser.factories.WigHistoryFactory;
import com.agsupport.parser.index.IndexParser;
import com.agsupport.parser.index.NasdaqParser;
import com.agsupport.parser.index.WigParser;

/**
 * Klasa odpowiedzialna za systematyczne pobieranie wartosci indeksów dla
 * światowych giełd.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Stateless
public class StockIndexCollector {

	private Logger logger = Logger.getLogger(StockIndexCollector.class);

	@EJB
	private StockMarketFacade stockMarketFacade;
	@EJB
	private StockIndexFacade stockIndexFacade;

	@PostConstruct
	public void init() {
		logger.info("StockIndexCollector.init START");

		if (stockMarketFacade.getStockMarketByAbbreviatedName("WIG20") == null) {
			logger.info("History for index WIG20 NOT EXISTS");
			logger.info("History for index WIG20 START");
			List<IndexParser> parserList = new LinkedList<IndexParser>();

			// tu wpisać datę
			parserList.addAll(WigHistoryFactory.getParsers("20121102"));

			for (IndexParser p : parserList) {

				if (p.getIsForHistory()) {
					logger.info("StockIndex - History PARSER");

					logger.info("History for index WIG20 NOT EXISTS");
					Map<String, StockIndex> map = p.getStockIndexList();
					for (Map.Entry<String, StockIndex> e : map.entrySet()) {
						logger.info("History PARSER for - " + e.getKey());
						// MAPA String - nazwa giełdy
						// StockIndex ma mieć wypełnioną DATĘ!
						String stockMarketName = e.getKey();
						StockIndex stockIndex = e.getValue();
						addStockIndex(stockMarketName, stockIndex);
					}
					continue;
				}
			}
			logger.info("History for index WIG20 END");
		}
		logger.info("StockIndexCollector.init END");

	}

	/**
	 * Metoda Timer Service. Wywoływana co 30 minut. Sekwencyjnie wywołuje
	 * parsery różnych stron z których pobierane są dane na temat giełd oraz
	 * wartości indeksów.
	 * 
	 */
	@Schedule(persistent = false, second = "0", minute = "*/10", hour = "*")
	public void collect() {
		logger.info("StockIndexCollector.collect START");
		List<IndexParser> parserList = new LinkedList<IndexParser>();

		/* tu wpisać datę */

		parserList.add(new NasdaqParser());
		parserList.add(new WigParser());

		/*
		 * UWAGA od doba: parsery nie mają wypełnionego pola StockMarket nie
		 * wiem, czy któraś z metod ma już to zaimplementowane
		 */

		for (IndexParser p : parserList) {

			if (p.getIsForHistory()) {
				logger.info("StockIndex - History PARSER");

				// JEŚLI istnieje WIG20 to znaczy że historia już istnieje.
				// TO ZNACZY ŻE HISTORIA JUŻ JEST ZACIĄGNIĘTA

				if (stockMarketFacade.getStockMarketByAbbreviatedName("WIG20") != null) {
					logger.info("History for index WIG20 EXISTS");
					continue;
				}
				logger.info("History for index WIG20 NOT EXISTS");
				Map<String, StockIndex> map = p.getStockIndexList();
				for (Map.Entry<String, StockIndex> e : map.entrySet()) {
					logger.info("History PARSER for - " + e.getKey());
					// MAPA String - nazwa giełdy
					// StockIndex ma mieć wypełnioną DATĘ!
					String stockMarketName = e.getKey();
					StockIndex stockIndex = e.getValue();
					addStockIndex(stockMarketName, stockIndex);
				}
				continue;
			}

			/*
			 * data dodania zbioru wartości indeksów do bazy dla danego parsera
			 * data jest również wykorzystana gdyby okazało się że stockMarket
			 * nie istnieje jeszcze w bazie
			 */
			Date dateOfAdd = new Date();

			Map<String, StockIndex> map = p.getStockIndexList();
			for (Map.Entry<String, StockIndex> e : map.entrySet()) {
				String stockMarketName = e.getKey();
				StockIndex stockIndex = e.getValue();
				stockIndex.setDateOfAdd(dateOfAdd);
				addStockIndex(stockMarketName, stockIndex);
			}
		}
		logger.info("StockIndexCollector.collect END");
	}

	/**
	 * Dodanie wartości indeksu dla danej giełdy
	 * 
	 * @param nameOfStockMarket
	 *            nazwa giełdy
	 * @param stockIndex
	 *            wartość indeksu giełdy
	 * @return
	 */
	private boolean addStockIndex(String nameOfStockMarket,
			StockIndex stockIndex) {
		logger.info("StockIndexCollector.createStockMarket - addStockIndex");
		StockMarket stockMarket = null;

		stockMarket = stockMarketFacade
				.getStockMarketByAbbreviatedName(nameOfStockMarket);

		if (stockMarket == null) {
			// próba dodatnia giełdy do bazy

			stockMarket = createStockMarket(nameOfStockMarket);

			if (stockMarket == null) {
				// Giełda nadal nie istnieje - brak integralności bazy.
				logger.info("StockIndexCollector.addStockIndex - stockMarket = null / NOT CREATED");
				logger.info("StockIndexCollector.addStockIndex NOT DONE!");
				return false;
			}
		}

		logger.info("StockIndexCollector.addStockIndex ");

		if (stockMarketFacade.addStockIndex(stockMarket.getId(), stockIndex) == true) {
			logger.info("Dodano StockIndex do giełdy o id = "
					+ stockMarket.getId());
			return true;
		} else {
			logger.info("NIE dodano StockIndex do giełdy o id = "
					+ stockMarket.getId());
			logger.info("Możliwy powód - zduplikowanie wartości!");
			return false;
		}
	}

	/**
	 * Dodanie nowej giełdy do bazy danych i natychmiastowe jej pobranie
	 * 
	 * @param nameOfStockMarket
	 *            nazwa skrócona giełdy
	 * @return
	 */
	private StockMarket createStockMarket(String nameOfStockMarket) {
		// Gdy nie istnieje dana giełda zostaje autmatycznie dodana do bazy
		logger.info("StockIndexCollector.createStockMarket - stockMarket = null");
		StockMarket stockMarket = new StockMarket();
		stockMarket.setAbbreviatedName(nameOfStockMarket);
		stockMarket.setDateOfAdd(new Date());
		stockMarketFacade.createStockMarket(stockMarket);
		stockMarket = stockMarketFacade
				.getStockMarketByAbbreviatedName(nameOfStockMarket);
		return stockMarket;
	}

	@Timeout
	public void timeout() {
		logger.info("Invoke.timeout STOCKindexCOLLECTOR");
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public StockMarketFacade getStockMarketFacade() {
		return stockMarketFacade;
	}

	public void setStockMarketFacade(StockMarketFacade stockMarketFacade) {
		this.stockMarketFacade = stockMarketFacade;
	}

	public StockIndexFacade getStockIndexFacade() {
		return stockIndexFacade;
	}

	public void setStockIndexFacade(StockIndexFacade stockIndexFacade) {
		this.stockIndexFacade = stockIndexFacade;
	}

}
