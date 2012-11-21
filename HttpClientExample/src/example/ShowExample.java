package example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.agsupport.core.service.communication.JSONDerivative;
import com.agsupport.core.service.communication.JSONDerivativeValue;
import com.agsupport.core.service.communication.ListOfDerivative;
import com.agsupport.core.service.communication.ListOfDerivativeValue;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ShowExample {

	public static void main(String[] args) {

		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client c = Client.create(cc);

		Date d1 = new Date();

		/**
		 * przykład użycia klienta biblioteki wszystkie wymagane do użycia tego
		 * klienta są w katalogu LIB, tego projektu
		 */

		WebResource wr1 = c
				.resource("http://localhost:8080/AgsupportWeb/service");
		ListOfDerivative resp1 = wr1.path("getDerivativeList")
				.accept("application/json").get(ListOfDerivative.class);
		for (JSONDerivative d : resp1.getDerivatives()) {

			System.out.println("Name: " + d.getName() + ", dateOfAdd: "
					+ d.getDateOfAdd());
		}
		
		
		WebResource wr2 = c
				.resource("http://localhost:8080/AgsupportWeb/service");
		JSONDerivative resp2 = wr1.path("getDerivativeById").queryParam("derivativeId", (new Long(1)).toString())
				.accept("application/json").get(JSONDerivative.class);
		
		System.out.println("Name: " + resp2.getName() + ", dateOfAdd: "
				+ resp2.getDateOfAdd());
		

	}

}
