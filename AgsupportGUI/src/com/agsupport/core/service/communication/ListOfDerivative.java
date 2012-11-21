package com.agsupport.core.service.communication;

import java.io.Serializable;
import java.util.List;

public class ListOfDerivative implements Serializable {

	private List<JSONDerivative> derivatives;

	public ListOfDerivative() {
	}

	public ListOfDerivative(List<JSONDerivative> derivatives) {
		this.derivatives = derivatives;
	}

	public List<JSONDerivative> getDerivatives() {
		return derivatives;
	}

	public void setDerivatives(List<JSONDerivative> derivatives) {
		this.derivatives = derivatives;
	}

}
