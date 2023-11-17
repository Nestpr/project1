package org.example.control;
import org.example.model.Weather;

import java.sql.*;
import java.util.List;


public class SQLiteWeatherStore implements WeatherStore {

	public void storeWeather(List<Weather> weatherList) {
		for (Weather weather : weatherList) {
			this.WeatherDatabase(weather.getLocation().getIsland());
			this.insertWeather(weather);
		}
	}

	public void WeatherDatabase(String location) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String tableName = "Weather_" + location.replace(" ", "_");
		try (Connection connection = DriverManager.getConnection(System.getenv("path"));
			 PreparedStatement statement = connection.prepareStatement(
					 "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
							 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
							 "timeInstant TEXT," +
							 "rain REAL," +
							 "wind REAL," +
							 "temperature REAL," +
							 "humidity REAL)"
			 )) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertWeather(Weather weather) {
		String tableName = "Weather_" + weather.getLocation().getIsland().replace(" ", "_");
		try (Connection connection = DriverManager.getConnection(System.getenv("path"))) {
			// Check if a record with the same values already exists
			boolean recordExists;
			String selectQuery = "SELECT id FROM " + tableName + " WHERE timeInstant = ? AND rain = ? AND wind = ? AND temperature = ? AND humidity = ?";
			try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
				selectStatement.setString(1, weather.getTimeInstant().toString());
				selectStatement.setDouble(2, weather.getRain());
				selectStatement.setDouble(3, weather.getWind());
				selectStatement.setDouble(4, weather.getTemperature());
				selectStatement.setDouble(5, weather.getHumidity());

				try (ResultSet resultSet = selectStatement.executeQuery()) {
					recordExists = resultSet.next();
				}
			}

			if (recordExists) {

				String updateQuery = "UPDATE " + tableName + " SET timeInstant = ?, rain = ?, wind = ?, temperature = ?, humidity = ? WHERE timeInstant = ? AND rain = ? AND wind = ? AND temperature = ? AND humidity = ?";
				try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
					updateStatement.setString(1, weather.getTimeInstant().toString());
					updateStatement.setDouble(2, weather.getRain());
					updateStatement.setDouble(3, weather.getWind());
					updateStatement.setDouble(4, weather.getTemperature());
					updateStatement.setDouble(5, weather.getHumidity());

					updateStatement.setString(6, weather.getTimeInstant().toString());
					updateStatement.setDouble(7, weather.getRain());
					updateStatement.setDouble(8, weather.getWind());
					updateStatement.setDouble(9, weather.getTemperature());
					updateStatement.setDouble(10, weather.getHumidity());

					updateStatement.executeUpdate();
				}
			} else {

				String insertQuery = "INSERT OR REPLACE INTO " + tableName + " (id, timeInstant, rain, wind, temperature, humidity) VALUES (null, ?, ?, ?, ?, ?)";
				try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
					insertStatement.setString(1, weather.getTimeInstant().toString());
					insertStatement.setDouble(2, weather.getRain());
					insertStatement.setDouble(3, weather.getWind());
					insertStatement.setDouble(4, weather.getTemperature());
					insertStatement.setDouble(5, weather.getHumidity());

					insertStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
