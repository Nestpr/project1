package org.example.control;
import org.example.model.Weather;

import java.sql.*;
import java.util.List;

public class SQLiteWeatherStore {


	/*
	public static Connection connect(String dbPath) {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:" + dbPath;
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
			return conn;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	private static void createTable(Statement statement) throws SQLException {
		statement.execute("CREATE TABLE IF NOT EXISTS weather (" +
				"timeInstant TEXT,\n" +
				"rain REAL,\n" +
				"wind REAL,\n" +
				"temperature REAL,\n" +
				"humidity REAL\n"+
				");");
	}

	private static boolean insert(Statement statement) throws SQLException {
		return statement.execute("INSERT INTO weather (timeInstant, rain, wind, temperature, humidity)\n" +
				"VALUES('someTime', 5.0, 10.0, 25.0, 0.8);");
	}

	*/
	private static final String JDBC_URL = "jdbc:sqlite:weather_database.db";

	public void WeatherDatabase() {
		// Load SQLite JDBC driver
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Create Weather table if not exists
		try (Connection connection = DriverManager.getConnection(JDBC_URL);
			 PreparedStatement statement = connection.prepareStatement(
					 "CREATE TABLE IF NOT EXISTS Weather (" +
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

	public void insertWeatherData(List<Weather> weatherList) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
			for (Weather weather : weatherList) {
				try (PreparedStatement statement = connection.prepareStatement(
						"INSERT INTO Weather (timeInstant, rain, wind, temperature, humidity) VALUES (?, ?, ?, ?, ?)"
				)) {
					statement.setString(1, weather.getTimeInstant().toString());
					statement.setDouble(2, weather.getRain());
					statement.setDouble(3, weather.getWind());
					statement.setDouble(4, weather.getTemperature());
					statement.setDouble(5, weather.getHumidity());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
