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
	private Map<String, Double> temperaturesPerIsland = new HashMap<>();
	private List<String> hotels = new ArrayList<>();
	private String bestIsland;
	private static String jdbcUrl;

	public TravelRecommendationBuilder(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void recommendation(String event){
		temperatureCollector(event);
		processRecommendation();

	}
	private void processRecommendation() {
		double highestAddTemperature = Double.MIN_VALUE;
		for (Map.Entry<String, Double> entry : temperaturesPerIsland.entrySet()) {
			String island = entry.getKey();
			double actualAdd = entry.getValue();
			if (actualAdd > highestAddTemperature) {
				highestAddTemperature = actualAdd;
				bestIsland = island;
			}
		}
		if (hotels.size() < 40){
		}
		else {
			for (String hotel : hotels) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode jsonNode = objectMapper.readTree(hotel);
					String islandEvent = jsonNode.path("hotelKey").path("island").asText();
					String event = jsonNode.toString();
					if (bestIsland.equals(islandEvent)) {
						System.out.println(islandEvent);
						System.out.println(event);
						saveEvent(event);
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
				double temperature = jsonNode.path("temperature").asDouble();
				String island = jsonNode.path("location").path("island").asText();
				addIslandTemperature(island, temperature);
			} else if ("Xotelo".equals(ssValue)) {
				hotels.add(event);
			} else {
				System.out.println("Evento no reconocido: " + ssValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void addIslandTemperature(String island, double temperature) {
		temperaturesPerIsland.put(island, temperaturesPerIsland.getOrDefault(island, 0.0) + temperature);
	}

	public static void saveEvent(String jsonEvent) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = objectMapper.readTree(jsonEvent);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		String hotelName = rootNode.at("/hotelKey/name").asText();
		String hotelKey = rootNode.at("/hotelKey/key").asText();
		String island = rootNode.at("/hotelKey/island").asText();
		String checkOutDate = rootNode.at("/checkOutDate").asText();
		List<String> averagePriceDays = parseList(rootNode.at("/averagePriceDays"));
		List<String> cheapPriceDays = parseList(rootNode.at("/cheapPriceDays"));
		List<String> highPriceDays = parseList(rootNode.at("/highPriceDays"));
		String ts = rootNode.at("/ts").asText();
		String ss = rootNode.at("/ss").asText();
		try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
			String createTableSQL = "CREATE TABLE IF NOT EXISTS events "
					+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " hotel_name TEXT, hotel_key TEXT, island TEXT, "
					+ " average_price_days TEXT, "
					+ " cheap_price_days TEXT, high_price_days TEXT, "
					+ " ts TEXT, ss TEXT, event_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
			try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL)) {
				createTableStatement.executeUpdate();
			}
			String insertEventSQL = "INSERT OR REPLACE INTO events "
					+ "(hotel_name, hotel_key, island, average_price_days, "
					+ " cheap_price_days, high_price_days, ts, ss) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement insertEventStatement = connection.prepareStatement(insertEventSQL)) {
				insertEventStatement.setString(1, hotelName);
				insertEventStatement.setString(2, hotelKey);
				insertEventStatement.setString(3, island);
				//insertEventStatement.setString(4, checkOutDate);
				insertEventStatement.setString(4, averagePriceDays.toString());
				insertEventStatement.setString(5, cheapPriceDays.toString());
				insertEventStatement.setString(6, highPriceDays.toString());
				insertEventStatement.setString(7, ts);
				insertEventStatement.setString(8, ss);
				insertEventStatement.executeUpdate();
			}

			String deleteOldEventsSQL = "DELETE FROM events WHERE id NOT IN (SELECT id FROM events ORDER BY event_datetime DESC LIMIT 5)";
			try (PreparedStatement deleteOldEventsStatement = connection.prepareStatement(deleteOldEventsSQL)) {
				deleteOldEventsStatement.executeUpdate();
			}

			System.out.println("Event saved successfully in the database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static List<String> parseList(JsonNode node) {
		List<String> list = new ArrayList<>();
		if (node.isArray()) {
			node.forEach(item -> list.add(item.asText()));
		}
		return list;
	}

}
