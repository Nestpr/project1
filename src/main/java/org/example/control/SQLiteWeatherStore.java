package org.example.control;
import org.example.model.Weather;

import java.sql.*;
import java.util.List;

public class SQLiteWeatherStore implements WeatherStore{


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
	private final String JDBC_URL = "jdbc:sqlite:weather_database.db";

	public void storeWeather(List<Weather> weatherList){
		this.WeatherDatabase();
		this.insertWeather(weatherList);
	}
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
					 "CREATE TABLE IF NOT EXISTS Weather" + " (" +
							 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
							 "timeInstant TEXT," +
							 "rain REAL," +
							 "wind REAL," +
							 "temperature REAL," +
							 "humidity REAL," +
							 "location TEXT)"
			 )) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	public void insertWeather(List<Weather> weatherList) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL)) {

				try (PreparedStatement statement = connection.prepareStatement(
						"INSERT OR REPLACE INTO Weather (timeInstant, rain, wind, temperature, humidity, location) VALUES (?, ?, ?, ?, ?, ?)"
				)) {
					for (Weather weather : weatherList) {
					System.out.println("SQL Statement: " + statement.toString());
					System.out.println("Weather: " + weather.getLocation().getIsland());

					statement.setString(1, weather.getTimeInstant().toString());
					statement.setDouble(2, weather.getRain());
					statement.setDouble(3, weather.getWind());
					statement.setDouble(4, weather.getTemperature());
					statement.setDouble(5, weather.getHumidity());
					statement.setString(6, weather.getLocation().getIsland());
					statement.executeUpdate();
				}
					System.out.println("Weather data inserted successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	 */

	public void insertWeather(List<Weather> weatherList) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
			try (PreparedStatement statement = connection.prepareStatement(
					"INSERT OR REPLACE INTO Weather (id, timeInstant, rain, wind, temperature, humidity, location) VALUES (null, ?, ?, ?, ?, ?, ?)"
			)) {
				for (Weather weather : weatherList) {
					System.out.println("SQL Statement: " + statement.toString());
					System.out.println("Weather: " + weather.getLocation().getIsland());

					statement.setNull(1, Types.INTEGER);
					statement.setString(1, weather.getTimeInstant().toString());
					statement.setDouble(2, weather.getRain());
					statement.setDouble(3, weather.getWind());
					statement.setDouble(4, weather.getTemperature());
					statement.setDouble(5, weather.getHumidity());
					statement.setString(6, weather.getLocation().getIsland());

					// Execute the statement inside the loop
					statement.executeUpdate();
				}
				System.out.println("Weather data inserted successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
