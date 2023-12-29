package org.example;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
					String island = jsonNode.toString();
					if (islaConSumaMasAlta.equals(islaEvento)) {
						System.out.println(islaEvento);
						System.out.println(island);
						guardarEvento(island);
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
	public static void guardarEvento(String jsonEvento) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = objectMapper.readTree(jsonEvento);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		String hotelName = rootNode.at("/hotelKey/name").asText();
		String hotelKey = rootNode.at("/hotelKey/key").asText();
		String island = rootNode.at("/hotelKey/island").asText();
		String checkOutDate = rootNode.at("/checkOutDate").asText();
		List<String> averagePriceDays = parsearLista(rootNode.at("/averagePriceDays"));
		List<String> cheapPriceDays = parsearLista(rootNode.at("/cheapPriceDays"));
		List<String> highPriceDays = parsearLista(rootNode.at("/highPriceDays"));
		String ts = rootNode.at("/ts").asText();
		String ss = rootNode.at("/ss").asText();

		// Conectar a la base de datos SQLite
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:hotel_db.db")) {

			String createTableSQL = "CREATE TABLE IF NOT EXISTS eventos "
					+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " hotel_name TEXT, hotel_key TEXT, island TEXT, "
					+ " check_out_date TEXT, average_price_days TEXT, "
					+ " cheap_price_days TEXT, high_price_days TEXT, "
					+ " ts TEXT, ss TEXT)";
			try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL)) {
				createTableStatement.executeUpdate();
			}

			String insertEventSQL = "INSERT INTO eventos "
					+ "(hotel_name, hotel_key, island, check_out_date, average_price_days, "
					+ " cheap_price_days, high_price_days, ts, ss) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement insertEventStatement = connection.prepareStatement(insertEventSQL)) {
				insertEventStatement.setString(1, hotelName);
				insertEventStatement.setString(2, hotelKey);
				insertEventStatement.setString(3, island);
				insertEventStatement.setString(4, checkOutDate);
				insertEventStatement.setString(5, averagePriceDays.toString());
				insertEventStatement.setString(6, cheapPriceDays.toString());
				insertEventStatement.setString(7, highPriceDays.toString());
				insertEventStatement.setString(8, ts);
				insertEventStatement.setString(9, ss);

				insertEventStatement.executeUpdate();
			}

			System.out.println("Evento guardado correctamente en la base de datos.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static List<String> parsearLista(JsonNode node) {
		List<String> lista = new ArrayList<>();
		if (node.isArray()) {
			node.forEach(item -> lista.add(item.asText()));
		}
		return lista;
	}

}
