package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelRecommendationBuilder implements Recommendation {
	private Map<String, Double> sumasTemperaturasPorIsla = new HashMap<>();
	private List<String> hoteles = new ArrayList<>();
	private String islaConSumaMasAlta;

	public void recommendation(String event){
		temperatureCollector(event);
		processRecommendation();
	}
	private void processRecommendation() {
		double sumaTemperaturasMasAlta = Double.MIN_VALUE;

		for (Map.Entry<String, Double> entry : sumasTemperaturasPorIsla.entrySet()) {
			String isla = entry.getKey();
			double sumaActual = entry.getValue();
			if (sumaActual > sumaTemperaturasMasAlta) {
				sumaTemperaturasMasAlta = sumaActual;
				islaConSumaMasAlta = isla;
			}
		}

		if (hoteles.size() < 21){
		}
		else {
			for (String hotel : hoteles) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode jsonNode = objectMapper.readTree(hotel);
					String islaEvento = jsonNode.path("hotelKey").path("island").asText();

					if (islaConSumaMasAlta.equals(islaEvento)) {
						System.out.println(islaEvento);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	public void temperatureCollector(String event) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(event);
			String ssValue = jsonNode.path("ss").asText();

			if ("OpenWeatherMapSupplier".equals(ssValue)) {
				double temperatura = jsonNode.path("temperature").asDouble();
				String isla = jsonNode.path("location").path("island").asText();
				sumarTemperaturaPorIsla(isla, temperatura);
			} else if ("Xotelo".equals(ssValue)) {
				hoteles.add(event);
			} else {
				System.out.println("Evento no reconocido: " + ssValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void sumarTemperaturaPorIsla(String isla, double temperatura) {
		sumasTemperaturasPorIsla.put(isla, sumasTemperaturasPorIsla.getOrDefault(isla, 0.0) + temperatura);
	}

}
