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

import com.agsupport.core.jpa.model.DerivativeValue;

/**
 * Fasada zarządzająca obiektami typu StockMarket znajdującymi się w relacyjnej
 * bazie danych.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Stateless
public class DerivativeValueFacade {

	private Logger logger = Logger.getLogger(DerivativeValueFacade.class);

	@PersistenceContext(unitName = "jpaAGS")
	private EntityManager em;

	/**
	 * Zwraca listę wartości danego instrumentu pochodnego dla podanej daty
	 * dodania ich do bazy
	 * 
	 * @param dateOfAdd
	 *            data dodania wartości instumentu pochodnego do bazy
	 * @param derivativeId
	 *            id instumentu pochodnego dla którego mają być zwrócone
	 *            wartości
	 * @return lista wartości instumentu pochodnego
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DerivativeValue> getDerivativeValuesByDateOfAdd(Date dateOfAdd,
			long derivativeId) {
		try {
			TypedQuery<DerivativeValue> query = em
					.createQuery(
							"SELECT d FROM DerivativeValue d WHERE d.derivative.id = :derivativeId "
									+ "AND d.dateOfAdd = :dateOfAdd ORDER BY d.expiredDate",
							DerivativeValue.class);
			query.setParameter("derivativeId", derivativeId).setParameter(
					"dateOfAdd", dateOfAdd, TemporalType.TIMESTAMP);
			return query.getResultList();
		} catch (Exception e) {
			logger.error(
					"DerivativeValueFacade.getDerivativeValuesByDateOfAdd", e);
			return null;
		}
	}

	/**
	 * Zwraca listę wartości danego instrumentu pochodnego dla pewnego zakresu
	 * oraz dla odpowiedniej daty jego wygaśnięcia. EXAMPLE: dziś mamy
	 * 08.11.2012 rok, chcemy wyświetlić jak zmieniała się wartość instrmentu
	 * pochodnego od 01.10.2012 do 31.10.2012 roku dla daty wygaśnięcia luty
	 * 2013 rok.
	 * 
	 * @param from
	 *            data początku zakresu
	 * @param to
	 *            data końca zakresu
	 * @param expierdDate
	 *            data wygasniecia instumentu pochodnego
	 * @param derivativeId
	 *            id instumentu pochodnego dla którego mają być zwrócone
	 *            wartości
	 * @return lista wartości instumentu pochodnego
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DerivativeValue> getDerivativeValuesForRangeAndExpireDate(
			Date from, Date to, Date expierdDate, long derivativeId) {
		try {
			TypedQuery<DerivativeValue> query = em
					.createQuery(
							"SELECT d FROM DerivativeValue d WHERE d.derivative.id = :derivativeId "
									+ "AND d.dateOfAdd >= :from AND d.dateOfAdd <= :to "
									+ "AND d.expiredDate = :expiredDate ORDER BY d.dateOfAdd",
							DerivativeValue.class);
			query.setParameter("derivativeId", derivativeId)
					.setParameter("from", from, TemporalType.TIMESTAMP)
					.setParameter("to", to, TemporalType.TIMESTAMP)
					.setParameter("expiredDate", expierdDate,
							TemporalType.TIMESTAMP);
			return query.getResultList();
		} catch (Exception e) {
			logger.error(
					"DerivativeValueFacade.getDerivativeValuesByDateOfAdd", e);
			return null;
		}
	}

	/**
	 * Zwraca listę możliwych dat wygaśnięcia dla danego instrumentu pochodnego
	 * 
	 * @param derivativeId
	 *            id instrumentu pochodnego
	 * @return list dat
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Date> getExpiredDateList(long derivativeId) {
		try {
			TypedQuery<Date> query = em
					.createQuery(
							"SELECT DISTINCT d.expiredDate FROM DerivativeValue d WHERE d.derivative.id = :derivativeId "
									+ " ORDER BY d.expiredDate", Date.class);
			query.setParameter("derivativeId", derivativeId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("DerivativeValueFacade.getExpiredDateList", e);
			return null;
		}
	}

	/**
	 * Zwraca listę dat, w których do bazy zostały dodane wartości danego
	 * insturmentu pochodnego
	 * 
	 * @param derivativeId
	 *            id instrumentu pochodnego
	 * @return list dat
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Date> getDateOfAddList(long derivativeId) {
		try {
			TypedQuery<Date> query = em
					.createQuery(
							"SELECT DISTINCT d.dateOfAdd FROM DerivativeValue d WHERE d.derivative.id = :derivativeId "
									+ " ORDER BY d.dateOfAdd", Date.class);
			query.setParameter("derivativeId", derivativeId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("DerivativeValueFacade.getDateOfAddList", e);
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
