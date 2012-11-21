package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.List;

import com.agsupport.core.jpa.model.DerivativeValue;

public class ListOfDerivativeValue implements Serializable {

	private List<JSONDerivativeValue> derivativeValues;

	public ListOfDerivativeValue() {
	}

	public ListOfDerivativeValue(List<JSONDerivativeValue> derivativeValues) {
		this.derivativeValues = derivativeValues;
	}

	public List<JSONDerivativeValue> getDerivativeValues() {
		return derivativeValues;
	}

	public void setDerivativeValues(List<JSONDerivativeValue> derivativeValues) {
		this.derivativeValues = derivativeValues;
	}

}
