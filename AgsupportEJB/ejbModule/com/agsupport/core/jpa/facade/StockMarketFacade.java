package com.agsupport.core.jpa.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.agsupport.core.jpa.model.StockIndex;
import com.agsupport.core.jpa.model.StockMarket;

/**
 * Fasada zarządzająca obiektami typu StockMarket znajdującymi się w relacyjnej
 * bazie danych.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Stateless
public class StockMarketFacade {

	private Logger logger = Logger.getLogger(StockMarketFacade.class);

	@PersistenceContext(unitName = "jpaAGS")
	private EntityManager em;

	/**
	 * Stworzenie w bazie danych giełdy
	 * 
	 * @param stockMarket
	 *            tworzona giełda
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createStockMarket(StockMarket stockMarket) {
		try {
			stockMarket.setDateOfAdd(new Date());
			em.persist(stockMarket);
		} catch (Exception e) {
			logger.error("createStockMarket.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Akutalizacja giełdy zznajdującej się w bazie
	 * 
	 * @param stockMarket
	 *            giełda znajdująca się w bazie (pole id musi być zgodne, z
	 *            obiektem aktualizowanym w bazie)
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean updateStockMarket(StockMarket stockMarket) {
		try {
			em.merge(stockMarket);
		} catch (Exception e) {
			logger.error("updateStockMarket.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Usunięcie giełdy z bazy danych
	 * 
	 * @param stockMarketId
	 *            id usuwanej giełdy
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean deleteStockMarket(long stockMarketId) {
		try {
			StockMarket stockMarket = em.find(StockMarket.class, stockMarketId);
			em.remove(stockMarket);
		} catch (Exception e) {
			logger.error("deleteStockMarket.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Pobieranie listy giełd
	 * 
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<StockMarket> getStockMarketList() {
		try {
			TypedQuery<StockMarket> query = em.createQuery(
					"SELECT s from StockMarket s", StockMarket.class);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("getStockMarketList.Excpetion", e);
			return null;
		}

	}

	/**
	 * Pobieranie giełdy na podstawie id
	 * 
	 * @param stockMarketId
	 *            id giełdy
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public StockMarket getStockMarketById(long stockMarketId) {
		try {
			StockMarket stockMarket = em.find(StockMarket.class, stockMarketId);
			return stockMarket;
		} catch (Exception e) {
			logger.error("getStockMarketById.Excpetion", e);
			return null;
		}
	}

	/**
	 * Pobieranie giełdy na podstawie skróconej nazwy
	 * 
	 * @param abberviatedName
	 *            skrócona nazwa
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public StockMarket getStockMarketByAbbreviatedName(String abberviatedName) {
		try {
			TypedQuery<StockMarket> query = em
					.createQuery(
							"SELECT s from StockMarket s where s.abbreviatedName =:abberviatedName",
							StockMarket.class);
			query.setParameter("abberviatedName", abberviatedName);
			StockMarket stockMarket = query.getSingleResult();
			return stockMarket;
		} catch (Exception e) {
			logger.error("getStockMarketByAbbreviatedName.Excpetion", e);
			return null;
		}
	}

	/**
	 * Dodanie wartości indeksu giełdowego do danej giełdy tylko i wyłącznie gdy
	 * wartości bezpośrednio sąsiadujących wartości indeksu w kontekście daty są
	 * różne.
	 * 
	 * @param stockMarketId
	 *            id giełdy
	 * @param stockIndex
	 *            indeks giełdowy
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean addStockIndex(long stockMarketId, StockIndex stockIndex) {
		try {
			TypedQuery<StockIndex> query = em
					.createQuery(
							"SELECT s FROM StockIndex s WHERE s.stockMarket.id = :stockMarketId ORDER BY s.dateOfAdd DESC",
							StockIndex.class);
			query.setParameter("stockMarketId", stockMarketId).setMaxResults(1);
			List<StockIndex> list = query.getResultList();

			/*
			 * Nie dodaje do bazy wartości indeksów dotyczących tej samej giełdy
			 * i takich których daty dodania do bazy bezpośrednio następnują po
			 * sobie a ich ceny nie zmieniają się .
			 */
			if (list != null && list.isEmpty() == false) {
				StockIndex stockIndexFirstResult = list.get(0);
				if (stockIndexFirstResult.getPrice().equals(
						stockIndex.getPrice())) {
					return false;
				}
			}

			StockMarket stockMarket = em.find(StockMarket.class, stockMarketId);
			stockIndex.setStockMarket(stockMarket);
			em.persist(stockIndex);
		} catch (Exception e) {
			logger.error("addStockIndex.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Usunięcie danej wartości indeksu giełdowego
	 * 
	 * @param stockIndexId
	 *            id indeksu giełdowego
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean deleteStockIndex(long stockIndexId) {
		try {
			StockIndex stockIndex = em.find(StockIndex.class, stockIndexId);
			StockMarket stockMarket = stockIndex.getStockMarket();
			stockMarket.getStockIndexes().remove(stockIndex);
			em.remove(stockIndex);
		} catch (Exception e) {
			logger.error("deleteStockIndex.Excpetion", e);
			return false;
		}
		return true;
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
