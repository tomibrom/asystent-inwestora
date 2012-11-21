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

import com.agsupport.core.jpa.model.Derivative;
import com.agsupport.core.jpa.model.DerivativeValue;

/**
 * Fasada zarządzająca obiektami typu Derivative znajdującymi się w relacyjnej
 * bazie danych.
 * 
 * @author Michał Gruszczyński
 * 
 */

@Stateless
public class DerivativeFacade {

	private Logger logger = Logger.getLogger(DerivativeFacade.class);

	@PersistenceContext(unitName = "jpaAGS")
	private EntityManager em;

	/**
	 * Stworzenie w bazie instrumentu pochodnego
	 * 
	 * @param derivative
	 *            tworzony instrument pochodny
	 * @return czy stworzono instrument pochodny
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createDerivative(Derivative derivative) {
		try {
			derivative.setDateOfAdd(new Date());
			em.persist(derivative);
		} catch (Exception e) {
			logger.error("createDerivative.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Aktualizacja instrumentu pochodnego znajdującej się w bazie
	 * 
	 * @param derivative
	 *            instrument pochodny znajdująca się w bazie (pole id musi być
	 *            zgodne, z obiektem aktualizowanym w bazie)
	 * @return czy aktualizowano instrument pochodny
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean updateDerivative(Derivative derivative) {
		try {
			em.merge(derivative);
		} catch (Exception e) {
			logger.error("updateDerivative.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Usunięcie instrumentu pochodnego z bazy danych
	 * 
	 * @param derivativeId
	 *            id usuwanej giełdy
	 * @return czy usunięto instrument pochodny
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean deleteDerivative(long derivativeId) {
		try {
			Derivative derivative = em.find(Derivative.class, derivativeId);
			em.remove(derivative);
		} catch (Exception e) {
			logger.error("deleteDerivative.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Pobieranie listy wszystkich instrumentów pochodnych
	 * 
	 * @return lista instruemntów pochodnych
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Derivative> getDerivativeList() {
		logger.info("DerivativeFacade.getDerivativeList - invoke");
		try {
			TypedQuery<Derivative> query = em.createQuery(
					"SELECT d FROM Derivative d ORDER BY d.name",
					Derivative.class);
			logger.info("DerivativeFacade.getDerivativeList before return 1");
			List<Derivative> list = query.getResultList();
			logger.info("DerivativeFacade.getDerivativeList before return 2");
			return list;
		} catch (Exception e) {
			logger.error("getDerivativeList.Excpetion", e);
			return null;
		}

	}

	/**
	 * Pobieranie instrumentu pochodnego na podstawie id
	 * 
	 * @param derivativeId
	 *            id instrumentu pochodnego
	 * @return instrument pochodny
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Derivative getDerivativeById(long derivativeId) {
		try {
			Derivative derivative = em.find(Derivative.class, derivativeId);
			return derivative;
		} catch (Exception e) {
			logger.error("getDerivativeById.Excpetion", e);
			return null;
		}
	}

	/**
	 * Pobieranie instrumentu pochodnego na podstawie nazwy
	 * 
	 * @param name
	 *            nazwa insturmentu pochodnego
	 * @return instrument pochodny
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Derivative getDerivativeByName(String name) {
		try {
			TypedQuery<Derivative> query = em.createQuery(
					"SELECT d FROM Derivative d WHERE d.name = :name",
					Derivative.class);
			query.setParameter("name", name);
			Derivative derivative = query.getSingleResult();
			return derivative;
		} catch (Exception e) {
			logger.error("getDerivativeByName.Excpetion", e);
			return null;
		}
	}

	/**
	 * Dodanie wartości do instrumentu pochodnego. Dodanie wartości zachodzi
	 * przy pewnych warunkach. Warunki: Wartości instrumentu pochodnego
	 * dotyczące tego samego instrumentu pochodnego, wygaśnięcia w tym samym
	 * miesiącu i roku, nie posiadają tych samych wartości i występują w bazie w
	 * bezpośrednim sąsiedzwie pod względem daty dodania do bazy
	 * 
	 * @param derivativeId
	 *            id instrumentu pochodnego
	 * @param derivativeValue
	 *            wartość instrumentu pochodnego
	 * @return czy dodano wartość instumentu pochodnego
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean addDerivativeValue(long derivativeId,
			DerivativeValue derivativeValue) {
		try {
			TypedQuery<DerivativeValue> query = em
					.createQuery(
							"SELECT d FROM DerivativeValue d WHERE d.derivative.id = :derivativeId AND d.expiredDate = :expiredDate ORDER BY d.dateOfAdd DESC",
							DerivativeValue.class);
			query.setParameter("derivativeId", derivativeId)
					.setParameter("expiredDate",
							derivativeValue.getExpiredDate(),
							TemporalType.TIMESTAMP).setMaxResults(1);
			List<DerivativeValue> list = query.getResultList();

			/*
			 * NIE DODANIE DWÓCH TYCH SAMYCH WARTOŚCI KTÓRE DOTYCZĄ TEGO SAMEGO
			 * // CZASU WYGAŚNIĘCIA, TEGO SAMEGO INSTRUMENTU POCHODNEGO I
			 * WARTOŚĆ INSTUMENTU POCHODNEGO JEST OSTATNIĄ Z DODANYCH
			 */
			if (list != null && list.isEmpty() == false) {
				DerivativeValue derivativeValueFirstResult = list.get(0);
				if (derivativeValueFirstResult.getPrice().equals(
						derivativeValue.getPrice())) {
					return false;
				}
			}

			Derivative derivative = em.find(Derivative.class, derivativeId);
			derivativeValue.setDerivative(derivative);
			em.persist(derivativeValue);

		} catch (Exception e) {
			logger.error("addDerivativeValue.Excpetion", e);
			return false;
		}
		return true;
	}

	/**
	 * Usunięcie danej wartości instrumentu pochodnego
	 * 
	 * @param derivativeValueId
	 *            id wartości instrumentu pochodnego
	 * @return czy usunięto wartość instumentu pochodnego
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean deleteDerivativeValue(long derivativeValueId) {
		try {
			DerivativeValue derivativeValue = em.find(DerivativeValue.class,
					derivativeValueId);
			Derivative derivative = derivativeValue.getDerivative();
			derivative.getDerivativeValues().remove(derivativeValue);
			em.remove(derivativeValue);
		} catch (Exception e) {
			logger.error("deleteDerivativeValue.Excpetion", e);
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
