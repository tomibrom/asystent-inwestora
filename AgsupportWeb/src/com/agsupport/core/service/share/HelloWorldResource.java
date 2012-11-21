package com.agsupport.core.service.share;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.agsupport.core.jpa.facade.DerivativeFacade;
import com.agsupport.core.jpa.facade.DerivativeValueFacade;
import com.agsupport.core.jpa.facade.StockIndexFacade;
import com.agsupport.core.jpa.facade.StockMarketFacade;
import com.agsupport.core.jpa.model.Derivative;
import com.agsupport.core.jpa.model.StockMarket;
import com.agsupport.core.service.communication.JSONDerivative;
import com.agsupport.core.service.communication.JSONMapper;
import com.agsupport.core.service.communication.JSONStockMarket;
import com.agsupport.core.service.communication.ListOfDate;
import com.agsupport.core.service.communication.ListOfDerivative;
import com.agsupport.core.service.communication.ListOfDerivativeValue;
import com.agsupport.core.service.communication.ListOfStockIndex;
import com.agsupport.core.service.communication.ListOfStockMarket;

@Stateless
@Path("/service")
public class HelloWorldResource {

	private Logger logger = Logger.getLogger("HelloWorldResource");

	@EJB
	private DerivativeFacade derivativeFacade;

	@EJB
	private DerivativeValueFacade derivativeValueFacade;

	@EJB
	private StockIndexFacade stockIndexFacade;

	@EJB
	private StockMarketFacade stockMarketFacade;

	@GET
	@Produces("application/json")
	@Path("getDerivativeList")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ListOfDerivative getDerivativeList() {
		logger.info("HelloWorldResource.getDerivativeList - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONDerivativeList(derivativeFacade
				.getDerivativeList());
	}

	@GET
	@Produces("application/json")
	@Path("getDerivativeById")
	public JSONDerivative getDerivativeById(
			@QueryParam("derivativeId") long derivativeId) {
		logger.info("HelloWorldResource.getDerivativeById - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONDerivative(derivativeFacade
				.getDerivativeById(derivativeId));
	}

	@GET
	@Produces("application/json")
	@Path("getDerivativeByName")
	public JSONDerivative getDerivativeByName(@QueryParam("name") String name) {
		logger.info("HelloWorldResource.getDerivativeByName - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONDerivative(derivativeFacade
				.getDerivativeByName(name));
	}

	@GET
	@Produces("application/json")
	@Path("deleteDerivativeValue")
	public Boolean deleteDerivativeValue(
			@QueryParam("derivativeValueId") long derivativeValueId) {
		logger.info("HelloWorldResource.deleteDerivativeValue - invoked");
		return derivativeFacade.deleteDerivativeValue(derivativeValueId);
	}

	@GET
	@Produces("application/json")
	@Path("updateDerivative")
	public Boolean updateDerivative(@QueryParam("dateOfAdd") long dateOfAdd,
			@QueryParam("description") String description,
			@QueryParam("name") String name, @QueryParam("id") long id) {
		logger.info("HelloWorldResource.updateDerivative - invoked");
		Derivative derivative = new Derivative();
		derivative.setDateOfAdd(new Date(dateOfAdd));
		derivative.setDescription(description);
		derivative.setName(name);
		derivative.setId(id);
		return derivativeFacade.updateDerivative(derivative);
	}

	@GET
	@Produces("application/json")
	@Path("getDerivativeValuesByDateOfAdd")
	public ListOfDerivativeValue getDerivativeValuesByDateOfAdd(
			@QueryParam("dateOfAdd") long dateOfAdd,
			@QueryParam("derivativeId") long derivativeId) {
		logger.info("HelloWorldResource.getDerivativeValuesByDateOfAdd - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockDerivativeValueList(derivativeValueFacade
				.getDerivativeValuesByDateOfAdd(new Date(dateOfAdd),
						derivativeId));
	}

