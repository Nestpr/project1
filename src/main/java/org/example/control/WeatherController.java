package org.example.control;

import org.example.model.Location;
import org.example.model.Weather;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class WeatherController {
	private List<Location> locations;
	private List<Instant> timeInstant;
	private WeatherSupplier weatherSupplier;
	private WeatherStore weatherStore;

	public WeatherController(List<Location> locations, WeatherSupplier weatherSupplier, WeatherStore weatherStore, List<Instant> timeInstant) {
		this.locations = locations;
		this.weatherSupplier = weatherSupplier;
		this.weatherStore = weatherStore;
		this.timeInstant = timeInstant;
	}

	public void execute(List<Location> locations, WeatherSupplier weatherSupplier, WeatherStore weatherStore, List<Instant> instantList){
		for (Location location : locations) {
			List<Weather> weatherList = weatherSupplier.getWeather(location, instantList);
			weatherStore.storeWeather(weatherList);
		}
	}





	/*
	public void CreateWeatherDataBase(List<Location>locations) {
		for (Location location : locations) {
			weatherStore.WeatherDatabase(location.getIsland());
		}
	}

	public void InsertWeatherData(List<Location>locations, List<Instant> timeInstant){
		for (Location location : locations) {
			try {
				List<Weather> weatherList = weatherSupplier.getWeatherList(location, timeInstant);
				try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
					for (Weather weather : weatherList) {
						try (PreparedStatement statement = connection.prepareStatement(
								"INSERT OR REPLACE INTO Weather (timeInstant, rain, wind, temperature, humidity) VALUES (?, ?, ?, ?, ?)"
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
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	 */
}
