package com.agsupport.core.jpa.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.agsupport.core.jpa.model.StockIndex;

/**
 * Fasada zarządzająca obiektami typu StockIndex znajdującymi się w relacyjnej
 * bazie danych.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Stateless
public class StockIndexFacade {

	private Logger logger = Logger.getLogger(StockIndexFacade.class);

	@PersistenceContext(unitName = "jpaAGS")
	private EntityManager em;

	/**
	 * Pobranie wszytskich wartosci indeksu danej giełdy
	 * 
	 * @param stockMarketId
	 *            id danej giełdy
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<StockIndex> getAllStockIndex(long stockMarketId) {
		try {
			TypedQuery<StockIndex> query = em
					.createQuery(
							"SELECT s FROM StockIndex s WHERE s.stockMarket.id = :stockMarketId ORDER BY s.dateOfAdd DESC",
							StockIndex.class);
			query.setParameter("stockMarketId", stockMarketId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("getAllStockIndex.Excpetion", e);
			return null;
		}
	}

	/**
	 * Pobranie wartosci indeksu danej giełdy z pewnego zakresu
	 * 
	 * @param stockMarketId
	 *            id danej giełdy
	 * @param from
	 *            początek zakresu
	 * @param to
	 *            koniec zakresu
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<StockIndex> getStockIndexForRange(long stockMarketId,
			Date from, Date to) {
		try {
			TypedQuery<StockIndex> query = em
					.createQuery(
							"SELECT s FROM StockIndex s WHERE s.stockMarket.id = :stockMarketId "
									+ "AND s.dateOfAdd >= :from "
									+ "AND s.dateOfAdd <= :to ORDER BY s.dateOfAdd DESC",
							StockIndex.class);
			query.setParameter("stockMarketId", stockMarketId)
					.setParameter("from", from, TemporalType.TIMESTAMP)
					.setParameter("to", to, TemporalType.TIMESTAMP);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("getAllStockIndex.Excpetion", e);
			return null;
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