	@GET
	@Produces("application/json")
	@Path("getDerivativeValuesForRangeAndExpireDate")
	public ListOfDerivativeValue getDerivativeValuesForRangeAndExpireDate(
			@QueryParam("from") long from, @QueryParam("to") long to,
			@QueryParam("expierdDate") long expierdDate,
			@QueryParam("derivativeId") long derivativeId) {
		logger.info("HelloWorldResource.getDerivativeValuesForRangeAndExpireDate - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockDerivativeValueList(derivativeValueFacade
				.getDerivativeValuesForRangeAndExpireDate(new Date(from),
						new Date(to), new Date(expierdDate), derivativeId));
	}

	@GET
	@Produces("application/json")
	@Path("getExpiredDateList")
	public ListOfDate getExpiredDateList(
			@QueryParam("derivativeId") long derivativeId) {
		logger.info("HelloWorldResource.getExpiredDateList - invoked");
		return new ListOfDate(
				derivativeValueFacade.getExpiredDateList(derivativeId));
	}

	@GET
	@Produces("application/json")
	@Path("getDateOfAddList")
	public ListOfDate getDateOfAddList(
			@QueryParam("derivativeId") long derivativeId) {
		logger.info("HelloWorldResource.getDateOfAddList - invoked");
		return new ListOfDate(
				derivativeValueFacade.getDateOfAddList(derivativeId));
	}

	@GET
	@Produces("application/json")
	@Path("getAllStockIndex")
	public ListOfStockIndex getAllStockIndex(
			@QueryParam("stockMarketId") long stockMarketId) {
		logger.info("HelloWorldResource.getAllStockIndex - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockIndexList(stockIndexFacade
				.getAllStockIndex(stockMarketId));
	}

	@GET
	@Produces("application/json")
	@Path("getAllStockIndex")
	public ListOfStockIndex getAllStockIndex(
			@QueryParam("stockMarketId") long stockMarketId,
			@QueryParam("from") long from, @QueryParam("to") long to) {
		logger.info("HelloWorldResource.getAllStockIndex - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockIndexList(stockIndexFacade
				.getStockIndexForRange(stockMarketId, new Date(from), new Date(
						to)));
	}

	@GET
	@Produces("application/json")
	@Path("updateStockMarket")
	public Boolean updateStockMarket(
			@QueryParam("stockMarketId") long stockMarketId,
			@QueryParam("dateOfAdd") long dateOfAdd,
			@QueryParam("description") String description,
			@QueryParam("abbreviatedName") String abbreviatedName,
			@QueryParam("name") String name) {
		logger.info("HelloWorldResource.updateStockMarket - invoked");
		StockMarket stockMarket = new StockMarket();
		stockMarket.setAbbreviatedName(abbreviatedName);
		stockMarket.setDateOfAdd(new Date(dateOfAdd));
		stockMarket.setDescription(description);
		stockMarket.setId(stockMarketId);
		stockMarket.setName(name);
		return stockMarketFacade.updateStockMarket(stockMarket);
	}

	@GET
	@Produces("application/json")
	@Path("getStockMarketList")
	public ListOfStockMarket getStockMarketList() {
		logger.info("HelloWorldResource.getStockMarketList - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockMarketList(stockMarketFacade
				.getStockMarketList());
	}

	@GET
	@Produces("application/json")
	@Path("getStockMarketById")
	public JSONStockMarket getStockMarketById(
			@QueryParam("stockMarketId") long stockMarketId) {
		logger.info("HelloWorldResource.getStockMarketById - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockMarket(stockMarketFacade
				.getStockMarketById(stockMarketId));
	}

	@GET
	@Produces("application/json")
	@Path("getStockMarketByAbbreviatedName")
	public JSONStockMarket getStockMarketByAbbreviatedName(
			@QueryParam("abberviatedName") String abberviatedName) {
		logger.info("HelloWorldResource.getStockMarketByAbbreviatedName - invoked");
		JSONMapper jsonMapper = new JSONMapper();
		return jsonMapper.mapJSONStockMarket(stockMarketFacade
				.getStockMarketByAbbreviatedName(abberviatedName));
	}

	@GET
	@Produces("application/json")
	@Path("deleteStockIndex")
	public Boolean deleteStockIndex(
			@QueryParam("stockIndexId") long stockIndexId) {
		logger.info("HelloWorldResource.deleteStockIndex - invoked");
		return stockMarketFacade.deleteStockIndex(stockIndexId);
	}

}
